package com.course.web.controller;

import com.course.service.domain.dto.CategoryDto;
import com.course.service.domain.dto.ResponseDto;
import com.course.service.service.CategoryService;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/category")

public class CategoryController {
    public static final String BUSINESS_NAME = "分类";
    @Resource
    private CategoryService  categoryService;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */

    @PostMapping("/all")
    public ResponseDto all(){
        return ResponseDto.builder().success(true).content( categoryService.all()).build();
    }

    /**
    * 增加数据或者更新数据
    * @param  categoryDto
    * @return
    */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CategoryDto  categoryDto){
         ValidatorUtil.require(categoryDto.getParent(), "父id");
         ValidatorUtil.require(categoryDto.getName(), "名称");
         ValidatorUtil.length(categoryDto.getName(), "名称", 1, 50);
         categoryService.save( categoryDto);
         return ResponseDto.builder().success(true).content( categoryDto).build();
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         categoryService.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }

}
