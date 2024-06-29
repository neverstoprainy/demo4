package com.service.impl;

import com.entity.User;
import com.mapper.AdminMapper;
import com.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public List<User> getUserlist(){return adminMapper.getUserlist();}

    @Override
    public void deleteUser(String userId){adminMapper.deleteUser(userId);}

}
