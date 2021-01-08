package com.cxxy.edu.entity;

import lombok.Data;

@Data
public class TestScore {
    private Integer testScoreId;

    private Integer testId;

    private Integer studentId;

    private String score;

}