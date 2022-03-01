package com.example.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dao.UserRoleDao;
import com.example.blog.entity.UserRole;
import com.example.blog.service.UserRoleService;
import org.springframework.stereotype.Service;


/**
 * 用户角色服务
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {


}
