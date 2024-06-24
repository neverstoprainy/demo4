package com.service.impl;

import com.mapper.FileMapper;
import com.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 19:17
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired(required = false)
    private FileMapper fileMapper;

    @Override
    @Transactional
    public void uploadFile(Long parentFolderId, String fileType, MultipartFile file, String token) throws Exception {
        // 实现上传文件逻辑，保存文件信息到数据库
        // 注意处理事务和异常
        fileMapper.uploadFile(parentFolderId, fileType, file.getOriginalFilename(), file.getSize(), token);
        // 这里 file.getOriginalFilename() 获取文件名，file.getSize() 获取文件大小
    }
}
