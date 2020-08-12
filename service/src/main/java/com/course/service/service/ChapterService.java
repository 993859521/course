package com.course.service.service;
import com.course.service.dto.ChapterMapper;
import com.course.service.domain.dto.ChapterDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity.Chapter;
import com.course.service.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static com.course.service.util.UuidUtil.getShortUuid;

@Service
@RequiredArgsConstructor( onConstructor = @__(@Autowired))
public class ChapterService {
    private final ChapterMapper chapterMapper;
    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    public PageDto selectAll(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        List<ChapterDto> chapterDtos = new ArrayList<ChapterDto>();
        List<Chapter> chapters = chapterMapper.selectAll();
        PageInfo pageInfo = new PageInfo<>(chapters);
        pageDto.setTotal(pageInfo.getTotal());
        for (int i = 0; i < chapters.size(); i++) {
            Chapter chapter = chapters.get(i);
            ChapterDto chapterDto=new ChapterDto();
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtos.add(chapterDto);
        }
        pageDto.setList(chapterDtos);
        return pageDto;
    }

    /**
     * 增加数据或者更新数据
     * @param chapterDto
     */
    public void save(ChapterDto chapterDto){
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);

        if(StringUtil.isEmpty(chapter.getId())){
            chapter.setId(getShortUuid());
            chapterMapper.insert(chapter);
        }else{
            chapterMapper.updateByPrimaryKey(chapter);
        }
    }

    /**
     * 删除数据
     * @param id
     */
    public void delete(String id){
        chapterMapper.deleteByPrimaryKey(id);
    }

}
