package com.cxxy.edu.entity;
import lombok.Data;

@Data
public class Notification {
    private Integer notificationId;

    private String notificationDetail;

    private Integer courseId;

    private Integer teacherId;

    private String studentId;

    private String classId;


}