package com.cxxy.edu.entity;
import lombok.Data;

@Data
public class Resource {
    private Integer resourceId;

    private Integer courseId;

    private String resourcePath;

    private Integer resourceChapter;

    private Integer resourceSecrecy;

    private Integer resourceSign;

}