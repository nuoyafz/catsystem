package com.catfeeding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 校园流浪猫投喂记录系统主启动类
 * @author 方舟
 */
@SpringBootApplication
@MapperScan("com.catfeeding.mapper")
public class CatFeedingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatFeedingApplication.class, args);
        System.out.println("====================================");
        System.out.println("校园流浪猫投喂记录系统启动成功！");
        System.out.println("访问地址：http://localhost:8080");
        System.out.println("====================================");
    }
}
