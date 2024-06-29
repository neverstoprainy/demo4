package com.service.impl;


import com.entity.FileEntity;
import com.entity.User;
import com.mapper.FileMapper;
import com.service.FileService;
import com.service.UserService;
import com.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.storage.location}")
    private String fileStorageLocation;

    @Autowired(required = false)
    private FileMapper fileMapper;
    @Autowired
    private UserService userService;

    @Override
    public void uploadFile(String parentFolderId, String fileType, MultipartFile file, String token) throws Exception {
        Path targetLocation = Paths.get(fileStorageLocation).resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), targetLocation);
        String userid = JwtUtil.getClaimsFromToken(token).getSubject();
        User user = userService.findById(userid);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setParentFolderId(parentFolderId);
        fileEntity.setFileType(fileType);
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setSize(BigDecimal.valueOf(file.getSize()));
        fileEntity.setIsDelete("N");
        fileEntity.setCreateTime(new Date()); // 设置创建时间
        fileEntity.setUpdateTime(new Date()); // 设置更新时间，根据需要设定
        fileEntity.setLastAccessedTime(new Date()); // 设置上次访问时间，根据需要设定
        // 设置创建人，可能需要从 token 解析用户信息或者直接使用已知的用户信息
        fileEntity.setCreateBy(user.getUsername());

        fileMapper.insertFile(fileEntity);
    }

    @Override
    public void recycleFile(String fileId, String parentFolderId, String userId, String token) {
        fileMapper.recycleFile(fileId);
    }

    @Override
    public Resource downloadFile(String fileId, String token) throws Exception {
        FileEntity fileEntity = fileMapper.getFileById(fileId);
        Path filePath = Paths.get(fileStorageLocation).resolve(fileEntity.getFileName()).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new Exception("File not found or not readable");
        }
    }

    @Override
    public Map<String, Object> getFileInfo(String id, String token) {
        return fileMapper.getFileInfoById(id);
    }

    @Override
    public void deleteFile(String fileId, String token) {
        fileMapper.deleteFile(fileId);
    }

    @Override
    public void renameFile(String fileId, String newFileName, String token) {
        fileMapper.updateFileName(fileId, newFileName);
    }

    @Override
    public void moveFile(String fileId, String newFolderId, String token) {

        fileMapper.moveFile(fileId, newFolderId);
    }

    @Override
    public List<Map<String, Object>> shareFile(String ownerId, String folderId, String token) {
        return fileMapper.shareFile(ownerId, folderId);
    }

    @Override
    public void unshareFile(String fileId, String token) {
        fileMapper.unshareFile(fileId);
    }
}
