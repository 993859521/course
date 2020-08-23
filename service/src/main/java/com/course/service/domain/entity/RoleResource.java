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
@Table(name = "role_resource")
public class RoleResource {
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
     * 资源|id
     */
    @Column(name = "resource_id")
    private String resourceId;
}