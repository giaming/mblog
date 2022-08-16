package com.example.util;

import com.alibaba.fastjson.JSONObject;
import com.example.pojo.SocketConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket/{nickname}")
@Component
@Slf4j
public class MyWebSocket {
    private static Map<String, Session> map = new HashMap<>();
    private static CopyOnWriteArraySet<MyWebSocket> clients = new CopyOnWriteArraySet<>();
    private Session session;
    private String nickname;

    @OnOpen
    public void onOpen(Session session, @PathParam("nickname") String nickname) {
        this.session = session;
        this.nickname = nickname;

        map.put(session.getId(), session);

        clients.add(this);
        log.info("有新用户加入,当前人数为：", clients.size());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",nickname + "已成功连接(其频道号为：" + session.getId() + "),当前在线人数为：" + clients.size());
        jsonObject.put("code", 1);
        this.session.getAsyncRemote().sendText(jsonObject.toJSONString());
    }

    @OnClose
    public void onClose() {
        clients.remove(this);
        log.info("有用户断开连接，当前用户人数为:{}", clients.size());
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("nickname") String nickname) {
        log.info("来自客户端：{}发来的消息：{}", nickname, message);

        SocketConfig socketConfig;
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", JSONObject.parseObject(message).get("code"));

        try {
            socketConfig = objectMapper.readValue(message, SocketConfig.class);
            jsonObject.put("nickname", nickname+":");
            // 私聊
            if (socketConfig.getType() == 1) {
                socketConfig.setFromUser(session.getId());
                Session fromSession = map.get(socketConfig.getFromUser());
                Session toSession = map.get(socketConfig.getToUser());

                // 接受者存在，发送以下消息给接受者和发送者
                if (toSession != null) {
//                   fromSession.getAsyncRemote().sendText(nickname+":"+socketConfig.getMsg());
//                   toSession.getAsyncRemote().sendText(nickname+":"+socketConfig.getMsg());
                    jsonObject.put("msg",socketConfig.getMsg());
                    fromSession.getAsyncRemote().sendText(jsonObject.toJSONString());
                    toSession.getAsyncRemote().sendText(jsonObject.toJSONString());
                } else {
                    // 接受者不存在，发送以下消息给发送者
                    fromSession.getAsyncRemote().sendText("频道号不存在或对方不在线");
                    jsonObject.put("msg", "频道号不存在或对方不在线");
                }
            } else {
                // 群聊
                jsonObject.put("msg", socketConfig.getMsg());
                broadcast(jsonObject.toJSONString());
            }
        } catch (Exception e) {
            log.info("发送消息出错");
            e.printStackTrace();
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.error("出现错误");
        error.printStackTrace();
    }

    /**
     * 自定义群发消息
     * @param msg
     */
    private void broadcast(String msg) {
        for (MyWebSocket client : clients) {
            // 异步发送消息
            client.session.getAsyncRemote().sendText(msg);
        }
    }
}
