package com.course.service.service;
import com.course.service.dto. RoleUserMapper;
import com.course.service.domain.dto. RoleUserDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity. RoleUser;
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
public class  RoleUserService {
private final  RoleUserMapper  roleUserMapper;
/**
* 列表查询
* @param pageDto
* @return
*/
public PageDto selectAll(PageDto pageDto){
PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
List< RoleUserDto>  roleUserDtos = new ArrayList< RoleUserDto>();
        List< RoleUser>  roleUsers =  roleUserMapper.selectAll();
            PageInfo pageInfo = new PageInfo<>( roleUsers);
            pageDto.setTotal(pageInfo.getTotal());
            for (int i = 0; i <  roleUsers.size(); i++) {
             RoleUser  roleUser =  roleUsers.get(i);
             RoleUserDto  roleUserDto=new  RoleUserDto();
            BeanUtils.copyProperties( roleUser, roleUserDto);
             roleUserDtos.add( roleUserDto);
            }
            pageDto.setList( roleUserDtos);
            return pageDto;
            }

            /**
            * 增加数据或者更新数据
            * @param  roleUserDto
            */
            public void save( RoleUserDto  roleUserDto){
             RoleUser  roleUser = CopyUtil.copy( roleUserDto,  RoleUser.class);

            if(StringUtil.isEmpty( roleUser.getId())){
                roleUser.setId(UuidUtil.getShortUuid());
                roleUserMapper.insert(roleUser);
            }else{
             roleUserMapper.updateByPrimaryKey( roleUser);
            }
            }

            /**
            * 删除数据
            * @param id
            */
            public void delete(String id){
             roleUserMapper.deleteByPrimaryKey(id);
            }

            }
