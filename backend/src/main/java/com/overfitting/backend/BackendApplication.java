// ✅ BackendApplication.java
package com.overfitting.backend;

import org.springframework.boot.SpringApplication; // Spring Boot 애플리케이션 실행을 위한 클래스 import
import org.springframework.boot.autoconfigure.SpringBootApplication; // Spring Boot 설정 자동화를 위한 어노테이션 import

@SpringBootApplication // 이 클래스가 Spring Boot의 시작점임을 명시
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args); // 스프링 부트 애플리케이션 실행
    }
}
