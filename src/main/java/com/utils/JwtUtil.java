package com.utils;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 16:50
 */

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "mySecretKey";

    public static String generateToken(String Id) {
        return Jwts.builder()
                .setSubject(Id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean isTokenExpired(String token) {
        final Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";

    // 添加 token 到黑名单，设置过期时间
    public   void addTokenToBlacklist(String token, long expirationTimeInMinutes) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, "invalid", expirationTimeInMinutes, TimeUnit.MINUTES);
    }

    // 检查 token 是否在黑名单中
    public boolean isTokenBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        return redisTemplate.hasKey(key);
    }

    // 从黑名单移除 token
    public void removeTokenFromBlacklist(String token) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.delete(key);
    }

}
