package com.yk.service;

import com.yk.entity.User;
import com.yk.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface UserService {
    //登录功能
    public User login(String code, String password);
    //分页查询用户信息
    public Page pagationUser(HashMap<String, Object> hashMap);
    //新增用户
    public Integer insertUser(User user);
    //根据Id查询用户信息
    public User getUserById(Integer id);
    //修改用户
    public Integer updateUser(User user);
    //根据用户编码查询用户信息
    public User getUserByCode(String code);
}
