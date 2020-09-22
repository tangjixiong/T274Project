package com.yk.interceptor;

import com.yk.entity.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("=================执行权限验证的拦截器====================");
        User user=(User)request.getSession().getAttribute("userSession");
        if(user==null){//没有登录
            response.sendRedirect( request.getContextPath()+"/401.jsp");
            return false;
        }
        return true;
    }
}
