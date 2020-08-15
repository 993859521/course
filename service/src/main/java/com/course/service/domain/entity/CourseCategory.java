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
@Table(name = "course_category")
public class CourseCategory {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 课程|course.id
     */
    @Column(name = "course_id")
    private String courseId;

    /**
     * 分类|course.id
     */
    @Column(name = "category_id")
    private String categoryId;
}