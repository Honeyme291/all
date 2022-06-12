package com.project.all.service;

import com.project.all.domain.User;

import java.util.Map;

/**
 * @ClassName:UserService
 * @Author:lxx
 * @Date 2022/5/3 19:54
 **/
public interface UserService {
    public Map<String, Object> createAccount(User user);
    public Map<String, Object> loginAccount(User user);
    public Map<String, Object> activationAccont(String confirmCode);

}
