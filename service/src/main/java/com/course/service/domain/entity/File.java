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
@Table(name = "file")
public class File {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 相对路径
     */
    private String path;

    /**
     * 文件名
     */
    private String name;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 大小|字节B
     */
    private Integer size;

    /**
     * 用途|枚举[FileUseEnum]：COURSE("C", "讲师"), TEACHER("T", "课程")
     */
    @Column(name = "use_enum")
    private String useEnum;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 修改时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 已上传分片
     */
    @Column(name = "shard_index")
    private Integer shardIndex;

    /**
     * 分片大小|B
     */
    @Column(name = "shard_size")
    private Integer shardSize;

    /**
     * 分片总数
     */
    @Column(name = "shard_total")
    private Integer shardTotal;

    /**
     * 文件标识
     */
    private String key_md5;

    /**
     * vod|阿里云vod
     */
    private String vod;
}