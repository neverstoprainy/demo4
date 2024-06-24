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
    void updatePassword(Long id, String password);
    void deleteUser(Long id);
    User findById(Long id);
}