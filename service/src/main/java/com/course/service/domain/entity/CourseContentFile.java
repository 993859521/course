package com.course.service.domain.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_content_file")
public class CourseContentFile {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 课程id
     */
    @Column(name = "course_id")
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