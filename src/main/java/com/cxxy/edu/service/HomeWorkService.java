package com.cxxy.edu.service;

import com.cxxy.edu.entity.Homework;
import com.cxxy.edu.entity.HomeworkQuestion;
import com.cxxy.edu.entity.Test;
import com.cxxy.edu.entity.TestQuestion;
import com.cxxy.edu.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Administrator
 * @title: HomeWorkService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/5/1320:58
 */
@Service
public class HomeWorkService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private HomeWorkMapper homeWorkMapper;
    @Autowired
    private HomeworkScoreMapper homeworkScoreMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private RebuildClassMapper rebuildClassMapper;
    @Autowired
    private HomeworkQuestionService homeworkQuestionService;

    public List<Map<String, Object>> getAllHistoryHomeWorkByUsernameAndCourseNameAndTeacherName(String username, String courseName, String teacherName) {
        return homeWorkMapper.getAllHistoryHomeWorkByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName);
    }

    public List<Map<String, Object>> getAllHomeWorkByUsernameAndCourseNameAndTeacherName(String username, String courseName, String teacherName) {
        return homeWorkMapper.getAllHomeWorkByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName);
    }

    public PageInfo<Map<String,Object>> homeworkOnlineList(int courseId,Integer pageNum,Integer pageSize,int sign) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> homeworkList = new ArrayList<Map<String, Object>>();
        //默认选项，即所有作业信息
        if (sign == 1) {
            homeworkList = teacherMapper.selectByCourseId(courseId);
        }
        //只选取当前该学期的所有作业信息
        else if (sign == 2) {
            homeworkList = teacherMapper.selectByCourseIdByTerm(courseId);
        }
        PageInfo<Map<String, Object>> homeworkPageInfo = new PageInfo<Map<String, Object>>(homeworkList);
        for (Map<String, Object> map : homeworkPageInfo.getList()) {
            int homeworkId = (int) map.get("homework_id");
            //已经回答的人数
            Long answeredNum = homeworkScoreMapper.selectByHomeworkId(homeworkId);
            if (answeredNum == null) {
                //未能查到数据，说明该测试并未开始，或没有学生回答过，即已答人数为0
                answeredNum = 0L;
            }
            int classId = (int) map.get("class_id");
            //该teach_id所对应班级的总人数
            Long classNum = studentMapper.selectByClassId(classId);
//                //该teach_id所对应重修班的总人数
//                Long rebuildClassNum = rebuildClassMapper.selectCountNum(teachId);
            //该teach_id对应的全部人数
            //Long num = classNum + rebuildClassNum;
            //未答题的人数
            Long unAnsweredNum = classNum - answeredNum;
            //已批改的人数
            Long correctNum = homeworkScoreMapper.selectCount(homeworkId);
            if (correctNum == null) {
                correctNum = 0L;
            }
            map.put("answeredNum", answeredNum);
            map.put("unAnsweredNum", unAnsweredNum);
            map.put("correctNum", correctNum);
        }
        return homeworkPageInfo;
    }
    public void deleteByHomeworkId(Integer homeworkId) {
        homeWorkMapper.deleteByHomeworkId(homeworkId);
    }

    public void insert(Homework homework) {
        homeWorkMapper.insert(homework);
    }

    public List<HomeworkQuestion> getAllQuestionByHomeworkId(int homework_id) {
        String[] homeworkQuestionContain = homeWorkMapper.selectByHomeworkId(homework_id).getContainQuestionId().toString().split(",");
        List<HomeworkQuestion> questions = new ArrayList<>();
        for (String s : homeworkQuestionContain) {
            questions.add(homeworkQuestionService.selectAllByQuestionId(Integer.parseInt(s)));
        }
        return questions;
    }

    public Homework selectByHomeworkId(int homework_id) {
        return homeWorkMapper.selectByHomeworkId(homework_id);
    }
}
