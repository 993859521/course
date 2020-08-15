package com.course.business.controller;

import com.course.service.domain.dto.SectionDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.dto.CourseMapper;
import com.course.service.service.SectionService;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/section")

public class SectionController {
    public static final String BUSINESS_NAME = "小节";
    @Resource
    private SectionService  sectionService;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        return ResponseDto.builder().success(true).content( sectionService.selectAll(pageDto)).build();
    }

    /**
    * 增加数据或者更新数据
    * @param  sectionDto
    * @return
    */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody SectionDto  sectionDto){
         ValidatorUtil.require(sectionDto.getTitle(), "标题");
         ValidatorUtil.length(sectionDto.getTitle(), "标题", 1, 50);
         ValidatorUtil.length(sectionDto.getVideo(), "视频", 1, 200);
         ResponseDto responseDto = new ResponseDto();
         sectionService.save(sectionDto);

         return ResponseDto.builder().success(true).content( sectionDto).build();
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         sectionService.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }


}
