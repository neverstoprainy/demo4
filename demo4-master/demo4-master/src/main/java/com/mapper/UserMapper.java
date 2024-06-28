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

    @Insert("INSERT INTO user(username, email, password, userRole, userRootFolder) VALUES(#{username}, #{email}, #{password}, #{userRole}, #{userRootFolder})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    void updatePassword(@Param("id") Long id, @Param("password") String password);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void delete(@Param("id") Long id);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Long id);
}