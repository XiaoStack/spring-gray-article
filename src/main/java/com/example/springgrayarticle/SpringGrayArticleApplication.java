package com.example.springgrayarticle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.example.springgrayarticle")
@EnableDiscoveryClient
public class SpringGrayArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringGrayArticleApplication.class, args);
    }

}
