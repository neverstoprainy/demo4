package com.entity;



import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 18:31
 */
@Data
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "folderName")
    private Long parentFolderId;
    private String createBy;
    private Date createTime;
    private Date updateTime;
    private Date lastAccessedTime;
    private String fileName;
    private BigDecimal size;
    private String fileType;
    private String isDelete;

}
