package com.entity;

import lombok.Data;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 19:04
 */
@Data
public class ResponseMessage {
    private int code;
    private Object data;
    private String message;


    public ResponseMessage() {
        // 默认构造方法
    }

    public ResponseMessage(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
