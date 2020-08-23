package com.course.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDto {

    /**
     * id
     */
    private String id;

    /**
     * 名称|菜单或按钮
     */
    private String name;

    /**
     * 页面|路由
     */
    private String page;

    /**
     * 请求|接口
     */
    private String request;

    /**
     * 父id
     */
    private String parent;
    private List<ResourceDto> children;



}