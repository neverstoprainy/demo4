package com.service.impl;


import com.entity.FileEntity;
import com.mapper.FileMapper;
import com.service.FileService;
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
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.storage.location}")
    private String fileStorageLocation;

    @Autowired(required = false)
    private FileMapper fileMapper;

    @Override
    public void uploadFile(Long parentFolderId, String fileType, MultipartFile file, String token) throws Exception {
        Path targetLocation = Paths.get(fileStorageLocation).resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), targetLocation);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setParentFolderId(parentFolderId);
        fileEntity.setFileType(fileType);
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setSize(BigDecimal.valueOf(file.getSize()));
        fileEntity.setIsDelete("N");
        fileMapper.insertFile(fileEntity);
    }

    @Override
    public void recycleFile(Long fileId, Long parentFolderId, String userId, String token) {
        fileMapper.recycleFile(fileId);
    }

    @Override
    public Resource downloadFile(Long fileId, String token) throws Exception {
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
    public Map<String, Object> getFileInfo(Long id, String token) {
        return fileMapper.getFileInfoById(id);
    }

    @Override
    public void deleteFile(Long fileId, String token) {
        fileMapper.deleteFile(fileId);
    }

    @Override
    public void renameFile(Long fileId, String newFileName, String token) {
        fileMapper.updateFileName(fileId, newFileName);
    }

    @Override
    public void moveFile(Long fileId, Long newFolderId, String token) {

        fileMapper.moveFile(fileId, newFolderId);
    }

    @Override
    public List<Map<String, Object>> shareFile(String ownerId, Long folderId, String token) {
        return fileMapper.shareFile(ownerId, folderId);
    }

    @Override
    public void unshareFile(Long fileId, String token) {
        fileMapper.unshareFile(fileId);
    }
}
