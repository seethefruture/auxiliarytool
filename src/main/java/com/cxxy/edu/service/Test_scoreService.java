package com.cxxy.edu.service;

import com.cxxy.edu.entity.Student;
import com.cxxy.edu.entity.TestScore;
import com.cxxy.edu.mapper.StudentMapper;
import com.cxxy.edu.mapper.TestScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: Test_scoreService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1413:28
 */
@Service
public class Test_scoreService {
    @Autowired
    private TestScoreMapper testScoreMapper;
    @Autowired
    private StudentMapper studentMapper;

    public int insert(TestScore test_score) {
        return testScoreMapper.insert(test_score);
    }

    public TestScore queryById(int id) {
        return testScoreMapper.queryById(id);
    }

    //查询该testId中所有学生的成绩
    public List<Map<String, Object>> studentScore(int testId) {
        List<TestScore> list = testScoreMapper.selectAllByTestId(testId);
        List<Map<String, Object>> studentScore = new ArrayList<Map<String, Object>>();
        for (TestScore testScore : list) {
            int studentId = testScore.getStudentId();
            String score = testScore.getScore();
            Student student = studentMapper.queryById(studentId);
            String studentName = student.getStudentName();
            String studentNo = student.getStudentNo();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("score", score);
            map.put("name", studentName);
            map.put("no", studentNo);
            studentScore.add(map);
        }
        return studentScore;
    }

    public String queryScoreByTestId(String username, int testId) {
        return testScoreMapper.queryScoreByTestId(username, testId);
    }
}
