package com.cxxy.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Administrator
 * @title: WebSocketConfig
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/10/1912:51
 */
@Component
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
