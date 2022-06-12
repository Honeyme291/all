package com.project.all.mapper;

import com.project.all.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName:UserMapper
 * @Author:lxx
 * @Date 2022/5/3 19:57
 **/
@Mapper
@ResponseBody
public interface UserMapper {

    public int insertUser(User user);

    public User selectUserByEmail(String email);

    public User selectUserByConfirmCode(String confirmCode);

    public int updateUserByConfirmCode(String confirmCode);
}
