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
@Table(name = "role_user")
public class RoleUser {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 角色|id
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 用户|id
     */
    @Column(name = "user_id")
    private String userId;
}