package com.cxxy.edu.service;

import com.cxxy.edu.entity.HomeworkQuestion;
import com.cxxy.edu.mapper.HomeworkQuestionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkQuestionService {
    @Autowired
    private HomeworkQuestionMapper homeworkQuestionMapper;

    public PageInfo<HomeworkQuestion> selectAllByCourseIdAndChapter(Integer id, int chapter,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<HomeworkQuestion> teacherList = homeworkQuestionMapper.selectAllByCourseIdAndChapter(id,chapter);
        PageInfo<HomeworkQuestion> teacherPageInfo = new PageInfo<HomeworkQuestion>(teacherList);;
        return teacherPageInfo;
    }

    public HomeworkQuestion selectAllByQuestionId(Integer questionId){
        return homeworkQuestionMapper.selectAllByQuestionId(questionId);
    }

    public void update(String detail,String answer,Integer id){
        homeworkQuestionMapper.update(detail,answer,id);
    }

    public void insert(HomeworkQuestion homeworkQuestion){
        homeworkQuestionMapper.insert(homeworkQuestion);
    }

    public void deleteByQuestionId(Integer questionId){
        homeworkQuestionMapper.deleteByQuestionId(questionId);
    }
}
