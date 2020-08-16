package com.course.service.service;

import com.course.service.dto.FileMapper;
import com.course.service.domain.dto.FileDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.entity.File;
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

import static com.course.service.util.UuidUtil.getShortUuid;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileService {
    private final FileMapper fileMapper;

    /**
     * 列表查询
     *
     * @param pageDto
     * @return
     */
    public PageDto selectAll(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<FileDto> fileDtos = new ArrayList<FileDto>();
        List<File> files = fileMapper.selectAll();
        PageInfo pageInfo = new PageInfo<>(files);
        pageDto.setTotal(pageInfo.getTotal());
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            FileDto fileDto = new FileDto();
            BeanUtils.copyProperties(file, fileDto);
            fileDtos.add(fileDto);
        }
        pageDto.setList(fileDtos);
        return pageDto;
    }

    /**
     * 增加数据或者更新数据
     *
     * @param fileDto
     */
    public void save(FileDto fileDto) {
        File file = CopyUtil.copy(fileDto, File.class);
        FileDto fileDb = findByKey(fileDto.getKey_md5());
        if (fileDb == null) {
            Date now = new Date();
            file.setCreatedAt(now);
            file.setUpdatedAt(now);
            file.setId(UuidUtil.getShortUuid());
            fileMapper.insertSelective(file);
        } else {
            fileDb.setShardIndex(fileDto.getShardIndex());
            log.info("ShardIndex:{}",file.getShardIndex());
            fileMapper.updateByPrimaryKey(CopyUtil.copy(fileDb, File.class));
        }
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public void delete(String id) {
        fileMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查找key的那条数据
     * @param key
     * @return
     */
    public FileDto findByKey(String key){
        File file = fileMapper.selectOne(File.builder().key_md5(key).build());
        return CopyUtil.copy(file, FileDto.class);
    }

}
