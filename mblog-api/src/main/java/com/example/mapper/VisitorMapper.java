package com.example.mapper;

import com.example.entity.Visitor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * (visitor)数据Mapper
 *
 * @author giaming
 * @since 2022-02-12 18:04:11
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
@Repository
public interface VisitorMapper extends BaseMapper<Visitor> {

    /**
     * 查询UUID是否已经存在
     * @param uuid
     * @return 0为不存在，1为存在
     */
    int hasUUID(String uuid);

    /**
     * 通过uuid找到访客
     * @param uuid
     * @return 返回访问对象
     */
    @Select("select id, uuid, ip, ip_source, os, browser, create_time, last_time, pv,user_agent " +
            "from visitor  where uuid=#{uuid}")
    Visitor selectByUUid(@Param("uuid") String uuid);

    /**
     * 计算pv
     * @return
     */
    @Select("select COALESCE(sum(pv), 0) from visitor")
    int getPV();
}
