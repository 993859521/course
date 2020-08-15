package com.course.business.controller;

import com.course.service.domain.dto.CourseCategoryDto;
import com.course.service.domain.dto.Course_ContentDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.domain.entity.CourseContent;
import com.course.service.service.Course_ContentService;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course_Content")

public class Course_ContentController {
    public static final String BUSINESS_NAME = "课程内容";
    @Resource
    private Course_ContentService  course_ContentService;


    /***
    * 列表查询
    * @param pageDto
    * @return
    */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        return ResponseDto.builder().success(true).content( course_ContentService.selectAll(pageDto)).build();
    }

    /**
    * 增加数据或者更新数据
    * @param  course_ContentDto
    * @return
    */

    @PostMapping("/save")
    public ResponseDto save(@RequestBody Course_ContentDto  course_ContentDto){
        ValidatorUtil.require(course_ContentDto.getContent(), "课程内容");
        ResponseDto responseDto = new ResponseDto();
        course_ContentService.save(course_ContentDto);
        return ResponseDto.builder().success(true).content( course_ContentDto).build();
    }


    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         course_ContentService.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }


}
