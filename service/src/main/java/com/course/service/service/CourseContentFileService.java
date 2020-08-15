package com.course.service.service;
import com.course.service.domain.entity.CourseContent;
import com.course.service.dto. CourseContentFileMapper;
import com.course.service.domain.dto. CourseContentFileDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity. CourseContentFile;
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
public class  CourseContentFileService {
private final  CourseContentFileMapper  courseContentFileMapper;
/**
* 列表查询
* @param pageDto
* @return
*/
public PageDto selectAll(PageDto pageDto){
PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
List< CourseContentFileDto>  courseContentFileDtos = new ArrayList< CourseContentFileDto>();
        List< CourseContentFile>  courseContentFiles =  courseContentFileMapper.selectAll();
            PageInfo pageInfo = new PageInfo<>( courseContentFiles);
            pageDto.setTotal(pageInfo.getTotal());
            for (int i = 0; i <  courseContentFiles.size(); i++) {
             CourseContentFile  courseContentFile =  courseContentFiles.get(i);
             CourseContentFileDto  courseContentFileDto=new  CourseContentFileDto();
            BeanUtils.copyProperties( courseContentFile, courseContentFileDto);
             courseContentFileDtos.add( courseContentFileDto);
            }
            pageDto.setList( courseContentFileDtos);
            return pageDto;
            }

            /**
            * 增加数据或者更新数据
            * @param  courseContentFileDto
            */
            public void save( CourseContentFileDto  courseContentFileDto){
             CourseContentFile  courseContentFile = CopyUtil.copy( courseContentFileDto,  CourseContentFile.class);

            if(StringUtil.isEmpty( courseContentFile.getId())){
                courseContentFile.setId(UuidUtil.getShortUuid());
                courseContentFileMapper.insert(courseContentFile);
            }else{
             courseContentFileMapper.updateByPrimaryKey( courseContentFile);
            }
            }

            /**
            * 删除数据
            * @param id
            */
            public void delete(String id){
             courseContentFileMapper.deleteByPrimaryKey(id);
            }
    public List<CourseContentFile> findBycCourseId(String courseId){
        List<CourseContentFile> select = courseContentFileMapper.select(CourseContentFile.builder().courseId(courseId).build());
        return select;

    }

            }
