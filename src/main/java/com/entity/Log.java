package com.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 18:47
 */

@Data
public class Log {

    @Id
    private String id;

    @Column(name = "action_type")
    private String actionType;

    private LocalDateTime timestamp;

    @Column(name = "file_or_folder_id")
    private String fileOrFolderId;


}
