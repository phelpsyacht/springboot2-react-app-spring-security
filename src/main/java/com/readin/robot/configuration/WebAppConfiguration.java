package com.readin.robot.configuration;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.readin.component.UploadProperties;


@Configuration
public class WebAppConfiguration implements WebMvcConfigurer {
    @Resource
    private UploadProperties uploadProperties;
    /**
     * 配置拦截器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (uploadProperties.getOsType() == 1) {
            registry.addResourceHandler("/upload/**").addResourceLocations("file:///" + uploadProperties.getPath());
        } else {
            registry.addResourceHandler("/upload/**").addResourceLocations("file:///" + uploadProperties.getPath() + "/");
        }
    }
}
