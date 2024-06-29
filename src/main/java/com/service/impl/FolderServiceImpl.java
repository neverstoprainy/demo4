package com.service.impl;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 19:21
 */


import com.entity.Folder;
import com.entity.User;
import com.mapper.FolderMapper;
import com.service.FolderService;
import com.service.UserService;
import com.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FolderServiceImpl implements FolderService{

    @Autowired(required = false)
    private FolderMapper folderMapper;
    @Autowired
    private UserService userService;

    @Override
    public void createFolder(String parentFolderId, String folderName, String token) throws Exception {
        String userid = JwtUtil.getClaimsFromToken(token).getSubject();
        User user = userService.findById(userid);
        Folder rootFolder = new Folder();

        rootFolder.setFolderName(folderName);
        rootFolder.setCreateBy(user.getUsername());
        rootFolder.setCreateTime(new Date());
        rootFolder.setUpdateTime(new Date());
        rootFolder.setIsDelete("N");
        rootFolder.setParentFolderId(parentFolderId);
        folderMapper.insert(rootFolder);
    }

    @Override
    @Transactional
    public void recycleFolder(String folderId, String parentFolderId, String userId, String token) {
        // 回收文件夹逻辑
        folderMapper.recycleFolder(folderId);
    }

    @Override
    public List<Map<String, Object>> getFolderContents(String folderId, String token){
        return folderMapper.getFolderContents(folderId);
    }

    @Override
    public Folder getFolderByRootFolderId(String rootfolderid){
        return folderMapper.getFolderByRootFolderId(rootfolderid);
    }

    @Override
    public void deleteFolder(String folderId, String token) {
        folderMapper.deleteFolder(folderId);
    }

    @Override
    public void renameFolder(String folderId, String newFolderName, String token) {
        folderMapper.updateFolderName(folderId, newFolderName);
    }

    @Override
    public void moveFolder(String folderId,  String newFolderId, String token) {
        folderMapper.moveFolder(folderId, newFolderId);
    }
    @Override
    public void insert(Folder folder){
        folderMapper.insertFolder(folder);
    }
}
