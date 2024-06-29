package com.controller;

import com.entity.User;
import com.service.AdminService;
import com.service.UserService;
import com.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;


    private Map<String, Object> response(int code, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("data", data);
        response.put("message", message);
        return response;
    }
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params){
        String adminname=params.get("adminname");
        String testpw=params.get("password");
        String turepw = (String) redisUtil.get(adminname);
        if(turepw.equals(testpw)){
            return response(0,"null","登陆成功");
        }
        else
            return response(1,"null","登陆失败");
    }

    @GetMapping("/getUserlist")
    public Map<String, Object> getUser(){
        List<User> users=adminService.getUserlist();
        return response(0,users,"获取成功");
    }

    @PostMapping ("/deleteUser")
    public Map<String, Object> deleteUser(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        log.info("userId是"+userId);
        User user=userService.findById(userId);
        if(user != null){
            try {
                adminService.deleteUser(userId);
                return response(0, null, "删除成功");
            } catch (Exception e) {
                return response(0, null, "删除失败");
            }
        }
        else return response(0, null, "删除失败，没有该用户");
    }

}
