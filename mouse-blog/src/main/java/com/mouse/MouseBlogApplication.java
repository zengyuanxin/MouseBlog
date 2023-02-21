package com.mouse;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 星星
 * @create 2023-02-03 1:18
 */
@SpringBootApplication
@MapperScan("com.mouse.mapper")
@EnableScheduling
@EnableSwagger2
public class MouseBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(MouseBlogApplication.class,args);
    }
}
