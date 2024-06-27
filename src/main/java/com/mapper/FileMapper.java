package com.mapper;

import com.entity.FileEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM file WHERE id = #{id}")
    FileEntity getFileById(Long id);

    @Select("SELECT * FROM file WHERE parentFolderId = #{folderId} AND is_delete = 'N'")
    List<FileEntity> getFilesByFolderId(Long folderId);

    @Insert("INSERT INTO file (parentFolderId, createBy, createTime, updateTime, lastAccessedTime, fileName, size, fileType, isDelete) VALUES (#{parentFolderId}, #{createBy}, #{createTime}, #{updateTime}, #{lastAccessedTime}, #{fileName}, #{size}, #{fileType}, #{isDelete})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertFile(FileEntity fileEntity);

    @Update("UPDATE file SET isDelete = 'Y' WHERE id = #{id}")
    void deleteFile(Long id);

    @Update("UPDATE file SET fileName = #{fileName} WHERE id = #{id}")
    void updateFileName(@Param("id") Long id, @Param("fileName") String fileName);

    @Update("UPDATE file SET parentFolderId = #{newFolderId} WHERE id = #{fileId}")
    void moveFile(@Param("fileId") Long fileId, @Param("newFolderId") Long newFolderId);

    @Update("UPDATE file SET isDelete = 'Y' WHERE id = #{fileId}")
    void recycleFile(Long fileId);

    @Select("SELECT * FROM file WHERE id = #{id}")
    Map<String, Object> getFileInfoById(Long id);

    @Select("SELECT * FROM file WHERE createBy = #{ownerId} AND parent_folder_id = #{folderId}")
    List<Map<String, Object>> shareFile(@Param("ownerId") String ownerId, @Param("folderId") Long folderId);

    @Update("UPDATE file SET isDelete = 'N' WHERE id = #{fileId}")
    void unshareFile(Long fileId);
}