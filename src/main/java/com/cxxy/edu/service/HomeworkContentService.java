package com.cxxy.edu.service;

import com.cxxy.edu.entity.Homework;
import com.cxxy.edu.entity.HomeworkContent;
import com.cxxy.edu.entity.HomeworkQuestion;
import com.cxxy.edu.mapper.HomeWorkMapper;
import com.cxxy.edu.mapper.HomeworkContentMapper;
import com.cxxy.edu.mapper.HomeworkQuestionMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeworkContentService {
    @Autowired
    private HomeWorkMapper homeWorkMapper;
    @Autowired
    private HomeworkQuestionMapper homeworkQuestionMapper;
    @Autowired
    private HomeworkContentMapper homeworkContentMapper;


    //查询该学生本次作业的所有内容
    public List<Map<String, Object>> selectHomeworkContent(Integer studentId, Integer homeworkId) {
        List<Map<String, Object>> homework1 = new ArrayList<>();
        Homework homework = homeWorkMapper.selectByHomeworkId(homeworkId);
        String homeworkContent = homework.getContainQuestionId();
        String[] questionIds = homeworkContent.split(",");
        for (int i = 0; i < questionIds.length; i++) {
            //题目的id
            int questionId = Integer.parseInt(questionIds[i]);
            HomeworkQuestion homeworkQuestion = homeworkQuestionMapper.selectAllByQuestionId(questionId);
            //问题
            String questionDetail = homeworkQuestion.getHomeworkQuestionDetail();
            HomeworkContent homeworkContent1 = homeworkContentMapper.selectByHomeworkQuestionIdAndStudentId(questionId, studentId,homeworkId);
            //学生回答
            String homeworkContentAnswer;
            //学生未回答
            if (homeworkContent1 == null) {
                homeworkContentAnswer = "";
            } else {
                homeworkContentAnswer = homeworkContent1.getHomeworkContentAnswer();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("question", questionDetail);
            map.put("answer", homeworkContentAnswer);
            homework1.add(map);
        }
        return homework1;

    }

    public void insert(HomeworkContent homeworkContent) {
        homeworkContentMapper.insert(homeworkContent);
    }
}
