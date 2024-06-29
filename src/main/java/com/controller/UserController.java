package com.controller;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 16:52
 */

import com.entity.Folder;
import com.entity.User;
import com.service.FolderService;
import com.service.UserService;
import com.utils.JwtUtil;
import com.utils.RedisUtil;
import com.utils.CaptchaUtil;
import com.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CaptchaUtil captchaUtil;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private FolderService folderService;

    @GetMapping("/sendEmailCode")
    public Map<String, Object> sendEmailCode(@RequestParam String email) {
        String emailCode = generateEmailCode();
        String emailCodeKey = "emailCode:" + email;
        redisUtil.set(emailCodeKey, emailCode, 300); // 验证码有效期5分钟

        try {
            emailUtil.sendEmail(email, "邮箱验证码", "您的验证码是：" + emailCode);
        } catch (MessagingException e) {
            return response(1, null, "发送邮件失败");
        }

        return response(0, null, "验证码已发送");
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> params) {
        // 生成用户根目录ID
        String rootFolderId = UUID.randomUUID().toString().replace("-", "");

        String username = params.get("username");
        String password = params.get("password");
        String email = params.get("email");
        String emailCode = params.get("emailCode");
        String captcha = params.get("captcha");
        String captchaId = params.get("captchaId");

        log.info(emailCode);
        String cachedCaptcha = (String) redisUtil.get(captchaId);
        if (cachedCaptcha == null || !cachedCaptcha.equalsIgnoreCase(captcha)) {
            return response(1, null, "验证码错误");
        }
        String storedEmailCode = (String) redisUtil.get("emailCode:" + email);
        if (storedEmailCode == null || !storedEmailCode.equals(emailCode)) {
            return response(1, null, "邮箱验证码错误");
        }
        log.info(cachedCaptcha);
        log.info(storedEmailCode);

        // 创建用户根目录
        Folder rootFolder = new Folder();
        rootFolder.setId(rootFolderId);
        rootFolder.setFolderName(username + "_root");
        rootFolder.setCreateBy(username);
        rootFolder.setCreateTime(new Date());
        rootFolder.setUpdateTime(new Date());
        rootFolder.setIsDelete("N");
        rootFolder.setParentFolderId(null);  // 根目录没有父目录
        rootFolder.setRootFolderId(rootFolderId);

        folderService.insert(rootFolder);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setCreateTime(new Date());
        user.setIsAdmin(false);
        user.setLoginTime(new Date());
        user.setRootFolderId(rootFolderId);
        userService.register(user);

        return response(0, null, "注册成功");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String email = params.get("email");
        String password = params.get("password");
        String captcha = params.get("captcha");
        String captchaId = params.get("captchaId");

        String cachedCaptcha = (String) redisUtil.get(captchaId);
        log.info("这是验证码ID:" + cachedCaptcha);
        if (cachedCaptcha == null || !cachedCaptcha.equalsIgnoreCase(captcha)) {
            return response(1, null, "验证码错误");
        }

        User user = null;
        if (username != null) {
            user = userService.findByUsername(username);
        } else if (email != null) {
            user = userService.findByEmail(email);
        }

        if (user == null || !user.getPassword().equals(password)) {
            return response(1, null, "用户名或密码错误");
        }

        String token = JwtUtil.generateToken(user.getId());
        return response(0, token, "登录成功");
    }

    @Autowired(required = false)
    private JwtUtil jwt;

    @PostMapping("/signout")
    public Map<String, Object> signout(@RequestHeader("Authorization") String token) {
        jwt.addTokenToBlacklist(token, 60000);
        if(jwt.isTokenBlacklisted(token) == true) {
            return response(1, null, "成功登出！");
        }
        return response(0,null,"登出失败！");
    }

    @GetMapping("/getAvatar")
    public Map<String, Object> getAvatar(@RequestHeader("Authorization") String token) {
        String userid = JwtUtil.getClaimsFromToken(token).getSubject();
        User user = userService.findById(userid);
        if (user == null) {
            return response(1, null, "用户不存在");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("userAvatar", user.getAvatar());
        return response(0,data,"获取成功");
    }


    @PostMapping("/changePassword")
    public Map<String, Object> updatePassword(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> params) {
        log.info(token);
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        String emailCode = params.get("emailCode");
        log.info(newPassword);
        String id = JwtUtil.getClaimsFromToken(token).getSubject();
        User user = userService.findById(id);
        log.info(id);
        if (user == null || !user.getPassword().equals(oldPassword)) {
            return response(1, null, "旧密码错误");
        }


        userService.updatePassword(user.getId(), newPassword);
        return response(0, null, "密码修改成功");
    }

    @GetMapping("/info")
    public Map<String, Object> getUserInfo(@RequestHeader("Authorization") String token) {
        String userid = JwtUtil.getClaimsFromToken(token).getSubject();
        User user = userService.findById(userid);
        log.info("这是："+user.getRootFolderId());
        if (user == null) {
            return response(1, null, "用户不存在");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());
        data.put("userRole", user.getIsAdmin() ? "admin" : "user");
        data.put("userRootFolder", user.getRootFolderId());

        return response(0, data, "ok");
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        String emailCode = params.get("emailCode");

        userService.deleteUser(userId);
        return response(0, null, "注销成功");
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> params) {
        String userId = JwtUtil.getClaimsFromToken(token).getSubject();
        String emailCode = params.get("email");
        String username = params.get("username");
        User user = userService.findById(userId);
        log.info(userId);
        if (user == null) {
            return response(1, null, "用户不存在");
        }
        userService.update(userId,username,emailCode);
        return response(0, null, "修改成功");
    }
    @GetMapping("/captcha")
    public Map<String, Object> getCaptcha() {
        String captchaId = Long.toString(System.currentTimeMillis()).substring(9,13);
        log.info("Captcha ID: " + captchaId);

        Map<String, String> captchaData = captchaUtil.generateCaptcha();
        String captchaText = captchaData.get("text");
        String captchaImageBase64 = captchaData.get("imageBase64");

        redisUtil.set(captchaId, captchaText, 300); // 5 minutes expiration time

        Map<String, Object> data = new HashMap<>();
        data.put("Id", captchaId);
        data.put("base64", captchaImageBase64);

        return response(0, data, "ok");
    }

    private String generateEmailCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private Map<String, Object> response(int code, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("data", data);
        response.put("message", message);
        return response;
    }
}