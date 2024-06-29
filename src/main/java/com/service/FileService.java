package com.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {
        void uploadFile(String parentFolderId, String fileType, MultipartFile file, String token) throws Exception;
        
        void recycleFile(String fileId, String parentFolderId, String userId, String token);
        
        Resource downloadFile(String fileId, String token) throws Exception;
        
        Map<String, Object> getFileInfo(String id, String token);
        
        void deleteFile(String fileId, String token);

        void renameFile(String fileId, String newFileName, String token);

        void moveFile(String fileId, String newFolderId, String token);

         List<Map<String, Object>> shareFile(String owerId, String folderId, String token);

        void unshareFile(String fileId, String token);
}


