package com.entity;



import lombok.Data;

import javax.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 18:31
 */
@Data
public class FileEntity {

    private Long id;
    private Long parentFolderId;
    private String createBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime lastAccessedTime;
    private String fileName;
    private BigDecimal size;
    private String fileType;
    private String isDelete;

}
