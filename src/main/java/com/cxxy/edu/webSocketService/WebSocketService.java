package com.cxxy.edu.webSocketService;

import com.cxxy.edu.CustomException.InternalServerError;
import com.cxxy.edu.notifucationService.StudentNotificationService;
import com.cxxy.edu.redisService.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Administrator
 * @title: WebSocketService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/10/1912:55
 */
@Component
@ServerEndpoint(value = "/websocket")
public class WebSocketService {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<WebSocketService>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    public Session session;

    //当前连接的客户端的username
    public String username;

    //  这里使用静态，让 service 属于类
    private static RedisService redisService;

    // 注入的时候，给类的 service 注入
    @Autowired
    public void setRedisService(RedisService redisService) {
        WebSocketService.redisService = redisService;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            System.out.println("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InternalServerError {
        if (message.startsWith("username")) {
            this.username = message.substring(8);
            System.out.println("客户端" + this.username + "连接!");

            // 向刚上线的用户推送离线消息
            if (redisService.sHasKey(StudentNotificationService.HOMEWORK_NOTIFICATION_SET_NAME, username)) {
                System.out.println("用户" + username + "有homework的新通知");
                sendMessage("homework");
                if (redisService.setRemove(StudentNotificationService.HOMEWORK_NOTIFICATION_SET_NAME, username) == 0) {
                    throw new InternalServerError("移除通知set异常");
                }
            }
            if (redisService.sHasKey(StudentNotificationService.TEST_NOTIFICATION_SET_NAME, username)) {
                System.out.println("用户" + username + "有test的新通知");
                sendMessage("test");
                if (redisService.setRemove(StudentNotificationService.TEST_NOTIFICATION_SET_NAME, username) == 0) {
                    throw new InternalServerError("移除通知set异常");
                }
            }
        } else {
            System.out.println("来自客户端的消息:" + message);
        }


    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        System.out.println(message);
        for (WebSocketService item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }
}
