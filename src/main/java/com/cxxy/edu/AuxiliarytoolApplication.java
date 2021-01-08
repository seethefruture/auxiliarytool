package com.cxxy.edu;

import com.cxxy.edu.CustomException.InternalServerError;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication()
@MapperScan({"com.cxxy.edu.mapper"})
@Transactional(rollbackFor = InternalServerError.class)
public class AuxiliarytoolApplication {
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(AuxiliarytoolApplication.class, args);
        System.out.println("AuxiliarytoolApplication start success! Enjoy it!\n" +
                "http://127.0.0.1:8080/");
    }

}
