package com.cxxy.edu.service;

import com.cxxy.edu.entity.TestQuestion;
import com.cxxy.edu.mapper.TestQuestionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestQuestionService {
    @Autowired
    private TestQuestionMapper testQuestionMapper;

    public PageInfo<TestQuestion> selectAllByCourseIdAndChapter(Integer id, Integer chapter,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<TestQuestion> teacherList = testQuestionMapper.selectAllByCourseIdAndChapter(id, chapter);
        PageInfo<TestQuestion> teacherPageInfo = new PageInfo<TestQuestion>(teacherList);;
        return teacherPageInfo;
    }

    public TestQuestion selectAllByQuestionId(Integer questionId) {
        return testQuestionMapper.selectAllByQuestionId(questionId);
    }

    public void insert(TestQuestion testQuestion) {
        testQuestionMapper.insert(testQuestion);
    }

    public void update(String detail, String questionA, String questionB, String questionC, String questionD, String type, String answer, Integer id) {
        testQuestionMapper.update(detail, questionA, questionB, questionC, questionD, type, answer, id);
    }

    public void deleteByQuestionId(Integer questionId) {
        testQuestionMapper.deleteByQuestionId(questionId);
    }

    public int updateQuestionCorrect(int question_id, float correct, int num) {
        return testQuestionMapper.updateQuestionCorrect(question_id, correct, num);
    }
}
