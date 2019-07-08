package com.zhengpp.padis.interceptor;

import com.zhengpp.padis.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zpp360 on 2019/7/6.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if(user!=null){
            return true;
        }else{
            request.getRequestDispatcher("/toLogin").forward(request, response);
            return false;
        }
    }
}
