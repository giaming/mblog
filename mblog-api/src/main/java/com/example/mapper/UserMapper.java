package com.example.mapper;

import com.example.common.lang.vo.UserInfo;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (user)数据Mapper
 *
 * @author giaming
 * @since 2022-02-12 18:04:11
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户部分信息list
     */
    @Select("select id, nickname, username, avatar, email, status, create_time, update_time, role " +
            " from user  order by create_time desc")
    List<UserInfo> getUserInfo();
}
