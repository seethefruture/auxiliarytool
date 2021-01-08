package com.cxxy.edu.controller;

import com.cxxy.edu.CustomException.InternalServerError;
import com.cxxy.edu.entity.Class;
import com.cxxy.edu.entity.Student;
import com.cxxy.edu.notifucationService.StudentNotificationService;
import com.cxxy.edu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@Controller
public class StudentMainInterfaceController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ClassService classService;
    @Autowired
    private StudentNotificationService studentNotificationService;


    @RequestMapping("/mainInterface")
    public String mainInterface(Model model, HttpSession session) {
        String username = session.getAttribute("username").toString();
        Student student = studentService.queryByUsername(username);
        Class myClass = classService.query(student.getClassId());
        model.addAttribute("class", myClass);
        model.addAttribute("student", student);
        return "student/mainInterface";
    }

    @RequestMapping("/exit")
    @ResponseBody
    public void exit(HttpSession session, HttpServletResponse response) {
        if (session.getAttribute("username") != null) {
            session.removeAttribute("username");
            response.setHeader("result", "success");
        } else {
            response.setHeader("result", "error");
        }
    }


    @RequestMapping("/changeConfig")
    public String changeConfig() {
        return "student/changeConfig";
    }

    @RequestMapping("/getHomePage")
    public String getHomePage(Model model, HttpSession session) throws IOException {
        String username = session.getAttribute("username").toString();
        Student student = studentService.queryByUsername(username);
        List<Map<String, Object>> courseInfo = studentService.getAllCourseByUsername(username);
        List<Map<String, Object>> otherCourseInfo = studentService.getOtherCourseByUsername(username);

        Calendar date = Calendar.getInstance();
        model.addAttribute("student", student);
        model.addAttribute("schoolYear", date.get(Calendar.YEAR) - classService.query(student.getClassId()).getClassGrade() + 1);
        model.addAttribute("term", (date.get(Calendar.MONTH) > 7) ? 1 : 2);
        model.addAttribute("courseInfos", courseInfo);
        model.addAttribute("otherCourseInfo", otherCourseInfo);
        return "student/innerHtml/homePage";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String a(String username) throws IOException , InternalServerError {
        System.out.println(username);
        studentNotificationService.sendNotificationByStudentUsername(username, StudentNotificationService.NotificationType.HOMEWORK_NOTIFICATION_TAG);
        return "success";
    }


}
