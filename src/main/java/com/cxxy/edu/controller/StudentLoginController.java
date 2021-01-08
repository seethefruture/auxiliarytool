package com.cxxy.edu.controller;

import com.cxxy.edu.CustomException.InternalServerError;
import com.cxxy.edu.entity.Class;
import com.cxxy.edu.entity.Student;
import com.cxxy.edu.service.ClassService;
import com.cxxy.edu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 * @title: LoginController
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1718:54
 */
@Controller
public class StudentLoginController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ClassService classService;

    @PostMapping(value = "/loginCheck", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void loginCheck(HttpSession session, String username, String password, HttpServletResponse response) throws IOException {
        Student student = studentService.queryByUsername(username);

        if (student == null) {
            response.setHeader("result", "non-existent");
            //账号不存在
        } else if (student.getStudentPassword().equals(password)) {
            session.setAttribute("username", username);
            //效验通过，重定向到学生首页
            response.setHeader("redirect", "/mainInterface");
        } else if (!student.getStudentPassword().equals(password)) {
            //密码不正确
            response.setHeader("result", "mismatch");
        }
    }


    @PostMapping(value = "/register/student", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void registerCheck(String username, String password, String no, String name, Integer myClass, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        if (!request.getMethod().equals("POST")) {
            throw new IllegalArgumentException("请求方法错误" + request.getMethod());
        }
        System.out.println("学生账户 " + username + " 请求注册" + myClass);
        if (studentService.queryByUsername(username) == null) {
            //未被注册，允许
            Student student = new Student();
            student.setStudentUsername(username);
            student.setStudentPassword(password);
            student.setStudentNo(no);
            student.setStudentName(name);
            student.setClassId(myClass);

            studentService.insert(student);
            response.addHeader("result", "success");
            session.setAttribute("username", username);
        } else {
            //已被注册
            response.addHeader("result", "occupied");
        }
    }

    @RequestMapping("/register")
    public String register(Model model) throws InternalServerError {
        List<Class> allClass = classService.queryAll();
        if (allClass.isEmpty()) {
            System.out.println("还没有添加班级");
            throw new InternalServerError("新用户注册时没有可选班级");
        } else {
            model.addAttribute("allClasses", allClass);
        }
        return "student/register";
    }
}
