package com.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 18:46
 */

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "folder")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "folderName")
    private String folderName;

    @Column(name = "createBy")
    private String createBy;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "updateTime")
    private Date updateTime;

    @Column(name = "lastAccessTime")
    private Date lastAccessTime;

    @Column(name = "parentFolderId")
    private Long parentFolderId;

    @Column(name = "isDelete")
    private String isDelete;

    @Column(name = "size")
    private BigDecimal size;

    // 不映射到数据库的字段
    @Transient
    private BigDecimal folderSize;

    // 文件夹包含的子文件夹
    @Transient
    private List<Folder> subFolders = new ArrayList<>();

    // 文件夹包含的文件
    @Transient
    private List<FileEntity> files = new ArrayList<>();

    // 计算文件夹大小的方法
    public BigDecimal calculateFolderSize() {
        BigDecimal totalSize = getSize(); // 加上文件夹自身的大小
        // 递归计算所有子文件夹的大小
        for (Folder subFolder : subFolders) {
            totalSize = totalSize.add(subFolder.calculateFolderSize());
        }
        // 加上所有文件的大小
        for (FileEntity file : files) {
            totalSize = totalSize.add(file.getSize());
        }
        // 更新文件夹大小字段
        setFolderSize(totalSize);
        return totalSize;
    }
}
