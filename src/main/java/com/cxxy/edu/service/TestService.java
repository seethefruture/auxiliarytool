package com.cxxy.edu.service;

import com.cxxy.edu.entity.Teach;
import com.cxxy.edu.entity.Test;
import com.cxxy.edu.entity.TestQuestion;
import com.cxxy.edu.entity.TestScore;
import com.cxxy.edu.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestService {
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TestScoreMapper testScoreMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private RebuildClassMapper rebuildClassMapper;
    @Autowired
    private TestQuestionMapper testQuestionMapper;

//    //在线测试列表，包括开始截止时间，课程名，班级，年级，已答人数，未答人数，平均分
//    public List<Map<String, Object>> testOnlineList(Integer teacherId) {
//        //所有课程信息的list
//        List<Map<String, Object>> courseList = teacherMapper.selectTeachByTeacherId(teacherId);
//
//        //将所有查出的结果放到list中
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        for (Map<String, Object> map : courseList) {
//            Integer teachId = (Integer) map.get("teach_id");
//            //获得开始截至时间以及test_id
//            List<Test> testList = testMapper.selectByTeachId(teachId);
//            for (Test test : testList) {
//                Integer testId = test.getTestId();
//                //开始时间
//                Date beginTime = test.getTestTime();
//                //截至时间
//                Date endTime = test.getSubmitTime();
//                //题目数
//                int questionNum = test.getTestNum();
//                Map<String, Object> testScore = testScoreMapper.selectByTestId(testId);
//                Long answeredNum;
//                double score;
//                double averageScore;
//                //未能查到数据，说明该测试并未开始或还未有人作答，即已答人数为0
//                if (testScore == null) {
//                    answeredNum = 0L;
//                    score = 0;
//                    averageScore = 0;
//                } else {
//                    //已答题的人数
//                    answeredNum = (Long) testScore.get("num");
//                    //总成绩
//                    score = (double) testScore.get("score");
//                    //平均分
//                    averageScore = score / answeredNum;
//                }
//                Integer classId = (Integer) map.get("class_id");
//                //该teach_id所对应班级的总人数
//                Long classNum = studentMapper.selectByClassId(classId);
////                //该teach_id所对应重修班的总人数
////                Long rebuildClassNum = rebuildClassMapper.selectCountNum(teachId);
//                //该teach_id对应的全部人数
////                Long num = classNum + rebuildClassNum;
//                Long num = classNum;
//                //未答题的人数
//                Long unAnsweredNum = num - answeredNum;
//                //班级名，课程名，年级
//                String courseName = (String) map.get("course_name");
//                Integer classGrade = (Integer) map.get("class_grade");
//                String className = (String) map.get("class_name");
//                //把所有的数据用map保存
//                Map<String, Object> map1 = new HashMap<String, Object>();
//                map1.put("beginTime", beginTime);
//                map1.put("endTime", endTime);
//                map1.put("answeredNum", answeredNum);
//                map1.put("averageScore", averageScore);
//                map1.put("unAnsweredNum", unAnsweredNum);
//                map1.put("courseName", courseName);
//                map1.put("classGrade", classGrade);
//                map1.put("className", className);
//                map1.put("classId", classId);
//                map1.put("testId", testId);
//                map1.put("questionNum", questionNum);
//                list.add(map1);
//            }
//        }
//        return list;
//    }

    public PageInfo<Map<String,Object>> testOnlineList(int courseId, Integer pageNum, Integer pageSize, int sign) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> testList = new ArrayList<Map<String, Object>>();
        //默认选项，即所有作业信息
        if (sign == 1) {
            testList = testMapper.selectByCourseId(courseId);
        }
        //只选取当前该学期的所有作业信息
        else if (sign == 2) {
            testList = testMapper.selectTermByCourseId(courseId);
        }
        PageInfo<Map<String, Object>> testPageInfo = new PageInfo<Map<String, Object>>(testList);
        for (Map<String, Object> map : testPageInfo.getList()) {
            int testId = (int) map.get("test_id");
            Map<String,Object> count = testScoreMapper.selectByTestId(testId);
            Long answeredNum;
            double score;
            double averageScore;
            if(count==null){
                //未能查到数据，说明该测试并未开始，或没有学生回答过，即已答人数为0
                answeredNum = 0L;
                score=0.00;
                averageScore=0.00;
            }
            else {
                //已经回答的人数
                answeredNum = (Long) count.get("num");
                //总成绩
                score = (double) count.get("score");
                //平均分
                averageScore = score / answeredNum;
            }
            int classId = (int) map.get("class_id");
            //该teach_id所对应班级的总人数
            Long classNum = studentMapper.selectByClassId(classId);
            //未答题的人数
            Long unAnsweredNum = classNum - answeredNum;



            map.put("answeredNum", answeredNum);
            map.put("unAnsweredNum", unAnsweredNum);
            map.put("averageScore", averageScore);
        }
        return testPageInfo;
    }

    public Test selectByTestId(Integer testId) {
        return testMapper.selectByTestId(testId);
    }

    public void insertTest(Test test) {
        testMapper.insertTest(test);
    }

    public void deleteByTestId(Integer testId) {
        testMapper.deleteByTestId(testId);
    }

    public List<Map<String, Object>> getAllHistoryTestQuestionByUsernameAndCourseNameAndTeacherName(String username, String courseName, String teacherName) {
        return formatTestQuestionNum(testMapper.getAllHistoryTestQuestionByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName));

    }

    public List<Map<String, Object>> getAllTestQuestionByUsernameAndCourseNameAndTeacherName(String username, String courseName, String teacherName) {
        return formatTestQuestionNum(testMapper.getAllTestQuestionByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName));

    }

    public List<TestQuestion> getAllQuestionByTestId(int test_id) {
        String[] testQuestionContain = testMapper.selectByTestId(test_id).getTestQuestionContain().toString().split(",");
        List<TestQuestion> questions = new ArrayList<>();
        for (String s : testQuestionContain) {
            questions.add(testQuestionMapper.selectAllByQuestionId(Integer.parseInt(s)));
        }
        return questions;
    }

    private List<Map<String, Object>> formatTestQuestionNum(List<Map<String, Object>> questions) {
        for (Map<String, Object> question : questions) {
            if (question.get("test_num").toString().equals("0")) {
                question.put("test_num", question.get("test_question_contain").toString().split(",").length);
            }
        }
        return questions;
    }
}
