package com.course.business.controller;

import com.course.service.domain.dto.CourseCategoryDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.service.CourseCategoryService;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/courseCategory")

public class CourseCategoryController {
    public static final String BUSINESS_NAME = "课程分类";
    @Resource
    private CourseCategoryService  courseCategoryService;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        return ResponseDto.builder().success(true).content( courseCategoryService.selectAll(pageDto)).build();
    }

    /**
    * 增加数据或者更新数据
    * @param  courseCategoryDto
    * @return
    */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseCategoryDto  courseCategoryDto){
         ResponseDto responseDto = new ResponseDto();
         courseCategoryService.save( courseCategoryDto);
         return ResponseDto.builder().success(true).content( courseCategoryDto).build();
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         courseCategoryService.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }

}
