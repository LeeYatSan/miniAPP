package com.miniAPP.mapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan(basePackages="com.miniAPP.mapper")
@ComponentScan(basePackages= {"com.miniAPP", "org.n3r.idworker"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}