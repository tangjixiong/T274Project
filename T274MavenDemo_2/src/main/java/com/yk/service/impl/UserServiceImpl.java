package com.yk.service.impl;

import com.yk.entity.User;
import com.yk.mapper.UserMapper;
import com.yk.service.UserService;
import com.yk.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
@Service("userService")
@Scope("singleton")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public  UserServiceImpl(){
        System.out.println("UserServiceImpl构造方法");
    }

    @Override
    public User login(String code, String password) {
        return userMapper.getUserByCodeAndPassword(code,password);
    }

    @Override
    public Page pagationUser(HashMap<String, Object> hashMap) {
        //1、查询数据总条数
        Integer total=userMapper.countUserPagation(hashMap);
        //2、创建Page对象
        Page page=new Page(hashMap.get("curpage"),hashMap.get("pagesize"),total);
        hashMap.put("startrow",page.getStartrow());
        hashMap.put("pagesize",page.getPageSize());
        //3、查询指定页码数据
        List<User> list=userMapper.pagationUser(hashMap);
        //4、保存数据到page
        page.setList(list);
        return page;
    }

    @Override
    public Integer insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User getUserByCode(String code) {
        return userMapper.getUserByCode(code);
    }
}
