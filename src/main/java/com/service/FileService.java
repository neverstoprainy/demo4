package com.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
        void uploadFile(Long parentFolderId, String fileType, MultipartFile file, String token) throws Exception;

}


