package com.service.impl;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 19:21
 */


import com.mapper.FolderMapper;
import com.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FolderServiceImpl implements FolderService {

    @Autowired(required = false)
    private FolderMapper folderMapper;
    @Override
    @Transactional
    public void createFolder(Long parentFolderId, String folderName, String token) throws Exception {
        // 实现创建文件夹逻辑，保存文件夹信息到数据库
        // 注意处理事务和异常
        folderMapper.createFolder(parentFolderId, folderName, token);
    }
}
