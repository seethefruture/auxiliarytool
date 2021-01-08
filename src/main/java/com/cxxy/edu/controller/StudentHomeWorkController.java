package com.cxxy.edu.controller;

import com.alibaba.fastjson.JSONObject;
import com.cxxy.edu.entity.*;
import com.cxxy.edu.entity.Class;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class StudentHomeWorkController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private HomeWorkService homeWorkService;
    @Autowired
    private HomeWork_ScoreService homeWork_scoreService;
    @Autowired
    private HomeworkContentService homeworkContentService;
    @Autowired
    private ClassService classService;


    @RequestMapping("/getCourseInfoForHomeWork")
    public String getCourseInfoForHomeWork(HttpSession session, Model model) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> courseInfo = studentService.getAllCourseForHomeWorkByUsername(username);
            model.addAttribute("courseInfos", courseInfo);
        }
        return "student/innerHtml/courseInfoForHomeWork";
    }

    @RequestMapping("/getCourseInfoForHomeWorkAJAX")
    @ResponseBody
    public List<Map<String, Object>> getCourseInfoForHomeWorkAJAX(HttpSession session, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> courseInfo = studentService.getAllCourseForHomeWorkByUsername(username);
            response.setHeader("result", "success");
            return courseInfo;
        }
        response.setHeader("result", "fail");
        return null;

    }

    @RequestMapping("/getCourseInfoForHistoryHomeWork")
    public String getCourseInfoForHistoryHomeWork(HttpSession session, Model model) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> courseInfo = studentService.getAllCourseForHistoryHomeWorkByUsername(username);
            model.addAttribute("courseInfos", courseInfo);
        }
        return "student/innerHtml/courseInfoForHistoryHomeWork";
    }

    @RequestMapping("/getCourseInfoForHistoryHomeWorkAJAX")
    @ResponseBody
    public List<Map<String, Object>> getCourseInfoForHistoryHomeWorkAJAX(HttpSession session, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> courseInfo = studentService.getAllCourseForHistoryHomeWorkByUsername(username);
            response.setHeader("result", "success");
            return courseInfo;
        }
        response.setHeader("result", "fail");
        return null;
    }

    @RequestMapping("/getHistoryHomeWork")
    public String getHistoryHomeWork(HttpSession session, Model model, String courseName, String teacherName) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> historyHomeWorkInfo = homeWorkService.getAllHistoryHomeWorkByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName);
            Utils.timeFormat(historyHomeWorkInfo);
            model.addAttribute("historyHomeWorkInfos", historyHomeWorkInfo);
        }
        return "student/innerHtml/historyHomeWork";
    }

    @RequestMapping("/getHistoryHomeWorkAJAX")
    @ResponseBody
    public List<Map<String, Object>> getHistoryHomeWorkAJAX(HttpSession session, String courseName, String teacherName, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> historyHomeWorkInfo = homeWorkService.getAllHistoryHomeWorkByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName);
            Utils.timeFormat(historyHomeWorkInfo);
            response.addHeader("result", "success");
            return historyHomeWorkInfo;
        }
        response.addHeader("result", "fail");
        return null;
    }

    @RequestMapping("/getHomeWork")
    public String getHomeWork(HttpSession session, Model model, String courseName, String teacherName) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> homeWorkInfo = homeWorkService.getAllHomeWorkByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName);
            Utils.timeFormat(homeWorkInfo);
            model.addAttribute("HomeWorkInfos", homeWorkInfo);
        }
        return "student/innerHtml/homeWork";
    }

    @RequestMapping("/getHomeWorkAJAX")
    @ResponseBody
    public List<Map<String, Object>> getHomeWorkAJAX(HttpSession session, String courseName, String teacherName, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> homeWorkInfo = homeWorkService.getAllHomeWorkByUsernameAndCourseNameAndTeacherName(username, courseName, teacherName);
            Utils.timeFormat(homeWorkInfo);
            response.addHeader("result", "success");
            return homeWorkInfo;
        }
        response.addHeader("result", "fail");
        return null;
    }


    @RequestMapping("/viewHistoryHomeWork")
    public String viewHistoryHomeWork(HttpSession session, Model model, int homework_id) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            String homeworkScore = homeWork_scoreService.queryScoreByHomeWorkId(username, homework_id);
            List<HomeworkQuestion> homeworkQuestions = homeWorkService.getAllQuestionByHomeworkId(homework_id);
            model.addAttribute("HomeWorkQuestionInfos", homeworkQuestions);
            model.addAttribute("HomeWorkScore", homeworkScore);
        }
        return "student/innerHtml/historyHomeWorkView";
    }

    @RequestMapping("/finishHomeWork")
    public String finishHomeWork(HttpSession session, Model model, int homework_id) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<HomeworkQuestion> homeworkQuestions = homeWorkService.getAllQuestionByHomeworkId(homework_id);
            model.addAttribute("HomeWorkQuestionInfos", homeworkQuestions);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            model.addAttribute("submitTime", format.format(homeWorkService.selectByHomeworkId(homework_id).getSubmitTime()));
        }
        return "student/innerHtml/finishHomeWork";
    }

    @PostMapping("/submitHomeWork")
    @ResponseBody
    public String submitHomeWork(HttpSession session, String data, int homework_id, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("提交时间" + data);
            List<String> answer = Utils.mySplit(data);
            HomeworkScore homeworkScore = new HomeworkScore();
            homeworkScore.setHomeworkId(homework_id);
            homeworkScore.setSubmitTime(new Date());
            homeworkScore.setStudentId(studentService.queryByUsername(username).getStudentId());
            homeWork_scoreService.insert(homeworkScore);
            for (String str : answer) {
                JSONObject False = JSONObject.parseObject(str);
                System.out.println("第" + False.get("id") + "题选择的作答是" + False.get("answer"));
                HomeworkContent homeworkContent = new HomeworkContent();
                homeworkContent.setHomeworkId(homework_id);
                homeworkContent.setStudentId(studentService.queryByUsername(username).getStudentId());
                homeworkContent.setHomeworkQuestionId(Integer.valueOf(False.get("id").toString()));
                homeworkContent.setHomeworkContentAnswer(False.get("answer").toString());
                homeworkContentService.insert(homeworkContent);
                response.setHeader("result", "success");
            }
            return "success";
        }
        response.setHeader("result", "fail");
        return "error";
    }
}
