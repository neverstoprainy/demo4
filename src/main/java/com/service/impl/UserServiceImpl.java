package com.service.impl;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 16:49
 */
import com.entity.User;
import com.mapper.UserMapper;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public void register(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updatePassword(Long id, String password) {
        userMapper.updatePassword(id, password);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.delete(id);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }
}