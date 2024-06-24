package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import redis.clients.jedis.Jedis;

@SpringBootApplication
@MapperScan("com.mapper")

public class Demo4Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo4Application.class, args);
    }

}
