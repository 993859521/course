package com.course.service.service;

import com.alibaba.fastjson.JSON;
import com.course.service.domain.dto.LoginUserDto;
import com.course.service.domain.dto.ResourceDto;
import com.course.service.dto.MyUserMapper;
import com.course.service.dto.UserMapper;
import com.course.service.domain.dto.UserDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity.User;
import com.course.service.exception.BusinessException;
import com.course.service.exception.BusinessExceptionCode;
import com.course.service.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.util.StringUtil;
import com.course.service.util.UuidUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserMapper userMapper;
    private final MyUserMapper myUserMapper;

    /**
     * 列表查询
     *
     * @param pageDto
     * @return
     */
    public PageDto selectAll(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<UserDto> userDtos = new ArrayList<UserDto>();
        List<User> users = userMapper.selectAll();
        PageInfo pageInfo = new PageInfo<>(users);
        pageDto.setTotal(pageInfo.getTotal());
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtos.add(userDto);
        }
        pageDto.setList(userDtos);
        return pageDto;
    }

    /**
     * 增加数据或者更新数据
     *
     * @param userDto
     */
    public void save(UserDto userDto) {
        User user = CopyUtil.copy(userDto, User.class);

        if (StringUtil.isEmpty(user.getId())) {
            user.setId(UuidUtil.getShortUuid());
            userMapper.insert(user);
        } else {
            userMapper.updateByPrimaryKey(user);
        }
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 重置密码
     *
     * @param userDto
     */
    public void savePassword(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }
    /**
     * 登录
     * @param userDto
     */
    public LoginUserDto login(UserDto userDto) {
        User user=userMapper.selectOne(User.builder().loginName(userDto.getLoginName()).build());
        if (user == null) {
            log.info("用户名不存在, {}", userDto.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (user.getPassword().equals(userDto.getPassword())) {
                // 登录成功
                LoginUserDto loginUserDto = CopyUtil.copy(user, LoginUserDto.class);
                // 为登录用户读取权限
                setAuth(loginUserDto);
                return loginUserDto;
            } else {
                log.info("密码不对, 输入密码：{}, 数据库密码：{}", userDto.getPassword(), user.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }
    /**
     * 为登录用户读取权限
     */
    private void setAuth(LoginUserDto loginUserDto) {
        List<ResourceDto> resourceDtoList = myUserMapper.findResources(loginUserDto.getId());
        loginUserDto.setResources(resourceDtoList);

        // 整理所有有权限的请求，用于接口拦截
        HashSet<String> requestSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(resourceDtoList)) {
            for (int i = 0, l = resourceDtoList.size(); i < l; i++) {
                ResourceDto resourceDto = resourceDtoList.get(i);
                String arrayString = resourceDto.getRequest();
                List<String> requestList = JSON.parseArray(arrayString, String.class);
                if (!CollectionUtils.isEmpty(requestList)) {
                    requestSet.addAll(requestList);
                }
            }
        }
        log.info("有权限的请求：{}", requestSet);
        loginUserDto.setRequests(requestSet);
    }

}
