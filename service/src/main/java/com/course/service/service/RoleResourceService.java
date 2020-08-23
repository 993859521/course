package com.course.service.service;
import com.course.service.dto. RoleResourceMapper;
import com.course.service.domain.dto. RoleResourceDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity. RoleResource;
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

@Service
@RequiredArgsConstructor( onConstructor = @__(@Autowired))
public class  RoleResourceService {
private final  RoleResourceMapper  roleResourceMapper;
/**
* 列表查询
* @param pageDto
* @return
*/
public PageDto selectAll(PageDto pageDto){
PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
List< RoleResourceDto>  roleResourceDtos = new ArrayList< RoleResourceDto>();
        List< RoleResource>  roleResources =  roleResourceMapper.selectAll();
            PageInfo pageInfo = new PageInfo<>( roleResources);
            pageDto.setTotal(pageInfo.getTotal());
            for (int i = 0; i <  roleResources.size(); i++) {
             RoleResource  roleResource =  roleResources.get(i);
             RoleResourceDto  roleResourceDto=new  RoleResourceDto();
            BeanUtils.copyProperties( roleResource, roleResourceDto);
             roleResourceDtos.add( roleResourceDto);
            }
            pageDto.setList( roleResourceDtos);
            return pageDto;
            }

            /**
            * 增加数据或者更新数据
            * @param  roleResourceDto
            */
            public void save( RoleResourceDto  roleResourceDto){
             RoleResource  roleResource = CopyUtil.copy( roleResourceDto,  RoleResource.class);

            if(StringUtil.isEmpty( roleResource.getId())){
                roleResource.setId(UuidUtil.getShortUuid());
                roleResourceMapper.insert(roleResource);
            }else{
             roleResourceMapper.updateByPrimaryKey( roleResource);
            }
            }

            /**
            * 删除数据
            * @param id
            */
            public void delete(String id){
             roleResourceMapper.deleteByPrimaryKey(id);
            }

            }
