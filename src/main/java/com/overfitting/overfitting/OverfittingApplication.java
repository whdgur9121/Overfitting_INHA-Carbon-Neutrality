package com.overfitting.overfitting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


@SpringBootApplication
public class OverfittingApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(OverfittingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("📌 등록된 매핑 경로 목록:");
        context.getBean(RequestMappingHandlerMapping.class)
                .getHandlerMethods()
                .forEach((key, value) -> System.out.println(key + " => " + value.getMethod().getName()));
    }
}

