package com.course.service.service;

import com.course.service.domain.dto.LoginUserDto;
import com.course.service.dto.MemberMapper;
import com.course.service.domain.dto.MemberDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity.Member;
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
import tk.mybatis.mapper.util.StringUtil;
import com.course.service.util.UuidUtil;

import java.util.ArrayList;
import java.util.List;


import java.util.Date;
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberService {
    private final MemberMapper memberMapper;

    /**
     * 列表查询
     *
     * @param pageDto
     * @return
     */
    public PageDto selectAll(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<MemberDto> memberDtos = new ArrayList<MemberDto>();
        List<Member> members = memberMapper.selectAll();
        PageInfo pageInfo = new PageInfo<>(members);
        pageDto.setTotal(pageInfo.getTotal());
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            MemberDto memberDto = new MemberDto();
            BeanUtils.copyProperties(member, memberDto);
            memberDtos.add(memberDto);
        }
        pageDto.setList(memberDtos);
        return pageDto;
    }

    /**
     * 增加数据或者更新数据
     *
     * @param memberDto
     */
    public void save(MemberDto memberDto) {
        Member member = CopyUtil.copy(memberDto, Member.class);

        if (StringUtil.isEmpty(member.getId())) {
            Date now = new Date();
            member.setId(UuidUtil.getShortUuid());
            member.setRegisterTime(now);
            memberMapper.insert(member);
        } else {
            memberMapper.updateByPrimaryKey(member);
        }
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public void delete(String id) {
        memberMapper.deleteByPrimaryKey(id);
    }
    public LoginUserDto login(MemberDto memberDto) {
        Member member=memberMapper.selectOne(Member.builder().mobile(memberDto.getMobile()).build());
        if (member == null) {
            log.info("用户名不存在, {}", memberDto.getMobile());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (member.getPassword().equals(memberDto.getPassword())) {
                // 登录成功
                LoginUserDto loginUserDto = CopyUtil.copy(member, LoginUserDto.class);
                return loginUserDto;
            } else {
                log.info("密码不对, 输入密码：{}, 数据库密码：{}", memberDto.getPassword(), member.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }

}
