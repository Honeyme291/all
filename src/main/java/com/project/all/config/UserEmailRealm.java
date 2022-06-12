package com.project.all.config;

import cn.hutool.crypto.SecureUtil;
import com.project.all.domain.User;
import com.project.all.domain.UserEmailToken;
import com.project.all.mapper.UserMapper;
import com.project.all.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

/**
 * @ClassName:UserEmailRealm
 * @Author:lxx
 * @Date 2022/5/4 21:18
 **/
@Component
public class UserEmailRealm extends AuthorizingRealm {

    // 注入用户业务
    @Autowired
    UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————邮箱登录授权————doGetAuthorizationInfo————");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        info.addStringPermission(user.getPerms());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("————邮箱登录认证————doGetAuthenticationInfo————");
        UserEmailToken userEmailToken = (UserEmailToken) token;
        String userEmail = (String) userEmailToken.getPrincipal();
        // 连接数据库 查询用户数据
        String pwd=userEmailToken.getPassword();
        User user = userMapper.selectUserByEmail(userEmail);
        //因为没有密码，并且验证码在之前就验证了
        if (user == null) {
            throw new UnknownAccountException();
        }
        String md5Pwd = SecureUtil.md5(pwd + user.getSalt());

        if(!md5Pwd.equals(user.getPassword())){
            throw new UnknownAccountException();
        }
        Subject current = SecurityUtils.getSubject();
        Session session = current.getSession();
        session.setAttribute("loginUser",user);
        return new SimpleAuthenticationInfo(user, userEmail, "");
    }

    /**
     * 用来判断是否使用当前的 realm
     *
     * @param var1 传入的token
     * @return true就使用，false就不使用
     */
    @Override
    public boolean supports(AuthenticationToken var1) {
        return var1 instanceof UserEmailToken;
    }
}

