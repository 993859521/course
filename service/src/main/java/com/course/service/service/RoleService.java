package com.course.service.service;

import com.course.service.domain.dto.*;
import com.course.service.domain.entity.RoleResource;
import com.course.service.domain.entity.RoleUser;
import com.course.service.dto.RoleMapper;
import com.course.service.domain.entity.Role;
import com.course.service.dto.RoleResourceMapper;
import com.course.service.dto.RoleUserMapper;
import com.course.service.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.util.StringUtil;
import com.course.service.util.UuidUtil;

import java.util.ArrayList;
import java.util.List;

import static com.course.service.util.UuidUtil.getShortUuid;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleService {
    private final RoleMapper roleMapper;
    private final RoleUserMapper roleUserMapper;
    private final RoleResourceMapper roleResourceMapper;


    /**
     * 列表查询
     *
     * @param pageDto
     * @return
     */
    public PageDto selectAll(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<RoleDto> roleDtos = new ArrayList<RoleDto>();
        List<Role> roles = roleMapper.selectAll();
        PageInfo pageInfo = new PageInfo<>(roles);
        pageDto.setTotal(pageInfo.getTotal());
        for (int i = 0; i < roles.size(); i++) {
            Role role = roles.get(i);
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtos.add(roleDto);
        }
        pageDto.setList(roleDtos);
        return pageDto;
    }


    /**
     * 增加数据或者更新数据
     *
     * @param roleDto
     */
    public void save(RoleDto roleDto) {
        Role role = CopyUtil.copy(roleDto, Role.class);

        if (StringUtil.isEmpty(role.getId())) {
            role.setId(UuidUtil.getShortUuid());
            roleMapper.insert(role);
        } else {
            roleMapper.updateByPrimaryKey(role);
        }
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public void delete(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }
    /**
     * 按角色保存用户
     */
    public void saveUser(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> userIdList = roleDto.getUserIds();
        roleUserMapper.delete(RoleUser.builder().roleId(roleId).build());
        // 保存角色用户
        for (int i = 0; i < userIdList.size(); i++) {
            RoleUser roleUser = new RoleUser();
            roleUser.setId(UuidUtil.getShortUuid());
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userIdList.get(i));
            roleUserMapper.insert(roleUser);
        }
    }
    /**
     * 按角色保存资源
     */
    public void saveResource(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> resourceIds = roleDto.getResourceIds();
        // 清空库中所有的当前角色下的记录
        roleResourceMapper.delete(RoleResource.builder().roleId(roleId).build());

        // 保存角色资源
        for (int i = 0; i < resourceIds.size(); i++) {
            RoleResource roleResource = new RoleResource();
            roleResource.setId(UuidUtil.getShortUuid());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceIds.get(i));
            roleResourceMapper.insert(roleResource);
        }
    }
    /**
     * 按角色加载用户
     * @param roleId
     */
    public List<String> listUser(String roleId) {
        List<RoleUser> roleUserList = roleUserMapper.select(RoleUser.builder().roleId(roleId).build());
        List<String> userIdList = new ArrayList<>();
        for (int i = 0, l = roleUserList.size(); i < l; i++) {
            userIdList.add(roleUserList.get(i).getUserId());
        }
        return userIdList;
    }
    /**
     * 按角色加载资源
     * @param roleId
     */
    public List<String> listResource(String roleId) {

        List<RoleResource> roleResourceList = roleResourceMapper.select(RoleResource.builder().roleId(roleId).build());
        List<String> resourceIdList = new ArrayList<>();
        for (int i = 0, l = roleResourceList.size(); i < l; i++) {
            resourceIdList.add(roleResourceList.get(i).getResourceId());
        }
        return resourceIdList;
    }
}
