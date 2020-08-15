package com.course.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course_ContentDto {

    /**
     * 课程id
     */
    private String id;

    /**
     * 课程内容
     */
    private String content;



}