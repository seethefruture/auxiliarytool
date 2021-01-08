package com.cxxy.edu.entity;

import lombok.Data;

@Data
public class Teach {
    private Integer teachId;
    private Integer courseId;
    private Integer classId;
    private String teachIntroduction;
    private String teachTime;
}
