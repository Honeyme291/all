package com.project.all.domain;

import lombok.Data;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @ClassName:UserEmailToken
 * @Author:lxx
 * @Date 2022/5/4 21:17
 **/
@Data // 使用lombok 生成get方法、set方法
public class UserEmailToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    private String userEmail;
    private String password;
    private boolean rememberMe;
    private String host;

    public UserEmailToken() {
        this.rememberMe = false;
    }

    public UserEmailToken(String userEmail) {
        this(userEmail, false, null);
    }

    public UserEmailToken(String userEmail, boolean rememberMe) {
        this(userEmail, rememberMe, null);
    }

    public UserEmailToken(String userEmail, boolean rememberMe, String host) {
        this.userEmail = userEmail;
        this.rememberMe = rememberMe;
        this.host = host;
    }
    public UserEmailToken(String userEmail,String password) {
        this.userEmail = userEmail;
        this.password=password;
        this.rememberMe = false;
        this.host = null;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    /**
     * 重写getPrincipal方法
     */
    @Override
    public Object getPrincipal() {
        return userEmail;
    }

    /**
     * 重写getCredentials方法
     */
    @Override
    public Object getCredentials() {
        return userEmail;
    }

    public String getPassword(){
        return password;
    }
}
