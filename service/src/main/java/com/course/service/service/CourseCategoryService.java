package com.course.service.service;
import com.course.service.domain.dto.CategoryDto;
import com.course.service.domain.dto.CourseDto;
import com.course.service.dto. CourseCategoryMapper;
import com.course.service.domain.dto. CourseCategoryDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity. CourseCategory;
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
@Slf4j
@Service
@RequiredArgsConstructor( onConstructor = @__(@Autowired))
public class  CourseCategoryService {
private final  CourseCategoryMapper  courseCategoryMapper;
/**
* 列表查询
* @param pageDto
* @return
*/
public PageDto selectAll(PageDto pageDto){
PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
List< CourseCategoryDto>  courseCategoryDtos = new ArrayList< CourseCategoryDto>();
        List< CourseCategory>  courseCategorys =  courseCategoryMapper.selectAll();
            PageInfo pageInfo = new PageInfo<>( courseCategorys);
            pageDto.setTotal(pageInfo.getTotal());
            for (int i = 0; i <  courseCategorys.size(); i++) {
             CourseCategory  courseCategory =  courseCategorys.get(i);
             CourseCategoryDto  courseCategoryDto=new  CourseCategoryDto();
            BeanUtils.copyProperties( courseCategory, courseCategoryDto);
             courseCategoryDtos.add( courseCategoryDto);
            }
            pageDto.setList( courseCategoryDtos);
            return pageDto;
            }

            /**
            * 增加数据或者更新数据
            * @param  courseCategoryDto
            */
            public void save( CourseCategoryDto  courseCategoryDto){
             CourseCategory  courseCategory = CopyUtil.copy( courseCategoryDto,  CourseCategory.class);

            if(StringUtil.isEmpty( courseCategory.getId())){
                courseCategory.setId(UuidUtil.getShortUuid());
                courseCategoryMapper.insert(courseCategory);
            }else{
             courseCategoryMapper.updateByPrimaryKey( courseCategory);
            }
            }

            /**
            * 删除数据
            * @param id
            */
            public void delete(String id){
             courseCategoryMapper.deleteByPrimaryKey(id);
            }
            public List<CourseCategoryDto> listByCourse(String courseId) {

                List<CourseCategory> courseCategoryList = courseCategoryMapper.select(CourseCategory.builder().courseId(courseId).build());
                return CopyUtil.copyList(courseCategoryList, CourseCategoryDto.class);
    }


    public void saveBatch(String courseId, List<CategoryDto> dtoList) {
        courseCategoryMapper.delete(CourseCategory.builder().courseId(courseId).build());

        for (int i = 0, l = dtoList.size(); i < l; i++) {
            CategoryDto categoryDto = dtoList.get(i);
            courseCategoryMapper.insert(
                    CourseCategory.builder()
                            .id(UuidUtil.getShortUuid())
                            .courseId(courseId)
                            .categoryId(categoryDto.getId())
                            .build()
            );

        }
    }
            }
