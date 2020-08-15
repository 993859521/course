package com.course.service.service;
import com.course.service.domain.entity.Category;
import com.course.service.domain.entity.Course;
import com.course.service.dto.CourseMapper;
import com.course.service.dto. SectionMapper;
import com.course.service.domain.dto. SectionDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity. Section;
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
        import java.util.Date;
@Slf4j
@Service
@RequiredArgsConstructor( onConstructor = @__(@Autowired))
public class  SectionService {
    private final  SectionMapper  sectionMapper;
    private final CourseService courseService;
/**
* 列表查询
* @param pageDto
* @return
*/
public PageDto selectAll(PageDto pageDto){
PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
List< SectionDto>  sectionDtos = new ArrayList< SectionDto>();
        List< Section>  sections =  sectionMapper.select(Section.builder().chapterId(pageDto.getChapterId()).courseId(pageDto.getCourseId()).build());
            PageInfo pageInfo = new PageInfo<>( sections);
            pageDto.setTotal(pageInfo.getTotal());
            for (int i = 0; i <  sections.size(); i++) {
             Section  section =  sections.get(i);
             SectionDto  sectionDto=new  SectionDto();
            BeanUtils.copyProperties( section, sectionDto);
             sectionDtos.add( sectionDto);
            }
            pageDto.setList( sectionDtos);
            return pageDto;
            }

            /**
            * 增加数据或者更新数据
            * @param  sectionDto
            */
            public void save( SectionDto  sectionDto){
             Section  section = CopyUtil.copy( sectionDto,  Section.class);
                if(StringUtil.isEmpty( section.getId())){
                        Date now = new Date();
                        section.setCreatedAt(now);
                        section.setUpdatedAt(now);
                section.setId(UuidUtil.getShortUuid());
                sectionMapper.insert(section);
            }else{
             sectionMapper.updateByPrimaryKey(section);
            }
                List<Section> select = sectionMapper.select(Section.builder().courseId(section.getCourseId()).build());
                Integer time=new Integer(0);
                for (Section item:select) {
                    time=time+item.getTime();
                }
                log.info("CourseId"+section.getCourseId()+"time:"+time.toString());
                courseService.save_time(section.getCourseId(),time);
            }

            /**
            * 删除数据
            * @param id
            */
            public void delete(String id){
                Section section = sectionMapper.selectByPrimaryKey(id);
                courseService.del_time(section.getCourseId(),section.getTime());
                sectionMapper.deleteByPrimaryKey(id);
            }

            }
