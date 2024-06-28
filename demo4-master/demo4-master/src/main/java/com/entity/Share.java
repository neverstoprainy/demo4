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
 * @date 2024/6/24 18:48
 */
@Data
public class Share {

    private String id;


    private LocalDateTime createTime;

    private LocalDateTime deadline;


    private String shareLink;
}
