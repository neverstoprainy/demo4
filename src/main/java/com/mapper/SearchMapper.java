package com.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {
    @Select("SELECT * FROM file_entity WHERE file_name LIKE CONCAT('%', #{desc}, '%') UNION ALL SELECT * FROM folder WHERE folder_name LIKE CONCAT('%', #{desc}, '%')")
    List<Map<String, Object>> search(String desc);
}
