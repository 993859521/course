package com.course.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ChapterDto")
public class ChapterDto {
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
}