package com.controller;

import com.entity.Folder;
import com.entity.ResponseMessage;
import com.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 19:20
 */
public class FolderController {
    @Autowired
    private FolderService folderService;

    @PostMapping("/createFolder")
    public ResponseEntity<ResponseMessage> createFolder(@RequestBody Folder folderRequest,
                                                        @RequestHeader("Authorization") String token) {
        try {
            folderService.createFolder(folderRequest.getParentFolderId(), folderRequest.getFolderName(), token);
            ResponseMessage response = new ResponseMessage(0, "创建成功", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, "创建失败", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
