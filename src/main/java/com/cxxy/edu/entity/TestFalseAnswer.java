package com.cxxy.edu.entity;

import lombok.Data;

@Data
public class TestFalseAnswer {
    private Integer testFalseAnswerId;

    private Integer testId;

    private Integer studentId;

    private Integer falseQuestionId;

    private String falseAnswerChoose;
}
