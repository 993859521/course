package com.course.service.service;
import com.course.service.domain.dto.CategoryDto;
import com.course.service.domain.entity.Category;
import com.course.service.dto. TeacherMapper;
import com.course.service.domain.dto. TeacherDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity. Teacher;
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

import static com.course.service.util.UuidUtil.getShortUuid;
@Slf4j
@Service
@RequiredArgsConstructor( onConstructor = @__(@Autowired))
public class  TeacherService {
private final  TeacherMapper  teacherMapper;
/**
* 列表查询
* @param pageDto
* @return
*/
public PageDto selectAll(PageDto pageDto){
PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
List< TeacherDto>  teacherDtos = new ArrayList< TeacherDto>();
        List< Teacher>  teachers =  teacherMapper.selectAll();
            PageInfo pageInfo = new PageInfo<>( teachers);
            pageDto.setTotal(pageInfo.getTotal());
            for (int i = 0; i <  teachers.size(); i++) {
             Teacher  teacher =  teachers.get(i);
             TeacherDto  teacherDto=new  TeacherDto();
            BeanUtils.copyProperties( teacher, teacherDto);
             teacherDtos.add(teacherDto);
            }
            pageDto.setList( teacherDtos);
            return pageDto;
            }
    public  Teacher selectAll(String id){

        Teacher teacher1 = teacherMapper.selectByPrimaryKey(id);
        return teacher1;
    }

            /**
            * 增加数据或者更新数据
            * @param  teacherDto
            */
            public void save( TeacherDto  teacherDto){
             Teacher  teacher = CopyUtil.copy( teacherDto,  Teacher.class);

            if(StringUtil.isEmpty( teacher.getId())){
                teacher.setId(UuidUtil.getShortUuid());
                teacherMapper.insert(teacher);
            }else{
             teacherMapper.updateByPrimaryKey( teacher);
            }
            }

            /**
            * 删除数据
            * @param id
            */
            public void delete(String id){
             teacherMapper.deleteByPrimaryKey(id);
            }
    public List<TeacherDto> all() {
        List<TeacherDto> teacherDtos = new ArrayList<TeacherDto>();
        List<Teacher> teachers = teacherMapper.selectAll();

        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            TeacherDto teacherDto = new TeacherDto();
            BeanUtils.copyProperties(teacher, teacherDto);
            teacherDtos.add(teacherDto);
        }
        return teacherDtos;
    }

            }
