package com.cxxy.edu.config;

import com.cxxy.edu.handlerIntercepter.LoginHandlerIntercepter;
import com.cxxy.edu.handlerIntercepter.RefererChecker;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author Administrator
 * @title: WebViewConfig
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1721:57
 */
@Configuration
public class WebViewConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/student").setViewName("student/login");
        registry.addViewController("/teacher").setViewName("teacher/login");
        registry.addViewController("/admin").setViewName("admin/login");
        registry.addViewController("/resourceView").setViewName("student/innerHtml/resourceView");
        registry.addViewController("/").setViewName("login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginHandlerIntercepter())
                //指定要拦截的请求 /** 表示拦截所有请求
                .addPathPatterns("/**")
                //排除不需要拦截的请求路径
                .excludePathPatterns("/login", "/student", "/register/**", "/teacher", "/teacher/doLogin", "/error", "/admin", "/admin/doLogin","/loginCheck")
                //springboot2+之后需要将静态资源文件的访问路径 也排除
                .excludePathPatterns(Arrays.asList("/assets/**", "/vendor/**", "/js/**", "/css/**", "/doubt_img/**","/images/**","/fonts/**"));
        registry.addInterceptor(new RefererChecker())
                //指定要拦截的请求 /** 表示拦截所有请求
                .addPathPatterns("/**")
                //排除不需要拦截的请求路径
                .excludePathPatterns("/login", "/student", "/register/**", "/teacher", "/teacher/doLogin", "/error", "/admin", "/admin/doLogin")
                //springboot2+之后需要将静态资源文件的访问路径 也排除
                .excludePathPatterns(Arrays.asList("/assets/**", "/vendor/**", "/js/**", "/css/**", "/doubt_img/**","/images/**"));
    }

}
