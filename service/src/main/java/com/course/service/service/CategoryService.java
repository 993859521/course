package com.course.service.service;
import com.course.service.dto.CategoryMapper;
import com.course.service.domain.dto. CategoryDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity.Category;
import com.course.service.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;
import com.course.service.util.UuidUtil;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor( onConstructor = @__(@Autowired))
public class  CategoryService {
    private final CategoryMapper categoryMapper;

    /**
     * 列表查询
     *
     * @param pageDto
     * @return
     */
    public PageDto selectAll(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
        List<Category> categorys = categoryMapper.selectAll();
        PageInfo pageInfo = new PageInfo<>(categorys);
        pageDto.setTotal(pageInfo.getTotal());
        for (int i = 0; i < categorys.size(); i++) {
            Category category = categorys.get(i);
            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(category, categoryDto);
            categoryDtos.add(categoryDto);
        }
        pageDto.setList(categoryDtos);
        return pageDto;
    }

    public List<CategoryDto> all() {
        List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
        List<Category> categorys = categoryMapper.selectAll();

        for (int i = 0; i < categorys.size(); i++) {
            Category category = categorys.get(i);
            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(category, categoryDto);
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }


    /**
     * 增加数据或者更新数据
     *
     * @param categoryDto
     */
    public void save(CategoryDto categoryDto) {
        Category category = CopyUtil.copy(categoryDto, Category.class);

        if (StringUtil.isEmpty(category.getId())) {
            category.setId(UuidUtil.getShortUuid());
            categoryMapper.insert(category);
        } else {
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public void delete(String id) {

        Category category = categoryMapper.selectByPrimaryKey(id);
        if ("00000000".equals(category.getParent())) {
            // 如果是一级分类，需要删除其下的二级分类
            categoryMapper.delete(Category.builder().parent(id).build());
        }
        categoryMapper.delete(category);
    }
}