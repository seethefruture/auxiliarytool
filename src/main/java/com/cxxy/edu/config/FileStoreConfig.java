package com.cxxy.edu.config;

import com.cxxy.edu.Beans.FileConfigBeans;
import com.cxxy.edu.CustomException.InternalServerError;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author Administrator
 * @title: FileStoreConfig
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/11/2913:49
 */
@Configuration
public class FileStoreConfig implements WebMvcConfigurer, InitializingBean {
    @Autowired
    private FileConfigBeans fileConfigBeans;

    private String doubtImgPath;
    private String resourcePath;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("检查静态文件路径");
        String rootPath = fileConfigBeans.getRootPath();
        String doubtImgPath = fileConfigBeans.getDoubtImgPath();
        String resourcePath = fileConfigBeans.getResourcePath();
        checkDirectory(rootPath, "根");
        if (doubtImgPath != null) {
            checkDirectory(doubtImgPath, "doubtImg");
            this.doubtImgPath = doubtImgPath;
        } else {
            System.out.println("在根路径创建默认doubtImg路径......");
            if (new File(rootPath + "/doubtImg").mkdir()) {
                System.out.println("创建静态资源doubtImg路径成功");
            } else {
                throw new InternalServerError("创建静态资源doubtImg路径失败！");
            }
            this.doubtImgPath = rootPath + "/doubtImg";
        }
        if (resourcePath != null) {
            checkDirectory(resourcePath, "resource");
            this.resourcePath = resourcePath;
        } else {
            System.out.println("在根路径创建默认resource路径......");
            if (new File(rootPath + "/resourcePath").mkdir()) {
                System.out.println("创建静态资源resourcePath路径成功");
            } else {
                throw new InternalServerError("创建静态资源resourcePath路径失败！");
            }
            this.resourcePath = rootPath + "/resourcePath";
        }

    }

    private void checkDirectory(String directoryPath, String type) throws InternalServerError {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            System.out.println("静态资源" + type + "路径检查成功");
        } else {
            System.out.println("静态资源" + type + "路径" + directory + "不存在,尝试创建......");
            boolean result = directory.mkdirs();
            if (result) {
                System.out.println("创建静态资源" + type + "路径成功");
            } else {
                throw new InternalServerError("创建静态资源" + type + "路径失败！");
            }
        }
    }

    //增加资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String doubtImgHandler = fileConfigBeans.getDoubtImgHandler();
        String resourceHandler = fileConfigBeans.getResourceHandler();
        if (doubtImgHandler == null) {
            doubtImgHandler = "/doubt_img";
        } else {
            doubtImgHandler = figureHandler(doubtImgHandler);
        }
        if (resourceHandler == null) {
            resourceHandler = "/resource";
        } else {
            resourceHandler = figureHandler(resourceHandler);
        }
        System.out.println("doubt_img映射目录:" + doubtImgHandler);
        registry.addResourceHandler(fileConfigBeans.getDoubtImgHandler() + "/**").addResourceLocations("file:" + fileConfigBeans.getDoubtImgPath() + "/");

        System.out.println("resource映射目录:" + resourceHandler);
        registry.addResourceHandler(fileConfigBeans.getResourcePath() + "/**").addResourceLocations("file:" + fileConfigBeans.getResourcePath() + "/");
    }

    private String figureHandler(String rawHandler) {
        if (!rawHandler.substring(0, 1).equals("/")) {
            rawHandler = "/" + rawHandler;
        }
        if (rawHandler.endsWith("/")) {
            rawHandler = rawHandler.substring(0, rawHandler.length() - 1);
        }
        return rawHandler;
    }

}
