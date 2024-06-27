package com.service;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 16:48
 */
import com.entity.User;

public interface UserService {
    User findByUsername(String username);
    User findByEmail(String email);
    void register(User user);
    void updatePassword(String id, String password);
    void deleteUser(String id);
    User findById(String id);
    void update(String id,String username,String email);
}