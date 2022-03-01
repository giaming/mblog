package com.example.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dao.ChatRecordDao;
import com.example.blog.entity.ChatRecord;
import com.example.blog.service.ChatRecordService;
import org.springframework.stereotype.Service;

/**
 * 聊天记录服务
 */
@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordDao, ChatRecord> implements ChatRecordService {



}
