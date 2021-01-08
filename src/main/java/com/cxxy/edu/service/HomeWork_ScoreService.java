package com.cxxy.edu.service;

import com.cxxy.edu.entity.HomeworkQuestion;
import com.cxxy.edu.entity.HomeworkScore;
import com.cxxy.edu.mapper.HomeworkScoreMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: HomeWork_ScoreService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1323:27
 */
@Service
public class HomeWork_ScoreService {
    @Autowired
    private HomeworkScoreMapper mapper;


    public void insert(HomeworkScore homeWork_score) {
        mapper.insert(homeWork_score);
    }

    public HomeworkScore queryById(int id) {
        return mapper.queryById(id);
    }

    public List<HomeworkScore> selectByQuestionId(Integer homeworkId) {
        return mapper.selectByQuestionId(homeworkId);
    }

    public HomeworkScore selectByHomeworkIdAndStudentId(Integer homeworkId, Integer studentId) {
        return mapper.selectByHomeworkIdAndStudentId(homeworkId, studentId);
    }

    public void updateByHomeworkScoreId(Integer homeworkScoreId, String score) {
        mapper.updateByHomeworkScoreId(homeworkScoreId, score);
    }

    public String queryScoreByHomeWorkId(String username, int homeworkId) {
        return mapper.queryScoreByTestId(username, homeworkId);
    }

    public PageInfo<Map<String,Object>> selectByQuestionId(int homeworkId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> homeworkList = mapper.selectAnswerStudentByQuestionId(homeworkId);
        PageInfo<Map<String,Object>> homeworkPageInfo = new PageInfo<Map<String,Object>>(homeworkList);
        return homeworkPageInfo;
    }
}
