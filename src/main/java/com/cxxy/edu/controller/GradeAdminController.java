package com.cxxy.edu.controller;

import com.cxxy.edu.entity.*;
import com.cxxy.edu.entity.Class;
import com.cxxy.edu.service.*;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GradeAdminController {
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private ClassService classService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeachService teachService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    @RequestMapping("/gradeAdmin/home")
    public String home(){
        return "gradeAdmin/home";
    }

    @RequestMapping("/gradeAdmin/homepage")
    public String homepage() {
        return "gradeAdmin/homepage";
    }

    @RequestMapping("/gradeAdmin/mainInterface")
    public String mainInterface(ModelMap model,HttpSession session) {
        Admin Admin = (Admin) session.getAttribute("gradeAdmin");
        model.addAttribute("admin",Admin);
        return "gradeAdmin/mainInterface";
    }

    //退出
    @RequestMapping("/gradeAdmin/exit")
    @ResponseBody
    public int exit(HttpSession session){
        int sign;
        if(session.getAttribute("gradeAdmin")!=null) {
            session.removeAttribute("admin");
            sign=1;
        }
        else {
            sign=0;
        }
        return sign;
    }

    /*
    系
     */
    @RequestMapping("/gradeAdmin/faculty/show")
    public String showFaculty(HttpSession session, ModelMap model){
        Admin admin = (Admin) session.getAttribute("gradeAdmin");
        int collegeId = admin.getCollegeId();
        List<Faculty> list = facultyService.selectByCollegeId(collegeId);
        model.addAttribute("list",list);
        return "gradeAdmin/faculty/show";
    }
    //删除
    @RequestMapping("/gradeAdmin/faculty/delete")
    @ResponseBody
    public int deleteFaculty(int facultyId){
        int sign;
        try{
            facultyService.deleteById(facultyId);
            sign=1;
        }catch (Exception ex){
            sign=0;
        }
        return sign;
    }

    //编辑
    @RequestMapping("/gradeAdmin/faculty/edit")
    public String edit(HttpServletRequest request, ModelMap model){
        int facultyId = Integer.parseInt(request.getParameter("facultyId"));
        Faculty faculty = facultyService.queryById(facultyId);
        model.addAttribute("faculty",faculty);
        return "gradeAdmin/faculty/edit";
    }

    @RequestMapping("/gradeAdmin/faculty/doEdit")
    @ResponseBody
    public int doEditFaculty(String name,int id){
        int sign;
        try{
            facultyService.update(name,id);
            sign=1;
        }catch (Exception ex){
            sign=0;
        }
        return sign;
    }

    //添加
    @RequestMapping("/gradeAdmin/faculty/insert")
    public String insertTeacher(){
        return "gradeAdmin/faculty/insert";
    }

    @RequestMapping("/gradeAdmin/faculty/doInsert")
    @ResponseBody
    public int doInsertFaculty(String name,HttpSession session){
        Admin admin = (Admin) session.getAttribute("gradeAdmin");
        int collegeId = admin.getCollegeId();
        int sign;
        if(name!="") {
            Faculty faculty = new Faculty();
            faculty.setCollegeId(collegeId);
            faculty.setFacultyName(name);
            try {
                facultyService.insert(faculty);
                sign = 1;
            } catch (Exception ex) {
                sign = 0;
            }
        }
        else{
            sign=0;
        }
        return sign;
    }

    /*
    班级
     */
    @RequestMapping("/gradeAdmin/class/show")
    public String showClass(){
        return "gradeAdmin/class/show";
    }

    @RequestMapping("/gradeAdmin/class/showList")
    public void showClassList(HttpSession session,ModelMap model,int pageNum){
        Admin admin = (Admin) session.getAttribute("gradeAdmin");
        int collegeId = admin.getCollegeId();
        PageInfo<Map<String,Object>> list = classService.selectByCollegeId(collegeId,pageNum,2);
        model.addAttribute("list",list);
    }

    //编辑
    @RequestMapping("/gradeAdmin/class/edit")
    public String editClass(HttpServletRequest request,HttpSession session,ModelMap model){
        int classId  = Integer.parseInt(request.getParameter("classId"));
        Class aClass = classService.queryClass2(classId);
        Admin admin = (Admin) session.getAttribute("gradeAdmin");
        int collegeId = admin.getCollegeId();
        List<Faculty> facultyList = facultyService.selectByCollegeId(collegeId);
        model.addAttribute("list",facultyList);
        model.addAttribute("class",aClass);
        return "gradeAdmin/class/edit";
    }

    @RequestMapping("/gradeAdmin/class/doEdit")
    @ResponseBody
    public int doEditClass(String name,Integer facultyId,Integer grade,Integer num,Integer classId){
        int sign;
        if(name!=""&&facultyId!=0&&grade!=0&&num!=0&&grade.toString().length()==4&&num.toString().length()==1) {
            try {
                classService.update(name, facultyId, grade, num, classId);
                sign = 1;
            } catch (Exception ex) {
                sign = 0;
            }
        }
        else sign=0;
        return sign;
    }

    //添加
    @RequestMapping("/gradeAdmin/class/insert")
    public String insertClass(HttpSession session,ModelMap model){
        Admin admin = (Admin) session.getAttribute("gradeAdmin");
        int collegeId = admin.getCollegeId();
        List<Faculty> facultyList = facultyService.selectByCollegeId(collegeId);
        model.addAttribute("list",facultyList);
        return "gradeAdmin/class/insert";
    }

    @RequestMapping("/gradeAdmin/class/doInsert")
    @ResponseBody
    public int doInsertClass(String name,Integer facultyId,Integer grade,Integer num,HttpSession session){
        int sign;
        if(name!=""&&facultyId!=0&&grade!=0&&num!=0&&grade.toString().length()==4&&num.toString().length()==1) {
            Class aClass = new Class();
            aClass.setClassGrade(grade);
            aClass.setClassName(name);
            aClass.setClassNumber(num);
            aClass.setFacultyId(facultyId);
            try {
                classService.insert(aClass);
                sign = 1;
            } catch (Exception ex) {
                sign = 0;
            }
        }
        else{
            sign=0;
        }
        return sign;
    }
    //展示该班级的所有学生
    @RequestMapping("/gradeAdmin/class/showStudent")
    public String showStudent(HttpServletRequest request,HttpSession session){
        int classId = Integer.parseInt(request.getParameter("classId"));
        session.setAttribute("studentClassId",classId);
        return "gradeAdmin/class/showStudent";
    }
    @RequestMapping("/gradeAdmin/class/showStudentList")
    public void showStudentList(ModelMap modelMap,int pageNum,HttpSession session){
        int classId = (int) session.getAttribute("studentClassId");
        PageInfo<Student> list = studentService.selectStudentByClassId(classId,pageNum,2);
        modelMap.addAttribute("student",list);
    }

    /*
    课程
     */
    //展示
    @RequestMapping("/gradeAdmin/course/show")
    public String showCourse(ModelMap model){
        return "gradeAdmin/course/show";
    }
    @RequestMapping("/gradeAdmin/course/doShow")
    public void doShowCourse(ModelMap model,int pageNum){
        PageInfo<Map<String,Object>> list = courseService.selectAll(pageNum,2);
        model.addAttribute("list",list);
//        else{
//            list = courseService.selectByCourseNameORTeacherName(teacherName,courseName);
//        }
    }
    @RequestMapping("/gradeAdmin/course/doShowByName")
    public void doShowByName(ModelMap model,String teacherName,String courseName){
        if(teacherName!=""||courseName!="") {
            List<Map<String, Object>> list = courseService.selectByCourseNameORTeacherName(teacherName, courseName);
            model.addAttribute("list", list);
        }
    }
    //添加
    @RequestMapping("/gradeAdmin/course/insert")
    public String insertCourse(){

        return "gradeAdmin/course/insert";
    }

    @RequestMapping("/gradeAdmin/course/doInsert")
    @ResponseBody
    public int doInsertCourse(String courseName,String teacherName){
        int sign;
        try{
            sign = courseService.insertCourse(courseName,teacherName);
        }catch (SQLException ex){
            sign = 5;
        }
        return sign;
    }

    @RequestMapping("/gradeAdmin/course/insertByUsername")
    public String insertCourseByUsername(){
        return "gradeAdmin/course/insertByUsername";
    }
    //用户名插入
    @RequestMapping("/gradeAdmin/course/doInsertByUsername")
    @ResponseBody
    public int doInsertCourseByUsername(String courseName,String teacherUsername){
        int sign;
        try{
            sign = courseService.insertByUsername(courseName,teacherUsername);
        }catch (SQLException ex){
            sign = 5;
        }
        return sign;
    }



    //编辑
    @RequestMapping("/gradeAdmin/course/edit")
    public String editCourse(HttpServletRequest request,ModelMap model){
        Integer courseId = Integer.parseInt(request.getParameter("id"));
        //Course course = courseService.queryById(courseId);
        Map<String,Object> map = courseService.selectByCourseId(courseId);
        model.addAttribute("course",map);
        return "gradeAdmin/course/edit";
    }
    @RequestMapping("/gradeAdmin/course/doEdit")
    @ResponseBody
    public int doEditCourse(String courseName,int courseId,String teacherName){
        int sign;
        try{
            sign = courseService.update(courseId,courseName,teacherName);
        }catch (Exception ex){
            sign=0;
        }
        return sign;
    }

    //删除
    @RequestMapping("/gradeAdmin/course/deleteCourse")
    @ResponseBody
    public int deleteCourse(int courseId){
        int sign;
        try{
            courseService.deleteById(courseId);
            sign=1;
        }catch (Exception ex){
            sign=0;
        }
        return sign;

    }

    /*
    任课信息
     */
    @RequestMapping("/gradeAdmin/teach/show")
    public String show() {
        return "gradeAdmin/teach/show";
    }

    @RequestMapping("/gradeAdmin/teach/showList")
    public void showTeachList(String courseName, String className, ModelMap model,int pageNum) {
        PageInfo<Map<String,Object>> list = teachService.selectTeachMessage(courseName, className,pageNum,2);
        model.addAttribute("list", list);
    }

    //添加
    //添加第一项，课程和教师
    @RequestMapping("/gradeAdmin/teach/insert1")
    public String insert1Teach() {
        return "gradeAdmin/teach/insert1";
    }

    //添加第2项，班级
    @RequestMapping("/gradeAdmin/teach/insert2")
    public String insert2Teach() {
        return "gradeAdmin/teach/insert2";
    }

    @RequestMapping("/gradeAdmin/teach/teachCourseList")
    public void insertTeachCourseList(String courseName,String teacherName, ModelMap model) {
        List<Map<String, Object>> courseList = courseService.selectByCourseNameORTeacherName(teacherName,courseName);;
        model.addAttribute("courseList",courseList);
    }

    @RequestMapping("/gradeAdmin/teach/teachClassList")
    public void insertClassList(String className, ModelMap model) {
        List<Class> classList = classService.selectClass(className);
        model.addAttribute("classList", classList);
    }

    //保存选择项
    @RequestMapping("/gradeAdmin/teach/saveChoice")
    @ResponseBody
    public int saveChoice(int courseId, HttpSession session) {
        if (courseId != 0) {
            session.setAttribute("AdminCourseId", courseId);
        }
        int sign = 1;
        return sign;
    }

    @RequestMapping("/gradeAdmin/teach/doInsert")
    @ResponseBody
    public int doInsert(int classId,String teachTime, HttpSession session) {
        int sign;
        if (classId == 0) {
            sign = 0;
            return sign;
        }
        int courseId = (int) session.getAttribute("AdminCourseId");
        Teach teach = new Teach();
        teach.setClassId(classId);
        teach.setCourseId(courseId);
        teach.setTeachTime(teachTime);
        try {
            teachService.insert(teach);
            sign = 1;
        } catch (Exception ex) {
            sign = 0;
        }
        return sign;
    }

    //编辑
    @RequestMapping("/gradeAdmin/teach/edit")
    public void editTeach(HttpServletRequest request,ModelMap modelMap){
        int teachId  = Integer.parseInt(request.getParameter("teachId"));
        Map<String,Object> map = teachService.selectByTeachId(teachId);
        modelMap.addAttribute("teach",map);
    }

    @RequestMapping("/gradeAdmin/teach/doEdit")
    @ResponseBody
    public int doEditTeach(String courseName,String className,String teacherName,String teachTime,int teachId,String classNumber,String classGrade) throws SQLException {
        if(classGrade.length()==4&&NumberUtils.isNumber(classGrade)&&NumberUtils.isNumber(classNumber)){
            int grade = Integer.parseInt(classGrade);
            int number = Integer.parseInt(classNumber);
            Integer classId = classService.selectClass1(className,number,grade);
            if(classId==null){
                return 2; //找不到该班级
            }
            else {
                int sign = courseService.judgeRepeat(courseName,teacherName,0,0);
                if(sign==1){
                    return 3;//找不到该老师
                }
                if(sign==2){
                    return 5;//有重名
                }
                if(sign==4){
                    return 4; //没有该课程
                }
                if(sign==3){
                    int courseId = courseService.selectByCourseNameAndTeacherName(courseName, teacherName);
                    if(teachTime.contains(",")&&teachTime.length()==3){
                        teachService.update(teachId,classId,courseId,teachTime);
                        return 7; //成功
                    }
                    else {
                        return 6; //学期填写格式错误
                    }
                }
            }

        }
        else {
            return 1;//输入不合法
        }
        return 5;

    }

    //删除
    @RequestMapping("/gradeAdmin/teach/delete")
    @ResponseBody
    public int deleteTeach(int teachId) {
        int sign;
        try {
            teachService.delete(teachId);
            sign = 1;
        } catch (Exception ex) {
            sign = 0;
        }
        return sign;

    }



}
