package com.cxxy.edu.controller;

import com.cxxy.edu.entity.Class;
import com.cxxy.edu.entity.*;
import com.cxxy.edu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeachService teachService;
    @Autowired
    private ClassService classService;

    @RequestMapping("/admin/doLogin")
    @ResponseBody
    public String adminDoLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        Admin admin = administratorService.selectByUsernameAndPassword(username, password);
        //错误信息，登陆失败
        String msg = "";
        if (admin != null) {
            //主管理员
            if (username.equals("0")) {
                msg = "0";
                session.setAttribute("admin", admin);
            }
            //各级管理员
            else {
                msg = "1";
                session.setAttribute("gradeAdmin", admin);
            }

            return msg;
        } else {
            msg = "登陆失败！";
            return msg;
        }
    }

    //主页面
    @RequestMapping("/admin/home")
    public String welcome() {
        return "admin/home";
    }

    @RequestMapping("/admin/homepage")
    public String homepage() {
        return "admin/homepage";
    }

    @RequestMapping("/admin/mainInterface")
    public String mainInterface(ModelMap model, HttpSession session) {
        Admin Admin = (Admin) session.getAttribute("admin");
        model.addAttribute("admin", Admin);
        return "admin/mainInterface";
    }

    //退出
    @RequestMapping("/admin/exit")
    @ResponseBody
    public int exit(HttpSession session) {
        int sign;
        if (session.getAttribute("admin") != null) {
            session.removeAttribute("admin");
            sign = 1;
        } else {
            sign = 0;
        }
        return sign;
    }

    /*
    各级管理员
     */
    //显示
    @RequestMapping("/admin/gradeAdmin/gradeAdmin")
    public String gradingAdmin(ModelMap model) {
        List<Map<String, Object>> list = administratorService.selectAdminAndCollege();
        model.addAttribute("list", list);
        return "admin/gradeAdmin/gradeAdmin";
    }

    //删除
    @RequestMapping("/admin/gradeAdmin/deleteGradingAdmin")
    @ResponseBody
    public int deleteGradingAdmin(int adminId) {
        int sign;
        try {
            administratorService.deleteByAdminId(adminId);
            sign = 1;
            return sign;
        } catch (Exception ex) {
            sign = 0;
            return sign;
        }

    }

    //添加
    @RequestMapping("/admin/gradeAdmin/insert")
    public String insertGradingAdmin(ModelMap model) {
        List<College> list = collegeService.selectAll();
        model.addAttribute("list", list);
        return "admin/gradeAdmin/insert";
    }

    @RequestMapping("/admin/gradeAdmin/doInsert")
    @ResponseBody
    public int doInsertGradingAdmin(String name, int collegeId) {
        int sign;
        if (name == "" || collegeId == 0) {
            sign = 0;
            return sign;
        }
        try {
            Admin admin = new Admin();
            admin.setAdminLevel(1);
            admin.setAdminName(name);
            admin.setAdminPassword("111111");
            admin.setAdminUsername(name);
            admin.setCollegeId(collegeId);
            administratorService.insert(admin);
            sign = 1;
        } catch (Exception ex) {
            sign = 0;
        }
        return sign;
    }

    //编辑，只能修改院系
    @RequestMapping("/admin/gradeAdmin/edit")
    public String editGradingAdmin(ModelMap model, HttpServletRequest request, HttpSession session) {
        int adminId = Integer.parseInt(request.getParameter("adminId"));
        session.setAttribute("adminId", adminId);
        Map<String, Object> map = administratorService.selectAdminAndCollegeByAdminId(adminId);
        List<College> list = collegeService.selectAll();
        model.addAttribute("list", list);
        model.addAttribute("map", map);
        return "admin/gradeAdmin/edit";
    }

    @RequestMapping("/admin/gradeAdmin/doEdit")
    @ResponseBody
    public int doEditGradingAdmin(int collegeId, HttpSession session) {
        int sign;
        int adminId = (int) session.getAttribute("adminId");
        Admin admin = administratorService.selectByAdminId(adminId);
        admin.setCollegeId(collegeId);
        try {
            administratorService.updateByAdminId(admin);
            sign = 1;
        } catch (Exception ex) {
            sign = 0;
        }
        return sign;
    }

    /*
    管理各院
     */
    //显示
    @RequestMapping("/admin/college/show")
    public String college(ModelMap model) {
        List<College> list = collegeService.selectAll();
        model.addAttribute("list", list);
        return "admin/college/show";
    }

    //添加
    @RequestMapping("/admin/college/insert")
    public String insertCollege() {
        return "admin/college/insert";
    }

    @RequestMapping("/admin/college/doInsert")
    @ResponseBody
    public int doInsertCollege(String collegeName) {
        int sign;
        try {
            College college = new College();
            college.setCollegeName(collegeName);
            collegeService.insert(college);
            sign = 1;
        } catch (Exception ex) {
            sign = 0;
        }
        return sign;
    }

    //编辑
    @RequestMapping("/admin/college/edit")
    public String editCollege(HttpServletRequest request, ModelMap model) {
        int collegeId = Integer.parseInt(request.getParameter("id"));
        College college = collegeService.queryById(collegeId);
        model.addAttribute("college", college);
        return "admin/college/edit";
    }

    @RequestMapping("/admin/college/doEdit")
    @ResponseBody
    public int doEditCollege(String collegeName, int collegeId) {
        int sign;
        try {
            collegeService.update(collegeId, collegeName);
            sign = 1;
        } catch (Exception ex) {
            sign = 0;
        }
        return sign;
    }

    //删除
    @RequestMapping("/admin/college/deleteCollege")
    @ResponseBody
    public int deleteCollege(int collegeId) {
        int sign;
        try {
            collegeService.deleteById(collegeId);
            sign = 1;
        } catch (Exception ex) {
            sign = 0;
        }
        return sign;

    }

    /*
    教师
     */
    //显示
    @RequestMapping("/admin/teacher/show")
    public String showTeacher() {
        return "admin/teacher/show";
    }

    @RequestMapping("/admin/teacher/showList")
    //@ResponseBody
    public void showTeacherList(String name, ModelMap model,int pageNum) {
        if (name.equals("null")) {
            PageInfo<Teacher> list = teacherService.selectAll(pageNum,2);
            model.addAttribute("list", list);
        }
    }
    @RequestMapping("/admin/teacher/showListByName")
    //@ResponseBody
    public String showTeacherListByName(String name, ModelMap model) {
        List<Teacher> list = teacherService.selectByTeacherName(name);
        model.addAttribute("list", list);
        return "admin/teacher/showListByName";
    }

    //添加
    @RequestMapping("/admin/teacher/insert")
    public String insertTeacher() {
        return "admin/teacher/insert";
    }

    @RequestMapping("/admin/teacher/doInsert")
    @ResponseBody
    public int doInsertTeacher(String name) {
        int sign;
        if (name != "") {
            Teacher teacher = new Teacher();
            teacher.setTeacherName(name);
            teacher.setTeacherUsername(name);
            teacher.setTeacherPassword("111111");
            try {
                teacherService.insertTeacher(teacher);
                sign = 1;
            } catch (Exception ex) {
                sign = 0;
            }
        } else {
            sign = 0;
        }
        return sign;
    }

    //删除
    @RequestMapping("/admin/teacher/delete")
    @ResponseBody
    public int deleteTeacher(int teacherId) {
        int sign;
        try {
            teacherService.deleteByTeacherId(teacherId);
            sign = 1;
        } catch (Exception ex) {
            sign = 0;
        }
        return sign;
    }

    //编辑
    @RequestMapping("/admin/teacher/edit")
    public String edit(HttpServletRequest request, ModelMap model) {
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        Teacher teacher = teacherService.selectByTeacherId(teacherId);
        model.addAttribute("teacher", teacher);
        return "admin/teacher/edit";
    }

    @RequestMapping("/admin/teacher/doEdit")
    @ResponseBody
    public int doEditTeacher(String username, String name, int teacherId) {
        int sign;
        try {
            teacherService.updateByTeacherId(name, username, teacherId);
            sign = 1;
        } catch (Exception ex) {
            sign = 0;
        }
        return sign;
    }



    /*
    修改个人信息
     */
    @RequestMapping("/admin/modify")
    public String modify(ModelMap model, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "admin/modify";
    }

    @RequestMapping("/admin/doModify")
    @ResponseBody
    public int modifyTeacher(String userName, String password, String name, int id, HttpSession session) {
        int sign;
        if (userName == "" || password == "" || name == "") {
            sign = 0;
        } else {
            try {
                administratorService.update(userName, password, name, id);
                sign = 1;
            } catch (Exception ex) {
                sign = 0;
            }
        }

        return sign;
    }


}
