package com.cxxy.edu.CunstomConfig;

import org.apache.catalina.valves.AccessLogValve;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @author Administrator
 * @title: ServletContainerCunstomConfig
 * @projectName auxiliarytool
 * @description:
 * @date 2019/4/2816:36
 */
@Configuration
public class ServletContainerCunstomConfig {

    @Bean
    public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setPort(8080);
        File tomcatBase = new File("logs/tomcatBase");
        System.out.println("tomcat文件检查" + (checkFile(tomcatBase) ? "成功" : "失败，请手动创建"));
        factory.setBaseDirectory(tomcatBase);
        File tomcatLogs = new File("logs/tomcatLogs");
        System.out.println("tomcat日志文件检查" + (checkFile(tomcatBase) ? "成功" : "失败，请手动创建"));
        AccessLogValve log = new AccessLogValve();
        log.setDirectory(tomcatLogs.getAbsolutePath());
        log.setBuffered(true);
        log.setPrefix("spring-boot-access-log");
        log.setPattern("host:%h****authentication:%u****date:%t****request:%r****response statue:%s****size:%bB");
        log.setSuffix(".log");
        log.setFileDateFormat(".yyyy-MM-dd");
        factory.addContextValves(log);
        return factory;
    }

    private boolean checkFile(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            boolean result = directory.mkdirs();
            return result;

        } else {
            return true;
        }
    }
}
