package com.mapper;

import org.apache.ibatis.annotations.Insert;

public interface FileMapper {

    @Insert("INSERT INTO files (parentFolderId, fileType, fileName, fileSize, createdBy) " +
            "VALUES (#{parentFolderId}, #{fileType}, #{fileName}, #{fileSize}, #{createdBy})")
    void uploadFile(Long parentFolderId, String fileType, String fileName, Long fileSize, String createdBy);
}
