package com.mapper;

import com.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select * from user")
    List<User> getUserlist();

    @Delete("delete from user where id = #{userId}")
    void deleteUser(String userId);
}
