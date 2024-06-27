package com.mapper;

import com.entity.FileEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM file_entity WHERE id = #{id}")
    FileEntity getFileById(Long id);

    @Select("SELECT * FROM file_entity WHERE parent_folder_id = #{folderId} AND is_delete = 'N'")
    List<FileEntity> getFilesByFolderId(Long folderId);

    @Insert("INSERT INTO file_entity (parent_folder_id, create_by, create_time, update_time, last_accessed_time, file_name, size, file_type, is_delete) VALUES (#{parentFolderId}, #{createBy}, #{createTime}, #{updateTime}, #{lastAccessedTime}, #{fileName}, #{size}, #{fileType}, #{isDelete})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertFile(FileEntity fileEntity);

    @Update("UPDATE file_entity SET is_delete = 'Y' WHERE id = #{id}")
    void deleteFile(Long id);

    @Update("UPDATE file_entity SET file_name = #{fileName} WHERE id = #{id}")
    void updateFileName(@Param("id") Long id, @Param("fileName") String fileName);

    @Update("UPDATE file_entity SET parent_folder_id = #{newFolderId} WHERE id = #{fileId}")
    void moveFile(@Param("fileId") Long fileId, @Param("newFolderId") Long newFolderId);

    @Update("UPDATE file_entity SET is_delete = 'Y' WHERE id = #{fileId}")
    void recycleFile(Long fileId);

    @Select("SELECT * FROM file_entity WHERE id = #{id}")
    Map<String, Object> getFileInfoById(Long id);

    @Select("SELECT * FROM file_entity WHERE create_by = #{ownerId} AND parent_folder_id = #{folderId}")
    List<Map<String, Object>> shareFile(@Param("ownerId") String ownerId, @Param("folderId") Long folderId);

    @Update("UPDATE file_entity SET is_delete = 'N' WHERE id = #{fileId}")
    void unshareFile(Long fileId);
}