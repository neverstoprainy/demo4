package com.mapper;


import com.entity.Folder;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface FolderMapper {

    @Select("SELECT * FROM folder WHERE id = #{id}")
    Folder getFolderById(Long id);

    @Select("SELECT * FROM folder WHERE parent_folder_id = #{parentFolderId} AND is_delete = 'N'")
    List<Folder> getFoldersByParentId(Long parentFolderId);

    @Insert("INSERT INTO folder (folder_name, create_by, create_time, update_time, last_access_time, parent_folder_id, is_delete, size) VALUES (#{folderName}, #{createBy}, #{createTime}, #{updateTime}, #{lastAccessTime}, #{parentFolderId}, #{isDelete}, #{size})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertFolder(Folder folder);

    @Update("UPDATE folder SET is_delete = 'Y' WHERE id = #{id}")
    void deleteFolder(Long id);

    @Update("UPDATE folder SET folder_name = #{folderName} WHERE id = #{id}")
    void updateFolderName(@Param("id") Long id, @Param("folderName") String folderName);

    @Update("UPDATE folder SET parent_folder_id = #{newFolderId} WHERE id = #{folderId}")
    void moveFolder(@Param("folderId") Long folderId, @Param("newFolderId") Long newFolderId);

    @Update("UPDATE folder SET is_delete = 'Y' WHERE id = #{folderId}")
    void recycleFolder(Long folderId);

    @Select("SELECT * FROM file_entity WHERE parent_folder_id = #{folderId} AND is_delete = 'N' UNION ALL SELECT * FROM folder WHERE parent_folder_id = #{folderId} AND is_delete = 'N'")
    List<Map<String, Object>> getFolderContents(@Param("folderId") Long folderId);
}