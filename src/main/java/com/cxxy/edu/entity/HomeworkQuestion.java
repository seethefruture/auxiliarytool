package com.cxxy.edu.entity;
import lombok.Data;

@Data
public class HomeworkQuestion {
    private Integer homeworkQuestionId;

    private String homeworkQuestionDetail;

    private String homeworkQuestionAnswer;

    private Integer courseId;

    private Integer homeworkQuestionChapter;

    private Integer homeworkQuestionNum;

    private float homeworkQuestionCorrect;



}