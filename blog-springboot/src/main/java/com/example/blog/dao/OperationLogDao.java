package com.example.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.entity.OperationLog;
import org.springframework.stereotype.Repository;


/**
 * 操作日志
 */
@Repository
public interface OperationLogDao extends BaseMapper<OperationLog> {
}
