package com.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 18:46
 */

@Data
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "folder_name")
    private String folderName;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "last_access_time")
    private LocalDateTime lastAccessTime;

    @Column(name = "parent_folder_id")
    private Long parentFolderId;

    @Column(name = "is_delete")
    private String isDelete;

    private BigDecimal size;


}
