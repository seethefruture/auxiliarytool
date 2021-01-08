package com.cxxy.edu.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Homework {
    private Integer homeworkId;

    private Integer teachId;

    private Integer homeworkChapter;

    private String containQuestionId;

    private Date submitTime;


}