package com.cxxy.edu.messegeQueue;

import com.alibaba.fastjson.JSONObject;
import com.cxxy.edu.config.RabbitConfig;
import com.cxxy.edu.utils.Utils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @title: MQSender
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/5/2618:04
 */
@Service
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendTestScore(String TYPE, int test_id, int score, String detail, String username) {
        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("test_id", test_id);
        object.put("score", score);
        object.put("detail", detail);
        object.put("TYPE", TYPE);
        amqpTemplate.convertAndSend(RabbitConfig.QUEUE, object);
    }

    public void sendViewProgress(String TYPE, String username, String resourceId, String timeout, String currentTime) {
        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("resourceId", resourceId);
        object.put("timeout", timeout);
        object.put("currentTime", currentTime);
        object.put("TYPE", TYPE);
        amqpTemplate.convertAndSend(RabbitConfig.QUEUE1, object);
    }

}


