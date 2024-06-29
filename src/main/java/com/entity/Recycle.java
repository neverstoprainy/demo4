package com.entity;

import lombok.Data;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 19:33
 */
@Data
public class Recycle{
    private String FileId;
    private String FolderId;
    private String parentFolderId;
    private String userId;
    private boolean isFolder;

}
