package com.entity;

import lombok.Data;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/27 10:17
 */

@Data
public class MoveRequest {
    private Long fileId;
    private Long folderId;
    private Long newparentFolderId;

}
