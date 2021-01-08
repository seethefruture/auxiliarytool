package com.cxxy.edu.controller;

import com.cxxy.edu.entity.Teacher;
import com.cxxy.edu.entity.Test;
import com.cxxy.edu.entity.TestQuestion;
import com.cxxy.edu.service.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherTestController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TestQuestionService testQuestionService;
    @Autowired
    private TestService testService;
    @Autowired
    private Test_scoreService testScoreService;
    @Autowired
    private TeachService teachService;
    @Autowired
    private ChapterService chapterService;
//    //题库总页
//    @RequestMapping("/teacher/questionBank")
//    public String question(){
//         return "teacher/questionBank";
//    }

    //测试题库
    @RequestMapping("/teacher/test/questionBank")
    public String questionBank(HttpSession session, ModelMap model){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        List<Map<String,Object>> list = teacherService.selectAllCourseByTeacherId(id);
        model.addAttribute("course",list);
         return "teacher/test/questionBank";
    }
    //选择章节
    @RequestMapping("/teacher/test/questionBankTeachId")
    public String questionBankChapter(HttpSession session, HttpServletRequest request,ModelMap modelMap){
        int testCourseId = Integer.parseInt(request.getParameter("courseId"));
        session.setAttribute("testCourseId",testCourseId);
        List<Map<String,Object>> chapter = chapterService.selectByCourseId(testCourseId);
        modelMap.addAttribute("chapter",chapter);
         return "teacher/test/result";
    }

    //每个章节具体的题目
    @RequestMapping("/teacher/test/testQuestion")
    public String testQuestion(int chapter,HttpSession session){
        session.setAttribute("testQuestionChapter",chapter);
         return "teacher/test/testQuestion";
    }
    @RequestMapping("/teacher/test/testQuestionList")
    public void questionBankDetail(HttpSession session,ModelMap model,int pageNum){
        int id = (int) session.getAttribute("testCourseId");
        int chapter = (int) session.getAttribute("testQuestionChapter");
        PageInfo<TestQuestion> question = testQuestionService.selectAllByCourseIdAndChapter(id,chapter,pageNum,5);
        model.addAttribute("question",question);
    }

    //编辑题目
    //1.显示
    @RequestMapping("/teacher/test/edit")
    public String editQuestion(HttpServletRequest request,ModelMap model){
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        TestQuestion testQuestion = testQuestionService.selectAllByQuestionId(questionId);
        model.addAttribute("question",testQuestion);
         return "teacher/test/edit";
    }
    //2.编辑，上传
    @PostMapping("/teacher/test/doEdit/{id}")
    @ResponseBody
    public int doEditQuestion(@PathVariable Integer id,String detail,String questionA,String questionB,String questionC,String questionD,String type,String answer,HttpSession session){
        int sign;
        int type1 = Integer.parseInt(type);
            try{
                testQuestionService.update(detail,questionA,questionB,questionC,questionD,type,answer,id);
                sign=1;//成功
            }catch (Exception e){
                sign=0;//失败
            }
            return sign;
    }

    //增加题目
    //1.显示
    @RequestMapping("/teacher/test/insert")
    public String insertQuestion(){
         return "teacher/test/insert";
    }
    //2.增加
    @PostMapping("/teacher/test/doInsert")
    @ResponseBody
    public int doInsertQuestion(String detail,String questionA,String questionB,String questionC,String questionD,String type,String answer,HttpSession session){
        int sign;
        int type1 = Integer.parseInt(type);
        int courseId = (int) session.getAttribute("testCourseId");
        int chapter = (int) session.getAttribute("testQuestionChapter");
        TestQuestion testQuestion = new TestQuestion();
        if(courseId!=0&&chapter!=0){
            testQuestion.setCourseId(courseId);
            testQuestion.setTestQuestionDetail(detail);
            testQuestion.setTestQuestionA(questionA);
            testQuestion.setTestQuestionB(questionB);
            testQuestion.setTestQuestionC(questionC);
            testQuestion.setTestQuestionD(questionD);
            testQuestion.setTestQuestionChapter(chapter);
            testQuestion.setTestQuestionAnswer(answer);
            testQuestion.setTestQuestionType(type1);
            testQuestion.setTestQuestionCorrect(0.0f);
            testQuestion.setTestQuestionNum(0);
            try{
                testQuestionService.insert(testQuestion);
                sign=1;//成功
            }catch (Exception e){
                sign=0;//失败
            }
            return sign;
        }
        else{
            sign=0;//失败
            return sign;
        }
    }

    //删除题目
    @PostMapping("/teacher/test/delete")
    @ResponseBody
    public int deleteQuestion(Integer id){
        int sign;
        if(id!=null||id!=0) {
            testQuestionService.deleteByQuestionId(id);
            sign=1;
            return sign;
        }
        else{
            sign=0;
            return sign;
        }
    }

    //在线测试
    //显示所教授的课程
    @RequestMapping("/teacher/test/testBank")
    public String testBank(HttpSession session, ModelMap model){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        List<Map<String,Object>> list = teacherService.selectAllCourseByTeacherId(id);
        model.addAttribute("course",list);
         return "teacher/test/testBank";
    }
    @RequestMapping("/teacher/test/testOnline")
    public String testOnline(int courseId,HttpSession session){
        session.setAttribute("testOnlineCourseId",courseId);
         return "teacher/test/testOnline";
    }
    //测试列表
    @RequestMapping("/teacher/test/testOnlineList")
    public void testOnlineList(HttpSession session,ModelMap model,int pageNum,int sign){
        int courseId = (int) session.getAttribute("testOnlineCourseId");
        PageInfo<Map<String,Object>> list = testService.testOnlineList(courseId,pageNum,5,sign);
        model.addAttribute("list",list);
    }
    //每个测试的所有题目以及正确率
    @RequestMapping("/teacher/test/testCorrect")
    public String testQuestionList(HttpServletRequest request,ModelMap model){
        int testId = Integer.parseInt(request.getParameter("testId"));
        Test test = testService.selectByTestId(testId);
        String contain = test.getTestQuestionContain();
        if(contain=="0"){
             return "error";
        }
        else {
            String[] stringArr = contain.split(",");
            //将查询出来的所有在contains中的题目用list存储
            List<TestQuestion> list = new ArrayList<>();
            for (int i = 0; i < stringArr.length; i++) {
                TestQuestion testQuestion = testQuestionService.selectAllByQuestionId(Integer.valueOf(stringArr[i]));
                list.add(testQuestion);
            }
            model.addAttribute("question", list);
             return "teacher/test/testCorrect";
        }
    }
    //查看该test中所有学生的成绩
    @RequestMapping("/teacher/test/studentScore")
    public String testStudentScore(HttpServletRequest request,ModelMap model){
        int testId = Integer.parseInt(request.getParameter("testId"));
        List<Map<String,Object>> list = testScoreService.studentScore(testId);
        model.addAttribute("list",list);
         return "teacher/test/studentScore";
    }

    //增加测试
    @RequestMapping("/teacher/test/addTest")
    public String addTest(ModelMap model,HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        List<Map<String,Object>> list = teacherService.selectByTeachId(id);
        List<Map<String,Object>> chapterList = chapterService.selectByTeacherId(id);
        model.addAttribute("course",list);
        model.addAttribute("chapter",chapterList);
         return "teacher/test/addTest";
    }

    //添加题目
    @RequestMapping("/teacher/test/addTestQuestion")
    public String addTestQuestion(int courseId,int chapter,ModelMap model,HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int teacherId = teacher.getTeacherId();
        session.setAttribute("courseId",courseId);
        session.setAttribute("testChapter",chapter);
         return "teacher/test/addTestQuestion";
    }
    @RequestMapping("/teacher/test/addTestQuestionList")
    public void addTestQuestionList(ModelMap model,HttpSession session,int pageNum){
        int courseId = (int) session.getAttribute("courseId");
        int chapter = (int) session.getAttribute("testChapter");
        PageInfo<TestQuestion> question = testQuestionService.selectAllByCourseIdAndChapter(courseId,chapter,pageNum,5);
        model.addAttribute("question",question);
    }
    //选择班级和其他各项
    @RequestMapping("/teacher/test/doAddTest")
    public String doAddTest(String[] question,HttpSession session,ModelMap model){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int teacherId = teacher.getTeacherId();
        int id = (int) session.getAttribute("courseId");
        session.setAttribute("questionList",question);
        List<Map<String,Object>> course = teachService.selectClassInformation(id,teacherId);
        model.addAttribute("course",course);
         return "teacher/test/doAddTest";
    }

    //执行插入操作
    @PostMapping("/teacher/test/insert")
    @ResponseBody
    public int insertTest(String start,String end,String type,int classId,HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int teacherId = teacher.getTeacherId();
        int courseId = (int) session.getAttribute("courseId");
        int sign;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = simpleDateFormat.parse(start);
            endTime=simpleDateFormat.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int testNum = Integer.parseInt(type);
        String[] questionContain = (String[]) session.getAttribute("questionList");
        String testQuestionContain= StringUtils.join(questionContain,",");
        //Teach teach = teachService.selectTeachId(courseId,teacherId,classId);
        int teachId = teachService.selectTeachId(courseId, classId);
        Test test = new Test();
        test.setSubmitTime(endTime);
        test.setTeachId(teachId);
        test.setTestNum(testNum);
        test.setTestQuestionContain(testQuestionContain);
        test.setTestTime(startTime);
        try {
            testService.insertTest(test);
            sign = 1;
            return sign;
        }catch(Exception ex){
            sign=0;
            return sign;
        }
    }

    //删除测试
    @RequestMapping("/teacher/test/delete")
    @ResponseBody
    public int delete(int testId){
        int sign;
        try{
            testService.deleteByTestId(testId);
            sign=1;
            return sign;
        }catch (Exception ex){
            sign = 0;
            return sign;
        }
    }



}
