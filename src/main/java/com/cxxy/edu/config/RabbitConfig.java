package com.cxxy.edu.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Administrator
 * @title: RabbitConfig
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/5/2617:47
 */
@Configuration
public class RabbitConfig {
    public static final String QUEUE = "testAnswerQueue";
    public static final String QUEUE1 = "viewProgressQueue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE1, true);
    }

}
