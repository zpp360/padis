package com.zhengpp.padis.controller;

import com.zhengpp.padis.entity.PageData;
import com.zhengpp.padis.entity.User;
import com.zhengpp.padis.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by zpp360 on 2019/7/6.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/")
    public ModelAndView toIndex(ModelAndView mv){
        mv.setViewName("redirect:/index");
        return mv;
    }

    @RequestMapping(value = "/toLogin")
    public ModelAndView toLogin(ModelAndView mv){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if(user!=null){
            //已经登录，跳转index
            mv.setViewName("redirect:/index");
        }else{
            mv.setViewName("/login");
        }
        return mv;
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public String login( @RequestParam(value = "user_name") String userName,@RequestParam("user_password") String userPassword) throws Exception {

        PageData pd = userService.findByUserName(userName);
        if(pd==null){
            return "用户名或密码错误";
        }
        String password = pd.getString("user_password");
        if(StringUtils.isBlank(password) || !password.equals(userPassword)){
            return "用户名或密码错误";
        }
        //用户名密码匹配成功，登录
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName,userPassword);
        subject.login(token);
        return "success";
    }

    /**
     * 首页
     * @param mv
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(ModelAndView mv){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        System.out.println(user);
        mv.addObject("user",user);
        mv.setViewName("/index");
        return mv;
    }

    /**
     * 退出
     * @param mv
     * @return
     */
    @RequestMapping(value = "logout")
    public ModelAndView logout(ModelAndView mv){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        mv.setViewName("redirect:/toLogin");
        return mv;
    }
}
