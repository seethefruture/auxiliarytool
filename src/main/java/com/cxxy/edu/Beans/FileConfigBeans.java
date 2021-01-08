/*
 * 文件名 FileManagerBeans
 * 创建人 吴昊
 * 创建日期 2019/9/25
 * 版权
 */

package com.cxxy.edu.Beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@PropertySource(value = {"classpath:config/fileConfig.properties"}, encoding = "UTF-8")
@ConfigurationProperties(prefix = "filepath")
@Component
public class FileConfigBeans {

    private String rootPath;

    private String doubtImgPath;

    private String resourcePath;

    private String doubtImgHandler;

    private String resourceHandler;

}
