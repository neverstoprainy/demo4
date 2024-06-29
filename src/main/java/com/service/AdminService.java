package com.service;

import com.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
     List<User> getUserlist();
     void deleteUser(String userId);
}
