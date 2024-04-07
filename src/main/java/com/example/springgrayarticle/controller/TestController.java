package com.example.springgrayarticle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * XXXX
 *
 * @author yinyong
 * @version 1.0
 * @classname TestControoler
 * @date 2024/3/26 23:26
 */
@RestController
@RequestMapping(value = "/api/test")
public class TestController {

    @Resource
    private RestTemplate restTemplate;

    /**
     * getUser
     */
    @GetMapping(value = "getUser")
    public String getUser() {
        return restTemplate.getForObject("http://spring-gray-comment/comment/api/test/getUser", String.class);
//        System.out.println("article-getUser");
//        restTemplate.getForEntity("");
//        return "article-getUser";
    }
}
