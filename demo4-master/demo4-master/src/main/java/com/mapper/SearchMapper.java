package com.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {
//    @Select("SELECT * FROM file WHERE fileName LIKE CONCAT('%', #{desc}, '%') UNION ALL SELECT * FROM folder WHERE folder_name LIKE CONCAT('%', #{desc}, '%')")
@Select("SELECT * FROM file WHERE fileName LIKE '%' #{desc} '%'")
    List<Map<String, Object>> search(String desc);
}
