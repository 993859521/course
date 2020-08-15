package com.course.service.service;
import com.course.service.domain.entity.CourseContent;
import com.course.service.dto.CourseContentMapper;
import com.course.service.domain.dto. Course_ContentDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;
import com.course.service.util.UuidUtil;
import java.util.ArrayList;
import java.util.List;

import static com.course.service.util.UuidUtil.getShortUuid;
/**
 * @author Administrator
 */
@Slf4j
@Service
@RequiredArgsConstructor( onConstructor = @__(@Autowired))
public class  Course_ContentService {
private final CourseContentMapper course_ContentMapper;
/**
* 列表查询
* @param pageDto
* @return
*/
public PageDto selectAll(PageDto pageDto){
PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
List< Course_ContentDto>  course_ContentDtos = new ArrayList< Course_ContentDto>();
        List< CourseContent>  course_Contents =  course_ContentMapper.selectAll();
            PageInfo pageInfo = new PageInfo<>( course_Contents);
            pageDto.setTotal(pageInfo.getTotal());
            for (int i = 0; i <  course_Contents.size(); i++) {
             CourseContent  course_Content =  course_Contents.get(i);
             Course_ContentDto  course_ContentDto=new  Course_ContentDto();
            BeanUtils.copyProperties(course_Content, course_ContentDto);
             course_ContentDtos.add( course_ContentDto);
            }
            pageDto.setList( course_ContentDtos);
            return pageDto;
            }

            /**
            * 增加数据或者更新数据
            * @param  course_ContentDto
            */
            public void save( Course_ContentDto  course_ContentDto){
             CourseContent  course_Content = CopyUtil.copy( course_ContentDto, CourseContent.class);
             log.info("值:{}，id:{}",StringUtil.isEmpty(course_Content.getId()),course_Content.getId());
            if(StringUtil.isEmpty(course_Content.getId())){
                log.info(course_Content.toString());
                course_ContentMapper.insert(course_Content);
            }else{
             course_ContentMapper.updateByPrimaryKey(course_Content);
            }
            }

            /**
            * 删除数据
            * @param id
            */
            public void delete(String id){
             course_ContentMapper.deleteByPrimaryKey(id);
            }

    /**
     * 查找数据
     * @param courseId
     * @return
     */
    public CourseContent findBycCourseId(String courseId){
                return course_ContentMapper.selectOne(CourseContent.builder().id(courseId).build());
            }

            }
