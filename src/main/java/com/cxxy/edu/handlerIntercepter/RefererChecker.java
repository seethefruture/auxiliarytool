/*
 * 文件名 RefererChecker
 * 创建人 吴昊
 * 创建日期 2019/9/12
 * 版权
 */

package com.cxxy.edu.handlerIntercepter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RefererChecker implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String referer = request.getHeader("referer");
        if (referer != null) {
            String address = request.getServerName();
            /*如果发起请求的点不是本站的页面*/
            if (!referer.contains(address)) {
                throw new Exception("禁止跨站引用！"+request.getRequestURI());
            }
        }
        /* 发起请求域名或IP*/
        return true;
    }
}
