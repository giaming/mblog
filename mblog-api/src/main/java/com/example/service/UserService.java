package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.lang.vo.UserInfo;
import com.example.entity.User;

import java.util.List;

/**
 * 服务接口
 *
 * @author giaming
 * @since 2022-02-12 18:04:11
 * @description 由 Mybatisplus Code Generator 创建
 */
public interface UserService extends IService<User> {
    /**
     * 查询所有用户（只含有部分信息）
     *
     * @return 用户（只含有部分信息）list
     */
    List<UserInfo> getUserInfoList();
}
