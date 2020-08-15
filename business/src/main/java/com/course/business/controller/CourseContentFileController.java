package com.course.business.controller;

import com.course.service.domain.dto.CourseCategoryDto;
import com.course.service.domain.dto.CourseContentFileDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.domain.entity.CourseContentFile;
import com.course.service.service.CourseContentFileService;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course-content-file")

public class CourseContentFileController {
    public static final String BUSINESS_NAME = "课程内容文件";
    @Resource
    private CourseContentFileService  courseContentFileService;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */

    /**
    * 增加数据或者更新数据
    * @param  courseContentFileDto
    * @return
    */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseContentFileDto  courseContentFileDto){
         ValidatorUtil.require(courseContentFileDto.getCourseId(), "课程id");
         ValidatorUtil.length(courseContentFileDto.getUrl(), "地址", 1, 100);
         ValidatorUtil.length(courseContentFileDto.getName(), "文件名", 1, 100);
         ResponseDto responseDto = new ResponseDto();
         courseContentFileService.save( courseContentFileDto);
         return ResponseDto.builder().success(true).content( courseContentFileDto).build();
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         courseContentFileService.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }
    @PostMapping("/list/{courseId}")
    public ResponseDto listCategory(@PathVariable(value = "courseId") String courseId) {
        ResponseDto responseDto = new ResponseDto();
        List<CourseContentFile> dtoList = courseContentFileService.findBycCourseId(courseId);
        responseDto.setContent(dtoList);
        return responseDto;
    }

}
