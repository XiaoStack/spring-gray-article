package com.example.springgrayarticle;

import com.example.springgrayarticle.config.GrayRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.springgrayarticle.*", excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = GrayRuleConfig.class)
})
@RibbonClients(value = {
    @RibbonClient(value = "spring-gray-comment", configuration = GrayRuleConfig.class)
})
@EnableDiscoveryClient
public class SpringGrayArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringGrayArticleApplication.class, args);
    }
}
