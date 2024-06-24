package com.controller;

import com.entity.FileEntity;
import com.entity.Recycle;
import com.entity.ResponseMessage;

import com.service.FileService;
import com.service.RecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired(required = false)
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam Long parentFolderId,
                                                      @RequestParam String fileType,
                                                      @RequestParam("file") MultipartFile file,
                                                      @RequestHeader("Authorization") String token) {
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

    @DeleteMapping("/recycleFile")
    public ResponseEntity<ResponseMessage> recycleFile(@RequestBody Recycle recycleRequest,
                                                       @RequestHeader("Authorization") String token) {
        try {
            recycleService.recycleFile(recycleRequest.getFileId(), recycleRequest.getParentFolderId(), recycleRequest.getUserId(), token);
            ResponseMessage response = new ResponseMessage(0, "回收成功", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, "回收失败", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
