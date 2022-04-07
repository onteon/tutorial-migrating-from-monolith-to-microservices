package com.example.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CompanyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class, args);
    }

}
