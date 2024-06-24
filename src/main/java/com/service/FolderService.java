package com.service;

public interface FolderService {
        void createFolder(Long parentFolderId, String folderName, String token) throws Exception;
    }


