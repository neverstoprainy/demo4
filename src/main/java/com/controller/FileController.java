package com.controller;

import com.entity.*;

import com.service.FileService;
import com.service.RecycleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired(required = false)
    private FileService fileService;

    @PostMapping("/uploadFile")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("parentFolderId") String parentFolderId,
                                                      @RequestParam("fileType") String fileType,
                                                      @RequestParam("file") MultipartFile file,
                                                      @RequestHeader("Authorization") String token){
        try {
            fileService.uploadFile(parentFolderId, fileType, file, token);
            ResponseMessage response = new ResponseMessage(0, "文件上传成功", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, "文件上传失败", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @Autowired
    private RecycleService recycleService;

    @PostMapping("/recycleFile")
    public ResponseEntity<ResponseMessage> recycleFile(@RequestBody Recycle recycleRequest,
                                                       @RequestHeader("Authorization") String token) {
        try {
            fileService.recycleFile(recycleRequest.getFileId(), recycleRequest.getParentFolderId(), recycleRequest.getUserId(), token);
            ResponseMessage response = new ResponseMessage(0, "回收成功", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, "回收失败", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileId") String fileId,
                                                 @RequestHeader("Authorization") String token) {
        try {
            Resource file = fileService.downloadFile(fileId, token);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseMessage> getFileInfo(@RequestParam("id") String id,
                                                       @RequestHeader("Authorization") String token) {
        try {
            Map<String, Object> fileInfo = fileService.getFileInfo(id, token);

            // 构建返回的数据结构
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("parentFolderId", fileInfo.get("parentFolderId"));
            data.put("creator", fileInfo.get("createBy"));
            data.put("lastAccessTime", fileInfo.get("lastAccessedTime"));
            data.put("size", fileInfo.get("size"));
            data.put("createTime", fileInfo.get("createTime"));
            data.put("fileName", fileInfo.get("fileName"));
            data.put("fileType", fileInfo.get("fileType"));

            // 构建 ResponseMessage
            ResponseMessage response = new ResponseMessage(0, data, "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteFile(@RequestBody DeleteRequest deleteRequest,
                                                      @RequestHeader("Authorization") String token) {
        try {
            fileService.deleteFile(deleteRequest.getFileId(), token);
            ResponseMessage response = new ResponseMessage(0, "操作成功", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/modifyFileName")
    public ResponseEntity<ResponseMessage> renameFile(@RequestBody RenameRequest renameRequest,
                                                      @RequestHeader("Authorization") String token) {
        try {
            fileService.renameFile(renameRequest.getFileId(), renameRequest.getNewFileName(), token);
            ResponseMessage response = new ResponseMessage(0, "操作成功", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/move")
    public ResponseEntity<ResponseMessage> moveFile(@RequestBody MoveRequest moveRequest,
                                                    @RequestHeader("Authorization") String token) {
        try {
            System.out.println("移动到了"+ moveRequest.getNewparentFolderId());
            fileService.moveFile(moveRequest.getFileId(),moveRequest.getNewparentFolderId(), token);
            ResponseMessage response = new ResponseMessage(0, "移动成功", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
//
//    @GetMapping("/share")
//    public ResponseEntity<ResponseMessage> shareFile(@RequestParam(value = "owerId", required = false) String owerId,
//                                                     @RequestParam(value = "folderId", required = false) String folderId,
//                                                     @RequestHeader("Authorization") String token) {
//        try {
//            List<Map<String, Object>> sharedFiles = fileService.shareFile(owerId, folderId, token);
//            ResponseMessage response = new ResponseMessage(0, sharedFiles, "ok");
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }

//    @PostMapping("/unshare")
//    public ResponseEntity<ResponseMessage> unshareFile(@RequestParam("fileId") String fileId,
//                                                       @RequestHeader("Authorization") String token) {
//        try {
//            fileService.unshareFile(fileId, token);
//            ResponseMessage response = new ResponseMessage(0, "取消成功", "ok");
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }

}
