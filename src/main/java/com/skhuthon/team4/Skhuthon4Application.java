package com.skhuthon.team4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling  // 추가
public class Skhuthon4Application {
    public static void main(String[] args) {
        SpringApplication.run(Skhuthon4Application.class, args);
    }
}