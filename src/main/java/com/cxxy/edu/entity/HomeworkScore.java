package com.cxxy.edu.entity;

import java.util.Date;
import lombok.Data;

@Data
public class HomeworkScore {
    private Integer homeworkScoreId;

    private Integer homeworkId;

    private Integer studentId;

    private String score;

    private Date submitTime;


}