package com.cxxy.edu.controller;

import com.cxxy.edu.CustomException.InternalServerError;
import com.cxxy.edu.entity.TestQuestion;
import com.cxxy.edu.messegeQueue.MQSender;
import com.cxxy.edu.service.*;
import com.cxxy.edu.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class StudentTestController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TestService testService;
    @Autowired
    private Test_scoreService test_scoreService;
    @Autowired
    private MQSender sender;
    @Autowired
    private TestFalseAnswerService testFalseAnswerService;
    @Autowired
    private ClassService classService;

    @RequestMapping("/getCourseInfoForTest")
    public String getCourseInfoForTest(HttpSession session, Model model) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> courseInfo = studentService.getAllCourseForTestByUsername(username);
            model.addAttribute("courseInfos", courseInfo);
        }
        return "student/innerHtml/courseInfoForTest";
    }

    @RequestMapping("/getCourseInfoForTestAJAX")
    @ResponseBody
    public List<Map<String, Object>> getCourseInfoForTestAJAX(HttpSession session, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> courseInfo = studentService.getAllCourseForTestByUsername(username);
            response.setHeader("result", "success");
            return courseInfo;
        }
        response.setHeader("result", "fail");
        return null;
    }

    @RequestMapping("/getCourseInfoForHistoryTest")
    public String getCourseInfoForHistoryTest(HttpSession session, Model model) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> courseInfo = studentService.getAllCourseForHistoryTestByUsername(username);
            model.addAttribute("courseInfos", courseInfo);
        }
        return "student/innerHtml/courseInfoForHistoryTest";
    }

    @RequestMapping("/getCourseInfoForHistoryTestAJAX")
    @ResponseBody
    public List<Map<String, Object>> getCourseInfoForHistoryTestAJAX(HttpSession session, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> courseInfo = studentService.getAllCourseForHistoryTestByUsername(username);
            response.setHeader("result", "success");
            return courseInfo;
        }
        response.setHeader("result", "fail");
        return null;
    }


    @RequestMapping("/getHistoryTest")
    public String getHistoryTest(HttpSession session, Model model, String courseName, String teacherName) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> historyTestInfo = testService.getAllHistoryTestQuestionByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName);
            Utils.timeFormat(historyTestInfo);
            model.addAttribute("historyTestInfos", historyTestInfo);
        }
        return "student/innerHtml/historyTest";
    }

    @RequestMapping("/getHistoryTestAJAX")
    @ResponseBody
    public List<Map<String, Object>> getHistoryTestAJAX(HttpSession session, String courseName, String teacherName, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> historyTestInfo = testService.getAllHistoryTestQuestionByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName);
            Utils.timeFormat(historyTestInfo);
            response.addHeader("result", "success");
            return historyTestInfo;
        }
        response.addHeader("result", "fail");
        return null;
    }

    @RequestMapping("/getTest")
    public String getTest(HttpSession session, Model model, String courseName, String teacherName) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> testInfo = testService.getAllTestQuestionByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName);
            Utils.timeFormat(testInfo);
            model.addAttribute("testInfos", testInfo);
        }
        return "student/innerHtml/test";
    }

    @RequestMapping("/getTestAJAX")
    @ResponseBody
    public List<Map<String, Object>> getTestAJAX(HttpSession session, String courseName, String teacherName, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> testInfo = testService.getAllTestQuestionByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName);
            Utils.timeFormat(testInfo);
            response.addHeader("result", "success");
            return testInfo;
        }
        response.addHeader("result", "fail");
        return null;
    }

    @RequestMapping("/viewHistoryTest")
    public String viewHistoryTest(HttpSession session, Model model, int test_id) throws InternalServerError {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            String testScore = test_scoreService.queryScoreByTestId(username, test_id);
            if (testScore == null) {
                testScore = "你没有完成本次测试！";
            }
            //int num = testService.selectByTestId(test_id).getTestNum();
            List<TestQuestion> testQuestions = null;
           // List<Map<String, Object>> testFalseAnswers = null;
            try {
                testQuestions = testService.getAllQuestionByTestId(test_id);
           //     testFalseAnswers = testFalseAnswerService.getFalseRecordByStudentIdAndTestId(studentService.queryByUsername(username).getStudentId(), test_id);
            } catch (NullPointerException e) {
                throw new InternalServerError("找不到对应题目");
            }
            model.addAttribute("TestScore", testScore);
            model.addAttribute("TestQuestionInfos", Utils.handleQuestionType(testQuestions,testQuestions.size()));
        }
        return "student/innerHtml/historyTestView";
    }

    @PostMapping("/viewFalseAnswer")
    @ResponseBody
    public List<Map<String, Object>> viewFalseAnswerAJAX(HttpSession session, int test_id,HttpServletResponse response) throws InternalServerError {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> testFalseAnswers = null;
            try {
                testFalseAnswers = testFalseAnswerService.getFalseRecordByStudentIdAndTestId(studentService.queryByUsername(username).getStudentId(), test_id);
            } catch (NullPointerException e) {
                throw new InternalServerError("找不到对应题目");
            }
            response.setHeader("result", "success");
            return testFalseAnswers;
        }
        response.setHeader("result", "fail");
        return null;
    }


    @RequestMapping("/finishTest")
    public String finishTest(HttpSession session, Model model, int test_id) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<TestQuestion> testQuestions = testService.getAllQuestionByTestId(test_id);
            int num = testService.selectByTestId(test_id).getTestNum();
            model.addAttribute("TestQuestionInfos", Utils.handleQuestionType(testQuestions, num));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            model.addAttribute("submitTime", format.format(testService.selectByTestId(test_id).getSubmitTime()));
        }
        return "student/innerHtml/finishTest";
    }

          @PostMapping("/submitTest")
        @ResponseBody
        public String submitTest(HttpSession session, int score, int test_id, String detail) {
            String username = session.getAttribute("username").toString();
            if (username != null) {
                sender.sendTestScore("TEST_SCORE", test_id, score, detail, username);
            }
            return "success";

    }


}
