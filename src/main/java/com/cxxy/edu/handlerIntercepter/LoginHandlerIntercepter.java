package com.cxxy.edu.handlerIntercepter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 * @title: LoginHandlerIntercepter
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1922:50
 */
public class LoginHandlerIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURI();
        //拦截！判断是否是LOGIN 其他的登录后才能访问
//            if (url.toLowerCase().indexOf("login") >= 0 || url.toLowerCase().indexOf("register") > 0 || url.toLowerCase().indexOf("activate") > 0 || url.toLowerCase().indexOf("retrievepassword") > 0) {
//                return true;
//            }
        //通过seesion来判断你是否登陆成功
        HttpSession session = httpServletRequest.getSession();

        if (httpServletRequest.getRequestURI().equals("/")) {
            return true;
        } else if (httpServletRequest.getRequestURI().equals("/websocket")) {
            return true;
        }
        if (session.getAttribute("username") != null) {
            return true;
        } else if (session.getAttribute("teacher") != null) {
            return true;
        } else if (session.getAttribute("admin") != null) {
            return true;
        } else if (session.getAttribute("gradeAdmin") != null) {
            return true;
        }
//        httpServletResponse.sendRedirect("/login");
        System.out.println(httpServletRequest.getRequestURI() + "被拦截");
        return false;
    }

}
