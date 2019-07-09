package com.zhengpp.padis.config;

import com.zhengpp.padis.entity.PageData;
import com.zhengpp.padis.entity.User;
import com.zhengpp.padis.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zpp360 on 2019/7/6.
 */
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        PageData pd = null;
        String password = null;
        User user = new User();
        try {
            pd  = userService.findByUserName(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(pd == null){
            throw new AccountException("用户名不正确");
        }else{
            password = pd.getString("user_password");
        }

        if(StringUtils.isBlank(password)){
            throw new AccountException("用户名不正确");
        }else if (! pd.getString("user_password").equals(new String((char[]) token.getCredentials()))) {
            throw new AccountException("密码不正确");
        }else{
            user.setUserId(pd.getString("user_id"));
            user.setUserName(pd.getString("user_name"));
            user.setUserPhone(pd.getString("user_phone"));
            user.setUserRole(pd.getString("user_role"));
            user.setUserUnit(pd.getString("user_unit"));
            user.setUserNumber(pd.getString("user_number"));
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证————");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }
}
