package com.example.springgrayarticle.controller;

import com.example.springgrayarticle.common.GrayConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * 测试控制器
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
     * 当前环境
     */
    @Value("${spring.cloud.nacos.discovery.metadata.grayTag:false}")
    private String grayTagConfig;

    /**
     * getArticle
     */
    @GetMapping("/getArticle")
    public ResponseEntity<String> getArticle(@RequestHeader(required = false) String grayTag) {
        System.out.println("header grayTag:" + grayTag);
        String serviceName = GrayConstant.GRAY_VALUE.equals(grayTagConfig) ? "article-gray" : "article-normal";
        System.out.println("当前环境:" + serviceName);
        // 图片文件路径
        String imagePath = GrayConstant.GRAY_VALUE.equals(grayTagConfig) ? "static/images/PatternOpening.jpg" : "static/images/PatternIsSmall.jpg";
        try {
            // 加载图片文件
            ClassPathResource resource = new ClassPathResource(imagePath);
            InputStream inputStream = resource.getInputStream();
            byte[] fileContent = StreamUtils.copyToByteArray(inputStream);
            // 将图片文件转换为 Base64 编码字符串
            String base64String = Base64.getEncoder().encodeToString(fileContent);
            // 打印 Base64 编码字符串
            // System.out.println("Base64 Encoded Image:\n" + base64String);
            ResponseEntity<String> forEntity = restTemplate.getForEntity("http://spring-gray-comment/comment/api/test/getComment", String.class);
            // 构建 HTML 页面的内容
            // 设置 HTTP 响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            // 返回包含 HTML 页面内容的 ResponseEntity
            String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>User Page</title>\n" +
                "    <style>\n" +
                "        /* 设置图片的最大宽度和高度 */\n" +
                "        img {\n" +
                "            max-width: 440px;\n" +
                "            max-height: 880px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Welcome to User Page</h1>\n" +
                "    <h2>" + forEntity.getBody() + "</h2>\n" +
                "    <!-- 显示图片，将 base64String 作为 src 属性值 -->\n" +
                "    <h2> 加载自服务 " + serviceName + " 的图片</h2>\n" +
                "    <img src=\"data:image/jpeg;base64," + base64String + "\" alt=\"Sample Image\">\n" +
                "    <!-- 其他内容 -->\n" +
                "</body>\n" +
                "</html>";
            return new ResponseEntity<>(content, headers, forEntity.getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
