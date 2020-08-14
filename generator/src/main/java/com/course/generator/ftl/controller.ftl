package com.course.business.controller;

import com.course.service.domain.dto.${Domain}Dto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.service.${Domain}Service;
import com.course.service.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/${domain}")

public class ${Domain}Controller {
    public static final String BUSINESS_NAME = "${tableNameCn}";
    @Resource
    private ${Domain}Service  ${domain}Service;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        return ResponseDto.builder().success(true).content( ${domain}Service.selectAll(pageDto)).build();
    }

    /**
    * 增加数据或者更新数据
    * @param  ${domain}Dto
    * @return
    */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ${Domain}Dto  ${domain}Dto){
            <#list fieldList as field>
                <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
                    <#if !field.nullAble>
         ValidatorUtil.require(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}");
                </#if>
        <#if (field.length > 0)>
         ValidatorUtil.length(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}", 1, ${field.length?c});
                </#if>
            </#if>
            </#list>
         ResponseDto responseDto = new ResponseDto();
         ${domain}Service.save( ${domain}Dto);
         return ResponseDto.builder().success(true).content( ${domain}Dto).build();
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         ${domain}Service.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }

}
