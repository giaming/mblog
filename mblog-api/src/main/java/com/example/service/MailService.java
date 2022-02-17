package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Friend;

/**
 * 服务接口
 *
 * @author giaming
 * @since 2022-02-12 18:04:11
 * @description 由 Mybatisplus Code Generator 创建
 */
public interface MailService {

    /**
     * 发送普通文本邮件
     *
     * @param toAccount 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String toAccount, String subject, String content);

    /**
     * 发送HTML邮件
     *
     * @param toAccount 收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    void sendHtmlMail(String toAccount, String subject, String content);


}
