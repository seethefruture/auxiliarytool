package com.cxxy.edu.entity;

import lombok.Data;

/**
 * @author Administrator
 * @title: FileStoreInfo
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/11/2915:08
 */
@Data
public class FileStoreInfo {

    private String filePath;
    private String sqlPath;
    private String customPath;

    public String getStorePath() {
        return this.filePath + "/" + customPath;
    }

    public String getSQLPath() {
        return this.sqlPath + customPath;
    }

}
