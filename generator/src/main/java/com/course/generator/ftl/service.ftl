package com.course.service.service;
import com.course.service.dto. ${Domain}Mapper;
import com.course.service.domain.dto. ${Domain}Dto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity. ${Domain};
import com.course.service.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;
import com.course.service.util.UuidUtil;
import java.util.ArrayList;
import java.util.List;

import static com.course.service.util.UuidUtil.getShortUuid;
<#list typeSet as type>
    <#if type=='Date'>
        import java.util.Date;
    </#if>
</#list>

@Service
@RequiredArgsConstructor( onConstructor = @__(@Autowired))
public class  ${Domain}Service {
private final  ${Domain}Mapper  ${domain}Mapper;
/**
* 列表查询
* @param pageDto
* @return
*/
public PageDto selectAll(PageDto pageDto){
PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
List< ${Domain}Dto>  ${domain}Dtos = new ArrayList< ${Domain}Dto>();
        List< ${Domain}>  ${domain}s =  ${domain}Mapper.selectAll();
            PageInfo pageInfo = new PageInfo<>( ${domain}s);
            pageDto.setTotal(pageInfo.getTotal());
            for (int i = 0; i <  ${domain}s.size(); i++) {
             ${Domain}  ${domain} =  ${domain}s.get(i);
             ${Domain}Dto  ${domain}Dto=new  ${Domain}Dto();
            BeanUtils.copyProperties( ${domain}, ${domain}Dto);
             ${domain}Dtos.add( ${domain}Dto);
            }
            pageDto.setList( ${domain}Dtos);
            return pageDto;
            }

            /**
            * 增加数据或者更新数据
            * @param  ${domain}Dto
            */
            public void save( ${Domain}Dto  ${domain}Dto){
             ${Domain}  ${domain} = CopyUtil.copy( ${domain}Dto,  ${Domain}.class);

            if(StringUtil.isEmpty( ${domain}.getId())){
                <#list typeSet as type>
                    <#if type=='Date'>
                        Date now = new Date();
                    </#if>
                </#list>
                <#list fieldList as field>
                    <#if field.nameHump=='createdAt'>
                        ${domain}.setCreatedAt(now);
                    </#if>
                    <#if field.nameHump=='updatedAt'>
                        ${domain}.setUpdatedAt(now);
                    </#if>
                </#list>
                ${domain}.setId(UuidUtil.getShortUuid());
                ${domain}Mapper.insert(${domain});
            }else{
             ${domain}Mapper.updateByPrimaryKey( ${domain});
            }
            }

            /**
            * 删除数据
            * @param id
            */
            public void delete(String id){
             ${domain}Mapper.deleteByPrimaryKey(id);
            }

            }
