package com.course.business.controller;

import com.course.service.domain.dto.RoleResourceDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.service.RoleResourceService;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/roleResource")

public class RoleResourceController {
    public static final String BUSINESS_NAME = "角色资源关联";
    @Resource
    private RoleResourceService  roleResourceService;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        return ResponseDto.builder().success(true).content( roleResourceService.selectAll(pageDto)).build();
    }

    /**
    * 增加数据或者更新数据
    * @param  roleResourceDto
    * @return
    */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody RoleResourceDto  roleResourceDto){
         ValidatorUtil.require(roleResourceDto.getRoleId(), "角色");
         ValidatorUtil.require(roleResourceDto.getResourceId(), "资源");
         ResponseDto responseDto = new ResponseDto();
         roleResourceService.save( roleResourceDto);
         return ResponseDto.builder().success(true).content( roleResourceDto).build();
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         roleResourceService.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }

}
