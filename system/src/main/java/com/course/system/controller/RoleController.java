package com.course.business.controller;

import com.course.service.domain.dto.RoleDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.service.RoleService;
import com.course.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/role")
@Slf4j
public class RoleController {
    public static final String BUSINESS_NAME = "角色";
    @Resource
    private RoleService  roleService;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        return ResponseDto.builder().success(true).content( roleService.selectAll(pageDto)).build();
    }

    /**
    * 增加数据或者更新数据
    * @param  roleDto
    * @return
    */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody RoleDto  roleDto){
         ValidatorUtil.require(roleDto.getName(), "角色");
         ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
         ValidatorUtil.require(roleDto.getDes(), "描述");
         ValidatorUtil.length(roleDto.getDes(), "描述", 1, 100);
         ResponseDto responseDto = new ResponseDto();
         roleService.save( roleDto);
         return ResponseDto.builder().success(true).content( roleDto).build();
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         roleService.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }
    @PostMapping("/save-user")
    public ResponseDto saveUser(@RequestBody RoleDto roleDto) {
        log.info("保存角色用户关联开始");
        ResponseDto<RoleDto> responseDto = new ResponseDto<>();
        roleService.saveUser(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 保存资源
     * @param roleDto
     */
    @PostMapping("/save-resource")
    public ResponseDto saveResource(@RequestBody RoleDto roleDto) {
        log.info("保存角色资源关联开始");
        ResponseDto<RoleDto> responseDto = new ResponseDto<>();
        roleService.saveResource(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }
    /**
     * 加载用户
     * @param roleId
     */
    @GetMapping("/list-user/{roleId}")
    public ResponseDto listUser(@PathVariable String roleId) {
        log.info("加载用户开始");
        ResponseDto responseDto = new ResponseDto<>();
        List<String> userIdList = roleService.listUser(roleId);
        responseDto.setContent(userIdList);
        return responseDto;
    }
    /**
     * 加载已关联的资源
     */
    @GetMapping("/list-resource/{roleId}")
    public ResponseDto listResource(@PathVariable String roleId) {
        log.info("加载资源开始");
        ResponseDto responseDto = new ResponseDto<>();
        List<String> resourceIdList = roleService.listResource(roleId);
        responseDto.setContent(resourceIdList);
        return responseDto;
    }

}
