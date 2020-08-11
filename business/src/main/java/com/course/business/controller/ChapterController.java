package com.course.business.controller;

import com.course.service.domain.dao.ChapterDto;
import com.course.service.service.ChapterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")

public class ChapterController {
    @Resource
    private ChapterService chapterService;
    @GetMapping("/list")
    public List<ChapterDto> list(){
        return chapterService.selectAll();
    }
    @GetMapping("/1")
    public String L(){
        return "chapterService.selectAll()";
    }
}
