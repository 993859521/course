package com.course.business.controller;

import com.course.service.domain.dto.TeacherDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.service.TeacherService;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/teacher")

public class TeacherController {
    public static final String BUSINESS_NAME = "讲师";
    @Resource
    private TeacherService  teacherService;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */
    @PostMapping("/all")
    public ResponseDto all(){
        return ResponseDto.builder().success(true).content( teacherService.all()).build();
    }
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        return ResponseDto.builder().success(true).content( teacherService.selectAll(pageDto)).build();
    }

    /**
    * 增加数据或者更新数据
    * @param  teacherDto
    * @return
    */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody TeacherDto  teacherDto){
         ValidatorUtil.require(teacherDto.getName(), "姓名");
         ValidatorUtil.length(teacherDto.getName(), "姓名", 1, 50);
         ValidatorUtil.length(teacherDto.getNickname(), "昵称", 1, 50);
         ValidatorUtil.length(teacherDto.getImage(), "头像", 1, 100);
         ValidatorUtil.length(teacherDto.getPosition(), "职位", 1, 50);
         ValidatorUtil.length(teacherDto.getMotto(), "座右铭", 1, 50);
         ValidatorUtil.length(teacherDto.getIntro(), "简介", 1, 500);
         ResponseDto responseDto = new ResponseDto();
         teacherService.save( teacherDto);
         return ResponseDto.builder().success(true).content( teacherDto).build();
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         teacherService.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }

}
