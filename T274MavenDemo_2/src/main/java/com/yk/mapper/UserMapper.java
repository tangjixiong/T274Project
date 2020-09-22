package com.yk.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yk.entity.Role;
import com.yk.entity.User;

import javax.jws.soap.SOAPBinding;

public interface UserMapper {
	//根据用户编码和密码查询用户信息
	public User getUserByCodeAndPassword(@Param("code") String code, @Param("password") String password);
	//统计数据总条数
	public Integer countUserPagation(HashMap<String, Object> hash);
	//查询指定页码数据
	public List<User> pagationUser(HashMap<String, Object> hash);
	//新增用户
	public Integer insertUser(User user);
	//根据Id查询用户信息
	public User getUserById(Integer id);
	//修改用户
	public Integer updateUser(User user);
	//根据用户编码查询用户信息
	public User getUserByCode(String code);
}
