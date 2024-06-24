package com.mapper;

import org.apache.ibatis.annotations.Update;
public interface RecycleMapper {

    @Update("UPDATE files SET status = 'RECYCLED' WHERE fileId = #{fileId} AND parentFolderId = #{parentFolderId} AND createdBy = #{userId}")
    void recycleFile(String fileId, String parentFolderId, String userId, String token);
}
