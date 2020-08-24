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
@Table(name = "member_course")
public class MemberCourse {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 会员id
     */
    @Column(name = "member_id")
    private String memberId;

    /**
     * 课程id
     */
    @Column(name = "course_id")
    private String courseId;

    /**
     * 报名时间
     */
    private Date at;
}