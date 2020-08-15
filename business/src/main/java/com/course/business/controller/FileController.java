package com.course.business.controller;


import com.course.service.domain.dto.CourseContentFileDto;
import com.course.service.domain.dto.FileDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;


import com.course.service.enums.FileUseEnum;
import com.course.service.service.FileService;
import com.course.service.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

@Slf4j
@RestController
@RequestMapping("/admin/file")

public class FileController {
    public static final String BUSINESS_NAME = "文件";
    @Resource
    private FileService  fileService;
    @Value("${file.path}")
    private String FILE_PATH;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        return ResponseDto.builder().success(true).content( fileService.selectAll(pageDto)).build();
    }



    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file,String use) throws Exception {
        log.info("上传文件开始");
        FileUseEnum useEnum=FileUseEnum.getByCode(use);
        String key = UuidUtil.getShortUuid();
        String filename=file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".")+1);
        //如果文件夹不存在则创建
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }
        String path = dir + File.separator + key + "." + suffix ;
        String fllPath=FILE_PATH+path;
        File dest=new File(fllPath);
        file.transferTo(dest);
        log.info("保存文件记录");
        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto=new FileDto();
        fileDto.setPath(path);
        fileDto.setSuffix(suffix);
        fileDto.setName(filename);
        fileDto.setUse_enum(use);
        fileDto.setSize(Math.toIntExact(file.getSize()));
        responseDto.setContent("\\"+path);
        fileService.save(fileDto);
        responseDto.setList(CourseContentFileDto.builder().courseId(UuidUtil.getShortUuid()).url("\\"+path).name(filename).size(Math.toIntExact(file.getSize())).build());
        return responseDto;
    }

}
