package com.course.business.controller;

import com.course.service.domain.dto.ChapterDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.service.ChapterService;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/chapter")

public class ChapterController {
    public static final String BUSINESS_NAME = "大章";
    @Resource
    private ChapterService chapterService;

    /***
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        return ResponseDto.builder().success(true).content(chapterService.selectAll(pageDto)).build();
    }

    /**
     * 增加数据或者更新数据
     * @param chapterDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto){
        ValidatorUtil.require(chapterDto.getName(),"名称");
        ValidatorUtil.require(chapterDto.getCourseId(),"课程ID");
        ValidatorUtil.length(chapterDto.getCourseId(),"课程ID",1,8);
        ResponseDto responseDto = new ResponseDto();
        chapterService.save(chapterDto);
        return ResponseDto.builder().success(true).content(chapterDto).build();
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
        chapterService.delete(id);
        return ResponseDto.builder().success(true).content(id).build();
    }

}
