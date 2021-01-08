package com.cxxy.edu.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Doubt {
    private Integer doubtId;

    private Integer teachId;

    private String doubtQuestion;

    private String doubtQuestionPath;

    private Integer studentId;

    private String doubtAnswer;

    private String doubtAnswerPath;

    private Date doubtTime;


}