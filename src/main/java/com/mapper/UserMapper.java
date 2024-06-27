package com.mapper;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 16:47
 */

import com.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

    @Insert("INSERT INTO user (username, password, email, createTime, updateTime, usedCapacity, capacity, isAdmin, avatar, FolderId, loginTime) VALUES (#{username}, #{password}, #{email}, #{createTime}, #{updateTime}, #{usedCapacity}, #{capacity}, #{isAdmin}, #{avatar}, #{folderId}, #{loginTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    void updatePassword(@Param("id") String id, @Param("password") String password);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void delete(@Param("id") String id);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") String id);

    @Update("UPDATE user SET username = #{username}, email = #{email} WHERE id = #{id}")
    void update(@Param("id") String id, @Param("username") String username, @Param("email") String email);

}