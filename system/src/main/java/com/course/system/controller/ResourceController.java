package com.course.business.controller;

import com.course.service.domain.dto.ResourceDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.domain.dto.RoleDto;
import com.course.service.service.ResourceService;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/resource")

public class ResourceController {
    public static final String BUSINESS_NAME = "资源";
    @Resource
    private ResourceService  resourceService;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */


    /**
    * 增加数据或者更新数据
    * @param  resourceDto
    * @return
    */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody String jsonStr) {
        // 保存校验
        ValidatorUtil.require(jsonStr, "资源");

        ResponseDto responseDto = new ResponseDto();
        resourceService.saveJson(jsonStr);
        return responseDto;
    }


    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         resourceService.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }
    @GetMapping("/load-tree")
    public ResponseDto loadTree() {
        ResponseDto responseDto = new ResponseDto();
        List<ResourceDto> resourceDtoList = resourceService.loadTree();
        responseDto.setContent(resourceDtoList);
        return responseDto;
    }
    /**
     * 保存用户
     * @param roleDto
     */


}
