package com.zhengpp.padis.controller;


import com.zhengpp.padis.entity.PageData;
import com.zhengpp.padis.entity.User;
import com.zhengpp.padis.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengpeng
 * @since 2019-07-06
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录用户详情
     * @param mv
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "loginUserInfo")
    public ModelAndView loginUserInfo(ModelAndView mv) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        mv.addObject("user",user);
        mv.setViewName("/system/user/userInfo");
        return mv;
    }

    /**
     * 重置密码页面
     * @param mv
     * @return
     * @throws Exception
     */
    @RequestMapping("/toResetPassword")
    public ModelAndView toResetPassword(ModelAndView mv) throws  Exception{
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        mv.addObject("user",user);
        mv.setViewName("/system/user/resetPassword");
        return mv;
    }


    /**
     * 重置密码
     * @param userId
     * @param userPassword
     * @param userNewPassword
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "resetPassword")
    @ResponseBody
    public String resetPassword(@RequestParam(value = "user_id") String userId,
                                      @RequestParam(value = "user_password") String userPassword,
                                      @RequestParam(value = "user_new_password") String userNewPassword) throws Exception{
        Subject subject = SecurityUtils.getSubject();
        PageData pd = userService.findById(userId);
        if(pd==null){
            return "用户不存在";
        }
        if(!pd.getString("user_password").equals(userPassword)){
            return "原密码错误";
        }
        if(pd.getString("user_password").equals(userNewPassword)){
            return "设置密码不能与旧密码相同";
        }
        if(StringUtils.isNotBlank(userNewPassword)){
            pd.put("user_password",userNewPassword);
            userService.updatePassword(pd);
            //重置密码成功退出登录
            subject.logout();
        }
        return "success";
    }
}

