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
    public void updatePassword(String id, String password) {
        userMapper.updatePassword(id, password);
    }

    @Override
    public void deleteUser(String id) {
        userMapper.delete(id);
    }

    @Override
    public User findById(String id) {
        return userMapper.findById(id);
    }

    @Override
    public void update(String id,String username,String email) {
        userMapper.update(id,username,email);
    }
}