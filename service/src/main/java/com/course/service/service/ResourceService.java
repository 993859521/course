package com.course.service.service;

import com.alibaba.fastjson.JSON;
import com.course.service.dto.ResourceMapper;
import com.course.service.domain.dto.ResourceDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity.Resource;
import com.course.service.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.util.StringUtil;
import com.course.service.util.UuidUtil;

import java.util.ArrayList;
import java.util.List;

import static com.course.service.util.UuidUtil.getShortUuid;
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResourceService {
    private final ResourceMapper resourceMapper;



    /**
     * 增加数据或者更新数据
     *
     * @param resourceDto
     */
    public void save(ResourceDto resourceDto) {
        Resource resource = CopyUtil.copy(resourceDto, Resource.class);

        if (StringUtil.isEmpty(resource.getId())) {
            resource.setId(UuidUtil.getShortUuid());
            resourceMapper.insert(resource);
        } else {
            resourceMapper.updateByPrimaryKey(resource);
        }
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }
    public List<ResourceDto> loadTree() {

        List<Resource> resourceList = resourceMapper.selectAll();
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        for (int i = resourceDtoList.size() - 1; i >= 0; i--) {
            // 当前要移动的记录
            ResourceDto child = resourceDtoList.get(i);

            // 如果当前节点没有父节点，则不用往下了
            if (StringUtils.isEmpty(child.getParent())) {
                continue;
            }
            // 查找父节点
            for (int j = i - 1; j >= 0; j--) {
                ResourceDto parent = resourceDtoList.get(j);
                if (child.getParent().equals(parent.getId())) {
                    if (CollectionUtils.isEmpty(parent.getChildren())) {
                        parent.setChildren(new ArrayList<>());
                    }
                    // 添加到最前面，否则会变成倒序，因为循环是从后往前循环的
                    parent.getChildren().add(0, child);

                    // 子节点找到父节点后，删除列表中的子节点
                    resourceDtoList.remove(child);
                }
            }
        }
        return resourceDtoList;
    }
    /**
     * 保存资源树
     * @param json
     */
    @Transactional
    public void saveJson(String json) {
        List<ResourceDto> jsonList = JSON.parseArray(json, ResourceDto.class);
        List<ResourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)) {
            for (ResourceDto d: jsonList) {
                d.setParent("");
                add(list, d);
            }
        }
        log.info("共{}条", list.size());

        resourceMapper.deleteByExample(null);
        for (int i = 0; i < list.size(); i++) {
            resourceMapper.insert(CopyUtil.copy(list.get(i), Resource.class));
        }
    }
    /**
     * 递归，将树型结构的节点全部取出来，放到list
     * @param list
     * @param dto
     */
    public void add(List<ResourceDto> list, ResourceDto dto) {
        list.add(dto);
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            for (ResourceDto d: dto.getChildren()) {
                d.setParent(dto.getId());
                add(list, d);
            }
        }
    }


}
