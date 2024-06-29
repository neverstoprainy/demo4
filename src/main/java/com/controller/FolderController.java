package com.controller;

import com.entity.*;
import com.service.FolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 19:20
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/folder")
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

    @PostMapping("/recycleFolder")
    public ResponseEntity<ResponseMessage> recycleFolder(@RequestBody Recycle recycleRequest,
                                                         @RequestHeader("Authorization") String token) {
        try {
            folderService.recycleFolder(recycleRequest.getFolderId(), recycleRequest.getParentFolderId(), recycleRequest.getUserId(), token);
            ResponseMessage response = new ResponseMessage(0, "回收成功", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, "回收失败", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/contents")
    public ResponseEntity<ResponseMessage> getFolderContents(@RequestParam(value = "folderId", required = false) String folderId,
                                                             @RequestHeader("Authorization") String token) {
        try {
            List<Map<String, Object>> contents = folderService.getFolderContents(folderId, token);
            // 构建 JSON 响应格式
            List<Object> responseData = new ArrayList<>();
            for (Map<String, Object> content : contents) {
                boolean isFolder = "folder".equals(content.get("type"));
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("id", content.get("id"));
                item.put("name", content.get("name"));
                item.put("isFolder", isFolder);
                responseData.add(item);
            }
            ResponseMessage response = new ResponseMessage(0, responseData, "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/root")
    public ResponseEntity<ResponseMessage> getRootFolderContents(@RequestParam(value = "rootfolderId", required = false) String rootfolderId,
                                                             @RequestHeader("Authorization") String token) {
        try {
            Folder root = folderService.getFolderByRootFolderId(rootfolderId);
            List<Map<String, Object>> contents = folderService.getFolderContents(root.getId(), token);
            // 构建 JSON 响应格式
            List<Object> responseData = new ArrayList<>();
            for (Map<String, Object> content : contents) {
                boolean isFolder = "folder".equals(content.get("type"));
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("id", content.get("id"));
                item.put("name", content.get("name"));
                item.put("isFolder", isFolder);
                responseData.add(item);
            }
            ResponseMessage response = new ResponseMessage(0, responseData, "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/deleteFolder")
    public ResponseEntity<ResponseMessage> deleteFolder(@RequestBody DeleteRequest deleteRequest,
                                                        @RequestHeader("Authorization") String token) {
        try {
            folderService.deleteFolder(deleteRequest.getFolderId(), token);
            ResponseMessage response = new ResponseMessage(0, "操作成功", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/modifyFolderName")
    public ResponseEntity<ResponseMessage> renameFolder(@RequestBody RenameRequest renameRequest,
                                                        @RequestHeader("Authorization") String token) {
        try {
            folderService.renameFolder(renameRequest.getFolderId(), renameRequest.getNewFolderName(), token);
            ResponseMessage response = new ResponseMessage(0, "操作成功", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/move")
    public ResponseEntity<ResponseMessage> moveFolder(@RequestBody MoveRequest moveRequest,
                                                      @RequestHeader("Authorization") String token) {
        try {
            folderService.moveFolder(moveRequest.getFolderId(),moveRequest.getNewparentFolderId(), token);
            ResponseMessage response = new ResponseMessage(0, "移动成功", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
