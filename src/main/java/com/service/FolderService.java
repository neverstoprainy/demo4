package com.service;

import com.entity.Folder;

import java.util.List;
import java.util.Map;

public interface FolderService {
        void createFolder(String parentFolderId, String folderName, String token) throws Exception;
        void recycleFolder(String folderId, String parentFolderId, String userId, String token);
        void deleteFolder(String folderId, String token);
        void renameFolder(String folderId, String newFolderName, String token);
        void moveFolder(String folderId, String newFolderId, String token);
        List<Map<String, Object>> getFolderContents(String folderId, String token);
        void insert(Folder folder);
        Folder getFolderByRootFolderId(String rootfolderid);
}


