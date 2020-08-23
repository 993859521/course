package com.course.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUserDto {

    /**
     * id
     */
    private String id;

    /**
     * 角色|id
     */
    private String roleId;

    /**
     * 用户|id
     */
    private String userId;



}