package com.course.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseContentFileDto {

    /**
     * id
     */
    private String id;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 地址
     */
    private String url;

    /**
     * 文件名
     */
    private String name;

    /**
     * 大小|字节b
     */
    private Integer size;



}