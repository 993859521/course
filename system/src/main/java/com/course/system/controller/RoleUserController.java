package com.course.business.controller;

import com.course.service.domain.dto.RoleUserDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.service.RoleUserService;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/roleUser")

public class RoleUserController {
    public static final String BUSINESS_NAME = "角色用户关联";
    @Resource
    private RoleUserService  roleUserService;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        return ResponseDto.builder().success(true).content( roleUserService.selectAll(pageDto)).build();
    }

    /**
    * 增加数据或者更新数据
    * @param  roleUserDto
    * @return
    */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody RoleUserDto  roleUserDto){
         ValidatorUtil.require(roleUserDto.getRoleId(), "角色");
         ValidatorUtil.require(roleUserDto.getUserId(), "用户");
         ResponseDto responseDto = new ResponseDto();
         roleUserService.save( roleUserDto);
         return ResponseDto.builder().success(true).content( roleUserDto).build();
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         roleUserService.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }

}
