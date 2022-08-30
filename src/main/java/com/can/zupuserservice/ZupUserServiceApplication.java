package com.can.zupuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ZupUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZupUserServiceApplication.class, args);
    }

}
