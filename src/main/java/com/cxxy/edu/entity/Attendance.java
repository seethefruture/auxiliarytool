package com.cxxy.edu.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Attendance {
    private Integer attendanceId;

    private Integer techId;

    private Date date;

    private Byte lessonNum;

    private Integer studentId;

    private Byte attendTag;


}