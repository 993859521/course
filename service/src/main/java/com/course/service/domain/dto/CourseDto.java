package com.course.service.domain.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.course.service.domain.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto{

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 概述
     */
    private String summary;

    /**
     * 时长|单位秒
     */
    private Integer time;

    /**
     * 价格（元）
     */
    private BigDecimal price;

    /**
     * 封面
     */
    private String image;

    /**
     * 级别|枚举[CourseLevelEnum]：ONE("1", "初级"),TWO("2", "中级"),THREE("3", "高级")
     */
    private String level;

    /**
     * 收费|枚举[CourseChargeEnum]：CHARGE("C", "收费"),FREE("F", "免费")
     */
    private String charge;

    /**
     * 状态|枚举[CourseStatusEnum]：PUBLISH("P", "发布"),DRAFT("D", "草稿")
     */
    private String status;

    /**
     * 报名数
     */
    private Integer enroll;

    /**
     * 顺序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private List<CategoryDto> categorys;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    private List<Teacher> teacher;


}