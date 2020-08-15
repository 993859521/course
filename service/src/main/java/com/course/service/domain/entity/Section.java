package com.course.service.domain.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "section")
public class Section {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 课程|course.id
     */
    @Column(name = "course_id")
    private String courseId;

    /**
     * 大章|chapter.id
     */
    @Column(name = "chapter_id")
    private String chapterId;

    /**
     * 视频
     */
    private String video;

    /**
     * 时长|单位秒
     */
    private Integer time;

    /**
     * 收费|C 收费；F 免费
     */
    private String charge;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 修改时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;
}