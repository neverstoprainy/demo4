package com.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 16:46
 */


@Data
public class User {
        private String id;

        private String username;

        private String password;

        private String email;

        private Date createTime;

        private Date updateTime;

        private BigDecimal usedCapacity;

        private BigDecimal capacity;

        private Boolean isAdmin;

        private String avatar;

        private Long folderId;

        private String loginTime;
}
