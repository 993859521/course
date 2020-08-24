package com.course.service.service;

import com.course.service.domain.dto.*;
import com.course.service.domain.entity.*;
import com.course.service.dto.*;
import com.course.service.enums.CourseStatusEnum;
import com.course.service.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
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
    private final CourseContentMapper courseContentMapper;
    private final SectionMapper sectionMapper;
    public List<CourseDto> listNew(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        Example example = new Example(Course.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("created_at desc");
        criteria.andEqualTo("status",CourseStatusEnum.PUBLISH.getCode());
        List<Course> courseList = courseMapper.selectByExample(example);
        return CopyUtil.copyList(courseList, CourseDto.class);
    }
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
    /**
     * 列表查询：关联课程分类表
     * @param pageDto
     */
    public void list(CoursePageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<CourseDto> courseDtoList = myCourseMapper.list(pageDto);
        PageInfo<CourseDto> pageInfo = new PageInfo<>(courseDtoList);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(courseDtoList);
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
    /**
     * 查找某一课程，供web模块用，只能查已发布的
     * @param id
     * @return
     */
    public CourseDto findCourse(String id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        if (course == null || !CourseStatusEnum.PUBLISH.getCode().equals(course.getStatus())) {
            return null;
        }
        CourseDto courseDto = CopyUtil.copy(course, CourseDto.class);

        // 查询内容
        CourseContent content = courseContentMapper.selectByPrimaryKey(id);
        if (content != null) {
            courseDto.setContent(content.getContent());
        }


        // 查找章信息
        List<Chapter> chapterList = chaopter_select(id);
        courseDto.setChapters(chapterList);
        List<Teacher> teachers = new ArrayList<Teacher>();
        // 查找讲师信息
        for (Chapter item :
                chapterList) {
            Teacher teacher = teacherService.selectAll(item.getTeacherId());
            TeacherDto teacherDto= new TeacherDto();

            if (!teachers.contains(teacher)) {
                teachers.add(teacher);
            }

        }
        courseDto.setTeacher(teachers);
        // 查找节信息
        List<Section> sectionList = sectionMapper.select(Section.builder().courseId(id).build());
        courseDto.setSections(sectionList);

        return courseDto;
    }

}
