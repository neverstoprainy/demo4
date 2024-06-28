package com.mapper;


import com.entity.Folder;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface FolderMapper {

    @Select("SELECT * FROM folder WHERE id = #{id}")
    Folder getFolderById(Long id);

    @Select("SELECT * FROM folder WHERE parentFolderId = #{parentFolderId} AND isDelete = 'N'")
    List<Folder> getFoldersByParentId(Long parentFolderId);

    @Insert("INSERT INTO folder (folderName, createBy, createTime, updateTime, lastAccessTime, parentFolderId, isDelete, size) VALUES (#{folderName}, #{createBy}, #{createTime}, #{updateTime}, #{lastAccessTime}, #{parentFolderId}, #{isDelete}, #{size})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertFolder(Folder folder);

    @Delete("DELETE FROM folder WHERE id = #{id}")
    void deleteFolder(Long id);

    @Update("UPDATE folder SET folderName = #{folderName} WHERE id = #{id}")
    void updateFolderName(@Param("id") Long id, @Param("folderName") String folderName);

    @Update("UPDATE folder SET parentFolderId = #{newFolderId} WHERE id = #{folderId}")
    void moveFolder(@Param("folderId") Long folderId, @Param("newFolderId") Long newFolderId);

    @Update("UPDATE folder SET isDelete = 'Y' WHERE id = #{folderId}")
    void recycleFolder(Long folderId);

    @Select("SELECT id, fileName AS name, createBy, createTime, updateTime, lastAccessedTime AS lastAccessTime, parentFolderId, isDelete, size, 'file' AS type " +
            "FROM file WHERE parentFolderId = #{parentFolderId} AND isDelete = 'N' " +
            "UNION ALL " +
            "SELECT id, folderName AS name, createBy, createTime, updateTime, lastAccessTime, parentFolderId, isDelete, size, 'folder' AS type " +
            "FROM folder WHERE parentFolderId = #{parentFolderId} AND isDelete = 'N'")
    List<Map<String, Object>> getFolderContents(@Param("parentFolderId") Long parentFolderId);


}