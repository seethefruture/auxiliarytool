package com.cxxy.edu.entity;
import lombok.Data;

@Data
public class TestQuestion {
    private Integer questionId;

    private Integer courseId;

    private String testQuestionDetail;

    private String testQuestionA;

    private String testQuestionB;

    private String testQuestionC;

    private String testQuestionD;

    private String testQuestionAnswer;

    private Integer testQuestionType;

    private Integer testQuestionNum;

    private float testQuestionCorrect;

    private Integer testQuestionChapter;
}
