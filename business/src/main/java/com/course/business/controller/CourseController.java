package com.course.business.controller;

import com.course.service.domain.dto.*;

import com.course.service.domain.entity.Course;
import com.course.service.domain.entity.CourseContent;
import com.course.service.service.CategoryService;
import com.course.service.service.CourseCategoryService;
import com.course.service.service.CourseService;
import com.course.service.service.Course_ContentService;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course")

public class CourseController {
    public static final String BUSINESS_NAME = "课程";
    @Resource
    private CourseService courseService;
    @Resource
    private CourseCategoryService courseCategoryService;
    @Resource
    private Course_ContentService contentService;
    /***
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
       courseService.selectAll(pageDto);

        return ResponseDto.builder().success(true).content(courseService.selectAll(pageDto)).build();
    }

    /**
     * 增加数据或者更新数据
     *
     * @param courseDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto) {
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
        ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);
        courseService.save(courseDto);
        return ResponseDto.builder().success(true).content(courseDto).build();
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id) {

        courseService.delete(id);
        return ResponseDto.builder().success(true).content(id).build();
    }
    @PostMapping("/list-category/{courseId}")
    public ResponseDto listCategory(@PathVariable(value = "courseId") String courseId) {
        ResponseDto responseDto = new ResponseDto();
        List<CourseCategoryDto> dtoList = courseCategoryService.listByCourse(courseId);
        responseDto.setContent(dtoList);
        return responseDto;
    }
    @PostMapping("/find-content/{courseId}")
    public ResponseDto findcontent(@PathVariable(value = "courseId") String courseId) {
        ResponseDto responseDto = new ResponseDto();
        CourseContent bycCourseId = contentService.findBycCourseId(courseId);
        responseDto.setContent(bycCourseId);
        return responseDto;
    }
    @PostMapping("/save-content")
    public ResponseDto saveContent(@RequestBody Course_ContentDto contentDto) {
        ResponseDto responseDto = new ResponseDto();
        contentService.save(contentDto);
        return responseDto;
    }
    @RequestMapping(value = "/sort")
    public ResponseDto sort(@RequestBody SortDto sortDto) {
        ResponseDto responseDto = new ResponseDto();
        courseService.sort(sortDto);
        return responseDto;
    }

}
