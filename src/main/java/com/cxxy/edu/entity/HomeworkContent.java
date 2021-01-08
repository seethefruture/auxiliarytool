package com.cxxy.edu.entity;

import lombok.Data;
import org.apache.ibatis.annotations.Delete;

@Data
public class HomeworkContent {

    private Integer homeworkContentId;

    private Integer studentId;

    private Integer homeworkId;

    private Integer homeworkQuestionId;

    private String homeworkContentAnswer;
}
