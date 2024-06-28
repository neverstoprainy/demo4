package com.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {

    @Select("SELECT id, fileName AS name, createBy, createTime, updateTime, lastAccessedTime AS lastAccessTime, parentFolderId, isDelete, size, 'file' AS type " +
            "FROM file WHERE fileName LIKE CONCAT('%', #{desc}, '%') " +
            "UNION ALL " +
            "SELECT id, folderName AS name, createBy, createTime, updateTime, lastAccessTime, parentFolderId, isDelete, size, 'folder' AS type " +
            "FROM folder WHERE folderName LIKE CONCAT('%', #{desc}, '%')")
    List<Map<String, Object>> search(String desc);
}
