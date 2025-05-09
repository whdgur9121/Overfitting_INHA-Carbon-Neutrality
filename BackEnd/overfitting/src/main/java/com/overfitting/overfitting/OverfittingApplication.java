package com.overfitting.overfitting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Spring Boot 애플리케이션의 진입점입니다.
// @SpringBootApplication은 @Configuration, @EnableAutoConfiguration, @ComponentScan을 포함한 복합 어노테이션입니다.
@SpringBootApplication
public class OverfittingApplication {

    // main 메서드는 자바 애플리케이션의 시작점입니다.
    public static void main(String[] args) {
        // SpringApplication.run()을 호출하여 Spring Boot 앱을 실행합니다.
        SpringApplication.run(OverfittingApplication.class, args);
    }
}

