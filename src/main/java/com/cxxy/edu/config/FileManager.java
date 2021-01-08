/*
 * 文件名 FileManager
 * 创建人 吴昊
 * 创建日期 2019/9/25
 * 版权
 */

package com.cxxy.edu.config;

import com.cxxy.edu.Beans.FileConfigBeans;
import com.cxxy.edu.entity.FileStoreInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileManager {
    @Autowired
    private FileConfigBeans fileConfigBeans;


    public enum FileType {
        RESOURCE,
        DOUBT
    }

    public FileStoreInfo getFileStoreInfo(FileType fileType, String filePath) {
        FileStoreInfo fileStoreInfo = new FileStoreInfo();
        fileStoreInfo.setCustomPath(filePath);
        switch (fileType) {
            case DOUBT:
                fileStoreInfo.setFilePath(fileConfigBeans.getDoubtImgPath());
                fileStoreInfo.setSqlPath("doubt_img/");
                break;
            case RESOURCE:
                fileStoreInfo.setFilePath(fileConfigBeans.getResourcePath());
                fileStoreInfo.setSqlPath("resource/");
                break;
            default:
                break;
        }
        return fileStoreInfo;
    }

    public String getFilePath(FileType fileType, String sqlPath) {
        StringBuilder filePath = new StringBuilder("");
        switch (fileType) {
            case DOUBT:
                sqlPath = sqlPath.substring(10);
                filePath.append(fileConfigBeans.getDoubtImgPath()+"/");
                filePath.append(sqlPath);
                break;
            case RESOURCE:
                sqlPath = sqlPath.substring(9);
                filePath.append(fileConfigBeans.getResourcePath()+"/");
                filePath.append(sqlPath);
                break;
            default:
                break;
        }
        return filePath.toString();
    }

}
