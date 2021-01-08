package com.cxxy.edu.messegeQueue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cxxy.edu.CustomException.InternalServerError;
import com.cxxy.edu.config.RabbitConfig;
import com.cxxy.edu.entity.TestFalseAnswer;
import com.cxxy.edu.entity.TestQuestion;
import com.cxxy.edu.entity.TestScore;
import com.cxxy.edu.service.*;
import com.cxxy.edu.utils.Utils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @title: MQReveiver
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/5/2618:35
 */
@Service
public class MQReceiver {
    @Autowired
    private TestQuestionService testQuestionService;
    @Autowired
    private TestFalseAnswerService testFalseAnswerService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private Test_scoreService test_scoreService;
    @Autowired
    private ResourceViewService resourceViewService;

    @RabbitListener(queues = {RabbitConfig.QUEUE, RabbitConfig.QUEUE1})
    public void receive(JSONObject jsonObject) throws Exception {
        switch (jsonObject.get("TYPE").toString()) {
            case "TEST_SCORE":
                int score = Integer.valueOf(jsonObject.get("score").toString());
                int test_id = Integer.valueOf(jsonObject.get("test_id").toString());
                int student_id = studentService.queryByUsername(jsonObject.get("username").toString()).getStudentId();
                TestScore testScore = new TestScore();
                testScore.setStudentId(student_id);
                testScore.setTestId(test_id);
                testScore.setScore(String.valueOf(score));
                if (test_scoreService.insert(testScore) <= 0) {
                    throw new Exception("插入成绩异常");
                }
                List<String> list = Utils.mySplit(jsonObject.get("detail").toString());
                for (String str : list) {
                    JSONObject detail = JSON.parseObject(str);
                    TestQuestion testQuestion = testQuestionService.selectAllByQuestionId(Integer.valueOf(detail.get("id").toString()));
                    int num = testQuestion.getTestQuestionNum();//当前引用次数
                    float correct = testQuestion.getTestQuestionCorrect();//当前正确率
                    int num_now = num + 1;
                    float correct_now;
                    if (detail.containsKey("false")) {
                        System.out.println("第" + detail.get("id") + "题选择的错误选项是" + detail.get("false"));
                        TestFalseAnswer testFalseAnswer = new TestFalseAnswer();
                        testFalseAnswer.setFalseQuestionId(Integer.valueOf(detail.get("id").toString()));
                        testFalseAnswer.setStudentId(student_id);
                        testFalseAnswer.setTestId(test_id);
                        testFalseAnswer.setFalseAnswerChoose(detail.get("false").toString());
                        if (testFalseAnswerService.insert(testFalseAnswer) <= 0) {
                            throw new Exception("插入错题异常");
                        }
                        correct_now = correct * num / num_now;
                    } else {
                        System.out.println("第" + detail.get("id") + "题选择了正确选项");
                        correct_now = (correct * num + 1) / num_now;
                    }
                    if (testQuestionService.updateQuestionCorrect(testQuestion.getQuestionId(), correct_now, num_now) <= 0) {
                        throw new Exception("更新题目统计异常");
                    }
                }
                break;
            case "viewProgress":
                String username = jsonObject.getString("username");
                String resourceId = jsonObject.getString("resourceId");
                String timeout = jsonObject.getString("timeout");
                String currentTime = jsonObject.getString("currentTime");
                if(username.equals("")|| resourceId.equals("") || timeout.equals("") || currentTime.equals("")){
                    System.out.println("mq 接收消息异常");
                    break;
                }
                System.out.println(username + "的课程id" + resourceId + "在页面停留了" + timeout + "进度" + currentTime);
                resourceViewService.insertViewProgress(username,resourceId,timeout,currentTime);
                break;
            case "UNKNOWN":
                System.out.println("UNKNOWN");
                break;
            default:
                throw new IllegalArgumentException("消息队列类型传递参数类型不合法");
        }

    }
}
