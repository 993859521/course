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
@Table(name = "chapter")
public class Chapter {
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
     * 名称
     */
    private String name;
    private String teacherId;
}