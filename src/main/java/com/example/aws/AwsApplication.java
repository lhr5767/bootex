package com.example.aws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // jpa auditing 활성화
@SpringBootApplication
public class AwsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsApplication.class, args);
    }

}
