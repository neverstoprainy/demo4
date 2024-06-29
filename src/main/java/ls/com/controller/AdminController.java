package ls.com.controller;


import lombok.extern.slf4j.Slf4j;
import ls.com.entity.Admin;
import ls.com.entity.User;
import ls.com.service.AdminService;
import ls.com.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        String adminname = params.get("adminname");
        String password = params.get("password");

        if (adminname == null || password == null) {
            return response(1, null, "用户名或密码不能为空");
        }


        Admin admin = adminService.findByAdminname(adminname);
        if (admin == null || !admin.getPassword().equals(password)) {
            return response(1, null, "用户名或密码错误");
        }

        String token = JwtUtil.generateToken(admin.getAdminname());
        return response(0, token, "登录成功");
    }
    @DeleteMapping("/delete")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {
        try {
            adminService.deleteUser(userId);
            return response(0, null, "用户删除成功");
        } catch (Exception e) {
            log.error("删除用户失败", e);
            return response(1, null, "用户删除失败");
        }
    }

    @GetMapping("/getUserlist")
    public Map<String, Object> getUserList() {
        try {
            List<User> userList = adminService.getUserList();
            List<Map<String, String>> userInfoList = userList.stream()
                    .map(user -> {
                        Map<String, String> userInfo = new HashMap<>();
                        userInfo.put("username", user.getUsername());
                        userInfo.put("email", user.getEmail());
                        return userInfo;
                    })
                    .collect(Collectors.toList());
            return response(0, userInfoList, "获取用户列表成功");
        } catch (Exception e) {
            log.error("获取用户列表失败", e);
            return response(1, null, "获取用户列表失败");
        }
    }

    private Map<String, Object> response(int code, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("data", data);
        response.put("message", message);
        return response;
    }
}
