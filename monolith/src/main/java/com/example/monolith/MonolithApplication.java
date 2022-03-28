package com.example.monolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MonolithApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MonolithApplication.class, args);
    }

}
