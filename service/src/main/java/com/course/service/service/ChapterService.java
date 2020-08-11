package com.course.service.service;

import com.course.service.dao.ChapterMapper;
import com.course.service.domain.dao.ChapterDto;
import com.course.service.domain.entity.Chapter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor( onConstructor = @__(@Autowired))
public class ChapterService {
    private final ChapterMapper chapterMapper;

    public List<ChapterDto> selectAll(){
        List<ChapterDto> chapterDtos = new ArrayList<ChapterDto>();
        List<Chapter> chapters = chapterMapper.selectAll();
        for (int i = 0; i < chapters.size(); i++) {
            Chapter chapter = chapters.get(i);
            chapterDtos.add(ChapterDto.builder()
                    .id(chapter.getId())
                    .courseId(chapter.getCourseId())
                    .name(chapter.getName())
                    .build()
            );
        }
        return chapterDtos;
    }


}
