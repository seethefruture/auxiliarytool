package com.cxxy.edu.entity;

import lombok.Data;

@Data
public class Admin {
    private Integer adminId;

    private String adminUsername;

    private String adminName;

    private String adminPassword;

    private Integer adminLevel;

    private Integer collegeId;


}