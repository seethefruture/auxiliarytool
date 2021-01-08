package com.cxxy.edu.notifucationService;

import com.cxxy.edu.CustomException.InternalServerError;
import com.cxxy.edu.redisService.RedisService;
import com.cxxy.edu.service.StudentService;
import com.cxxy.edu.webSocketService.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @author Administrator
 * @title: StudentNotificationService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/10/1917:52
 */
@Service
public class StudentNotificationService {

    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private RedisService redisService;

    private StudentService studentService;

    //Redis 存放新作业的set key
    public static final String HOMEWORK_NOTIFICATION_SET_NAME = "ntHomeworkSet";
    //Redis 存放新测试的set key
    public static final String TEST_NOTIFICATION_SET_NAME = "ntTestSet";


    public enum NotificationType {
        HOMEWORK_NOTIFICATION_TAG,
        TEST_NOTIFICATION_TAG;
    }


    //给某个username发送通知
    public void sendNotificationByStudentUsername(String username, NotificationType notificationType) throws IOException, InternalServerError {
        String notificationMsg = "";
        String redisKeyName = "";
        switch (notificationType) {
            case TEST_NOTIFICATION_TAG:
                notificationMsg = "test";
                redisKeyName = TEST_NOTIFICATION_SET_NAME;
                break;
            case HOMEWORK_NOTIFICATION_TAG:
                notificationMsg = "homework";
                redisKeyName = HOMEWORK_NOTIFICATION_SET_NAME;
                break;
            default:
                break;
        }
        boolean isClientOnline = false;
        Session targetSession = null;
        for (WebSocketService item : WebSocketService.webSocketSet) {
            if (item.username.equals(username)) {
                isClientOnline = true;
                targetSession = item.session;
            }
        }
        //客户端在线，发送通知
        if (isClientOnline) {
            System.out.println("客户端" + username + "在线，发送即时通知");
            webSocketService.sendMessage(targetSession, notificationMsg);
        }
        //客户端离线，将消息放到redis中
        else {
            System.out.println("客户端" + username + "离线，保存通知");
            if (redisService.sSet(redisKeyName, username) == 0) {
                throw new InternalServerError("redis存储失败");
            }
        }

    }

    public void sendNotificationByStudentId(int studentId, NotificationType notificationType) throws IOException, InternalServerError {
        sendNotificationByStudentUsername(studentService.queryById(studentId).getStudentUsername(), notificationType);
    }

//    TODO
//    public void sendNotificationByClassId(int classId, NotificationType notificationType) throws IOException, InternalServerError {
//        List<Student> informStudent = studentService.selectStudentByClassId(classId);
//        for (Student student : informStudent) {
//            sendNotificationByStudentUsername(student.getStudentUsername(), notificationType);
//        }
//    }

}
