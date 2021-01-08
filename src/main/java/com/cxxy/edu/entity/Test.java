package com.cxxy.edu.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Test {
    private Integer testId;

    private String testQuestionContain;

    private Date testTime;

    private Integer teachId;

    private Date submitTime;

    private Integer testNum;

}