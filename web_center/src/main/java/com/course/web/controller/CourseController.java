package com.course.web.controller;

import com.course.service.domain.dto.*;
import com.course.service.domain.entity.CourseContent;
import com.course.service.enums.CourseStatusEnum;
import com.course.service.service.CourseCategoryService;
import com.course.service.service.CourseService;
import com.course.service.service.Course_ContentService;
import com.course.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/course")

public class CourseController {
    public static final String BUSINESS_NAME = "课程";
    @Resource
    private CourseService courseService;
    @GetMapping("/list-new")
    public ResponseDto listNew() {
        PageDto pageDto = new PageDto();
        pageDto.setPage(1);
        pageDto.setSize(3);
        ResponseDto responseDto = new ResponseDto();
        List<CourseDto> courseDtoList = courseService.listNew(pageDto);
        responseDto.setContent(courseDtoList);
        return responseDto;
    }
    @PostMapping("/list")
    public ResponseDto list(@RequestBody CoursePageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        pageDto.setStatus(CourseStatusEnum.PUBLISH.getCode());
        courseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @GetMapping("/find/{id}")
    public ResponseDto findCourse(@PathVariable String id) {
        log.info("查找课程开始：{}", id);
        ResponseDto responseDto = new ResponseDto();
        CourseDto courseDto = courseService.findCourse(id);
        responseDto.setContent(courseDto);
        log.info("查找课程结束：{}", responseDto);
        return responseDto;
    }


}
