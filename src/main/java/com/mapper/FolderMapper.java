package com.mapper;


import org.apache.ibatis.annotations.Insert;

public interface FolderMapper {
    @Insert("INSERT INTO folders (parentFolderId, folderName, createdBy) " +
            "VALUES (#{parentFolderId}, #{folderName}, #{createdBy})")
    void createFolder(Long parentFolderId, String folderName, String createdBy);
}
