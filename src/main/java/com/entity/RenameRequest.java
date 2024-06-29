package com.entity;

import lombok.Data;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/27 10:16
 */

@Data
public class RenameRequest {
    private String fileId;
    private String folderId;
    private String newFileName;
    private String newFolderName;

}
