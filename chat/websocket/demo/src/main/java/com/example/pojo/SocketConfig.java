package com.example.pojo;

import lombok.Data;
import org.springframework.boot.SpringApplication;

@Data
public class SocketConfig {
    /**
     * 聊天类型：0：群聊， 1：私聊
     */
    private int type;

    /**
     * 发送者
     */
    private String fromUser;

    /**
     * 接收者
     */
    private String toUser;

    /**
     * 消息
     */
    private String msg;

    /**
     * 消息类型：
     * 1： 文本
     * 2： 图片
     */
    private int code;
}
