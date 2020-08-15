package com.course.service.domain.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "course_content")
public class CourseContent {
    /**
     * 课程id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 课程内容
     */
    private String content;
}