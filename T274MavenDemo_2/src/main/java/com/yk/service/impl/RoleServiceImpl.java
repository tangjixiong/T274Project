package com.yk.service.impl;

import com.yk.entity.Role;
import com.yk.mapper.RoleMapper;
import com.yk.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> getAllRole() {
        return roleMapper.getAllRole();
    }
}
