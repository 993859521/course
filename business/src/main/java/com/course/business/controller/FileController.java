package com.course.business.controller;


import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.course.service.domain.dto.CourseContentFileDto;
import com.course.service.domain.dto.FileDto;
import com.course.service.domain.dto.PageDto;
import com.course.service.domain.dto.ResponseDto;

import com.course.service.enums.FileUseEnum;
import com.course.service.service.FileService;
import com.course.service.util.Base64ToMultipartFile;
import com.course.service.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;

@Slf4j
@RestController
@RequestMapping("/admin/file")

public class FileController {
    public static final String BUSINESS_NAME = "文件";
    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.domain}")
    private String ossDomain;
    @Resource
    private FileService fileService;
    @Value("${file.path}")
    private String FILE_PATH;

    /***
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        return ResponseDto.builder().success(true).content(fileService.selectAll(pageDto)).build();
    }


    @RequestMapping("/upload")
    public ResponseDto upload(@RequestBody FileDto fileDto) throws Exception {
        String use = fileDto.getUseEnum();
        String key = fileDto.getKey_md5();
        String suffix = fileDto.getSuffix();
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);
        log.info("上传文件开始");
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        //如果文件夹不存在则创建
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }
        String path = new StringBuffer(dir)
                .append(File.separator)
                .append(key)
                .append(".")
                .append(suffix)
                .toString(); // course\6sfSqfOwzmik4A4icMYuUe.mp4
        String localPath = new StringBuffer(path)
                .append(".")
                .append(fileDto.getShardIndex())
                .toString(); // course\6sfSqfOwzmik4A4icMYuUe.mp4.1
        String fullPath = FILE_PATH + localPath;
        File dest = new File(fullPath);
        shard.transferTo(dest);
        log.info("保存文件记录");
        ResponseDto responseDto = new ResponseDto();
        fileDto.setPath(path);
        responseDto.setContent("\\" + path);

        fileService.save(fileDto);
        responseDto.setContent(fileDto);

        if (fileDto.getShardIndex().equals(fileDto.getShardTotal())) {
            this.merge(fileDto);
        }
        return responseDto;
    }

    public void merge(FileDto fileDto) throws Exception {
        log.info("合并分片开始ShardIndex{},ShardTotal{}",fileDto.getShardIndex(),fileDto.getShardTotal());
        String path = fileDto.getPath(); //http://127.0.0.1:9000/file/f/course\6sfSqfOwzmik4A4icMYuUe.mp4
        path = path.replace(FILE_PATH, ""); //course\6sfSqfOwzmik4A4icMYuUe.mp4
        Integer shardTotal = fileDto.getShardTotal();
        File newFile = new File(FILE_PATH + path);
        FileOutputStream outputStream = new FileOutputStream(newFile, true);//文件追加写入
        FileInputStream fileInputStream = null;//分片文件
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;

        try {
            for (int i = 0; i < shardTotal; i++) {
                // 读取第i个分片
                fileInputStream = new FileInputStream(new File(FILE_PATH + path + "." + (i + 1))); //  course\6sfSqfOwzmik4A4icMYuUe.mp4.1
                while ((len = fileInputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
            }
        } catch (IOException e) {
            log.error("分片合并异常", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                outputStream.close();
                log.info("IO流关闭");
            } catch (Exception e) {
                log.error("IO流关闭", e);
            }
        }
        log.info("合并分片结束");

        System.gc();
        Thread.sleep(100);

        // 删除分片
        log.info("删除分片开始");
        for (int i = 0; i < shardTotal; i++) {
            String filePath = FILE_PATH + path + "." + (i+1) ;
            File file = new File(filePath);
            boolean result = file.delete();
            log.info("删除{}，{}", filePath, result ? "成功" : "失败");
        }
        log.info("删除分片结束");
    }

    @GetMapping("/check/{key}")
    public ResponseDto check(@PathVariable String key) throws Exception {
        log.info("检查上传分片开始：{}", key);
        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto = fileService.findByKey(key);
        responseDto.setContent(fileDto);
        return responseDto;
    }
    @PostMapping("/oss-simple")
    public ResponseDto fileUpload(@RequestParam MultipartFile file, String use, String key_md5,String size) throws Exception {
        log.info("上传文件开始");
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String key = UuidUtil.getShortUuid();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String dir = useEnum.name().toLowerCase();
        String path = dir + "/" + key + "." + suffix;
        log.info("1:{},2:{},3:{}",endpoint, accessKeyId, accessKeySecret);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, new ByteArrayInputStream(file.getBytes()));


        ossClient.putObject(putObjectRequest);


        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto = new FileDto();
        fileDto.setPath(ossDomain + path);
        fileDto.setName(fileName);
        fileDto.setSize(Integer.parseInt(size));
        responseDto.setContent(fileDto);
        fileService.save(FileDto.builder()
                .key_md5(key_md5)
                .name(fileName)
                .path(ossDomain + path)
                .suffix(suffix)
                .useEnum(use)
                .size(Integer.parseInt(size))
                .build());

        return responseDto;
    }

}
