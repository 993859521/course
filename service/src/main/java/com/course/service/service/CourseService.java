package com.course.service.service;

import com.course.service.domain.dto.*;
import com.course.service.domain.entity.Chapter;
import com.course.service.domain.entity.Teacher;
import com.course.service.dto.ChapterMapper;
import com.course.service.dto.CourseMapper;
import com.course.service.domain.entity.Course;
import com.course.service.dto.MyCourseMapper;
import com.course.service.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.StringUtil;
import com.course.service.util.UuidUtil;

import java.util.ArrayList;
import java.util.List;

import static com.course.service.util.UuidUtil.getShortUuid;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseService {
    private final CourseMapper courseMapper;
    private final CourseCategoryService courseCategoryService;
    private final MyCourseMapper myCourseMapper;
    private final ChapterMapper chapterMapper;
    private final TeacherService teacherService;

    /**
     * 列表查询
     *
     * @param pageDto
     * @return
     */
    public PageDto selectAll(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<CourseDto> courseDtos = new ArrayList<CourseDto>();
        List<Course> courses = courseMapper.selectAll();
        PageInfo pageInfo = new PageInfo<>(courses);
        pageDto.setTotal(pageInfo.getTotal());
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course, courseDto);
            List<Chapter> chapters = chaopter_select(course.getId());
            List<Teacher> teachers = new ArrayList<Teacher>();
            for (Chapter item :
                    chapters) {
                Teacher teacher = teacherService.selectAll(item.getTeacherId());
                TeacherDto teacherDto= new TeacherDto();

                if (!teachers.contains(teacher)) {
                    teachers.add(teacher);
                }

            }
            courseDto.setTeacher(teachers);
            courseDtos.add(courseDto);
        }

        pageDto.setList(courseDtos);
        return pageDto;
    }

    public List<Chapter> chaopter_select(String CourseId) {
        List<Chapter> chapters = chapterMapper.select(Chapter.builder().courseId(CourseId).build());
        return chapters;
    }

    /**
     * 增加数据或者更新数据
     *
     * @param courseDto
     */
    public void save(CourseDto courseDto) {
        Course course = CopyUtil.copy(courseDto, Course.class);

        if (StringUtil.isEmpty(course.getId())) {
            Date now = new Date();
            course.setCreatedAt(now);
            course.setUpdatedAt(now);
            course.setId(UuidUtil.getShortUuid());
            courseMapper.insert(course);
        } else {
            courseMapper.updateByPrimaryKey(course);
        }
        courseCategoryService.saveBatch(course.getId(), courseDto.getCategorys());
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    public void save_time(String courseid, Integer time) {
        Course course = courseMapper.selectOne(Course.builder().id(courseid).build());
        course.setTime(time);
        log.info(course.getTime().toString());
        courseMapper.updateByPrimaryKey(course);

    }

    public void del_time(String courseid, Integer time) {
        Course course = courseMapper.selectOne(Course.builder().id(courseid).build());
        course.setTime(course.getTime() - time);
        log.info(course.getTime().toString());
        courseMapper.updateByPrimaryKey(course);


    }

    @Transactional
    public void sort(SortDto sortDto) {
        // 修改当前记录的排序值
        myCourseMapper.updateSort(sortDto);

        // 如果排序值变大
        if (sortDto.getNewSort() > sortDto.getOldSort()) {
            myCourseMapper.moveSortsForward(sortDto);
        }

        // 如果排序值变小
        if (sortDto.getNewSort() < sortDto.getOldSort()) {
            myCourseMapper.moveSortsBackward(sortDto);
        }
    }


}
