package com.course.service.service;

import com.course.service.dto.MemberCourseMapper;
import com.course.service.domain.dto.MemberCourseDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity.MemberCourse;
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

import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberCourseService {
    private final MemberCourseMapper memberCourseMapper;

    /**
     * 列表查询
     *
     * @param pageDto
     * @return
     */
    public PageDto selectAll(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<MemberCourseDto> memberCourseDtos = new ArrayList<MemberCourseDto>();
        List<MemberCourse> memberCourses = memberCourseMapper.selectAll();
        PageInfo pageInfo = new PageInfo<>(memberCourses);
        pageDto.setTotal(pageInfo.getTotal());
        for (int i = 0; i < memberCourses.size(); i++) {
            MemberCourse memberCourse = memberCourses.get(i);
            MemberCourseDto memberCourseDto = new MemberCourseDto();
            BeanUtils.copyProperties(memberCourse, memberCourseDto);
            memberCourseDtos.add(memberCourseDto);
        }
        pageDto.setList(memberCourseDtos);
        return pageDto;
    }

    /**
     * 增加数据或者更新数据
     *
     * @param memberCourseDto
     */
    public void save(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourse = CopyUtil.copy(memberCourseDto, MemberCourse.class);

        if (StringUtil.isEmpty(memberCourse.getId())) {
            Date now = new Date();
            memberCourse.setId(UuidUtil.getShortUuid());
            memberCourseMapper.insert(memberCourse);
        } else {
            memberCourseMapper.updateByPrimaryKey(memberCourse);
        }
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public void delete(String id) {
        memberCourseMapper.deleteByPrimaryKey(id);
    }
    /**
     * 报名，先判断是否已报名
     * @param memberCourseDto
     */
    public MemberCourseDto enroll(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourseDb = memberCourseMapper.selectOne(MemberCourse.builder().memberId(memberCourseDto.getMemberId()).courseId(memberCourseDto.getCourseId()).build());
        if (memberCourseDb == null) {
            Date now = new Date();
            MemberCourse memberCourse = CopyUtil.copy(memberCourseDto, MemberCourse.class);
            memberCourse.setId(UuidUtil.getShortUuid());
            memberCourse.setAt(now);
            memberCourseMapper.insert(memberCourse);
            // 将数据库信息全部返回，包括id, at
            return CopyUtil.copy(memberCourse, MemberCourseDto.class);
        } else {
            // 如果已经报名，则直接返回已报名的信息
            return CopyUtil.copy(memberCourseDb, MemberCourseDto.class);
        }

    }

    /**
     * 获取报名信息
     */
    public MemberCourseDto getEnroll(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourse = memberCourseMapper.selectOne(MemberCourse.builder().memberId(memberCourseDto.getMemberId()).courseId(memberCourseDto.getCourseId()).build());
        return CopyUtil.copy(memberCourse, MemberCourseDto.class);
    }

}
