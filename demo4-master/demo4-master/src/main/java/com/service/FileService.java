package com.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {
        void uploadFile(Long parentFolderId, String fileType, MultipartFile file, String token) throws Exception;
        void recycleFile(Long fileId, Long parentFolderId, String userId, String token);
        Resource downloadFile(Long fileId, String token) throws Exception;
        Map<String, Object> getFileInfo(Long id, String token);
        void deleteFile(Long fileId, String token);

        void renameFile(Long fileId, String newFileName, String token);

        void moveFile(Long fileId, Long oldFolderId, Long newFolderId, String token);

         List<Map<String, Object>> shareFile(String owerId, Long folderId, String token);

        void unshareFile(Long fileId, String token);
}


