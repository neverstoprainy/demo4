package com.service.impl;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 19:21
 */


import com.entity.Folder;
import com.mapper.FolderMapper;
import com.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class FolderServiceImpl implements FolderService{

    @Autowired(required = false)
    private FolderMapper folderMapper;

    @Override
    public void createFolder(Long parentFolderId, String folderName, String token) throws Exception {
        Folder folder = new Folder();
        folder.setParentFolderId(parentFolderId);
        folder.setFolderName(folderName);
        folder.setIsDelete("N");
        folderMapper.insertFolder(folder);
    }

    @Override
    @Transactional
    public void recycleFolder(Long folderId, Long parentFolderId, String userId, String token) {
        // 回收文件夹逻辑
        folderMapper.recycleFolder(folderId);
    }

    @Override
    public List<Map<String, Object>> getFolderContents(Long folderId, String token){
        return folderMapper.getFolderContents(folderId);
    }

    @Override
    public void deleteFolder(Long folderId, String token) {
        folderMapper.deleteFolder(folderId);
    }

    @Override
    public void renameFolder(Long folderId, String newFolderName, String token) {
        folderMapper.updateFolderName(folderId, newFolderName);
    }

    @Override
    public void moveFolder(Long folderId, Long newparentFolderId,String token) {
        folderMapper.moveFolder(folderId, newparentFolderId);
    }
}
