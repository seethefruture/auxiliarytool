package com.cxxy.edu.service;

import com.cxxy.edu.entity.TestFalseAnswer;
import com.cxxy.edu.mapper.TestFalseAnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: TestFalseAnswerService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/5/2721:48
 */
@Service
public class TestFalseAnswerService {
    @Autowired
    private TestFalseAnswerMapper testFalseAnswerMapper;

    public int insert(TestFalseAnswer testFalseAnswer) {
        return testFalseAnswerMapper.insert(testFalseAnswer);
    }

    public List<Map<String,Object>> getFalseRecordByStudentIdAndTestId(int student_id, int test_id) {
        return testFalseAnswerMapper.getFalseRecordByStudentIdAndTestId(student_id, test_id);
    }
}
