package com.service;

import java.util.List;
import java.util.Map;

public interface FolderService {
        void createFolder(Long parentFolderId, String folderName, String token) throws Exception;
        void recycleFolder(Long folderId, Long parentFolderId, String userId, String token);
        void deleteFolder(Long folderId, String token);
        void renameFolder(Long folderId, String newFolderName, String token);
        void moveFolder(Long folderId, Long newparentFolderId, String token);
        List<Map<String, Object>> getFolderContents(Long folderId, String token);
    }


