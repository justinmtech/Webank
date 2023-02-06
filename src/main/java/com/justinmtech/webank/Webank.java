package com.justinmtech.webank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class Webank {

    public static void main(String[] args) {
        SpringApplication.run(Webank.class, args);
    }
}
