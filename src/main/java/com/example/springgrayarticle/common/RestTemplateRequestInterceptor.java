package com.example.springgrayarticle.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * RestTemplate 请求拦截器
 *
 * @author yinyong
 * @version 1.0
 * @classname RestTemplateRequestInterceptor
 * @date 2024/4/7 22:34
 */
@Component
@Slf4j
public class RestTemplateRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
        // 在这里可以获取并复制请求头
        // 例如，从当前线程的请求属性中获取请求头
        HttpServletRequest httpServletRequest = getCurrentHttpServletRequest();
        if (httpServletRequest != null) {
            List<String> headerNames = Collections.list(httpServletRequest.getHeaderNames());
            for (String headerName : headerNames) {
                String headerValue = httpServletRequest.getHeader(headerName);
                httpRequest.getHeaders().add(headerName, headerValue);
                // 将灰度标记的请求头透传给下个服务
                if (StringUtils.equalsIgnoreCase(GrayConstant.GRAY_HEADER, headerName) && Boolean.TRUE.toString().equals(headerValue)) {
                    // 保存灰度发布的标记
                    GrayRequestContextHolder.setGrayTag(true);
                }
            }
        }
        if (GrayRequestContextHolder.getGrayTag() == null) {
            GrayRequestContextHolder.setGrayTag(false);
        }
        // 执行请求
        return execution.execute(httpRequest, bytes);
    }

    // 获取当前线程的 HttpServletRequest 对象
    private HttpServletRequest getCurrentHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest();
        }
        return null;
    }
}
