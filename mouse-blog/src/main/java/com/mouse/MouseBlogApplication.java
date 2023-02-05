package com.mouse;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 星星
 * @create 2023-02-03 1:18
 */
@SpringBootApplication
@MapperScan("com.mouse.mapper")
public class MouseBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(MouseBlogApplication.class,args);
    }
}
