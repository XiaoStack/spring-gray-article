package com.example.springgrayarticle.config;

import com.example.springgrayarticle.common.RestTemplateRequestInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * XXXX
 *
 * @author yinyong
 * @version 1.0
 * @classname RestTemplateConfig
 * @date 2024/4/7 23:10
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateRequestInterceptor()));
        return restTemplate;
    }
}
