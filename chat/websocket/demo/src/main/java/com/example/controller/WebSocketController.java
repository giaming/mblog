package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class WebSocketController {
    @RequestMapping("/websocketTest")
    public ModelAndView sendMessage(Map<String, Object> map) throws IOException {
        try {
            log.info("跳转到websocket页面上");
            return new ModelAndView("webSocketClients", map);
        } catch (Exception e) {
            log.info("页面跳转发生错误:{}", e.getMessage());
            map.put("msg", "请求错误");
            return new ModelAndView("error", map);
        }
    }
}
