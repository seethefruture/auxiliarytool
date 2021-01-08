package com.cxxy.edu.controller;

import com.cxxy.edu.entity.*;
import com.cxxy.edu.service.*;
import com.github.pagehelper.Page;
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
import java.util.*;

@Controller
public class TeacherHomeworkController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private HomeworkQuestionService homeworkQuestionService;
    @Autowired
    private HomeWorkService homeworkService;
    @Autowired
    private TeachService teachService;
    @Autowired
    private HomeWork_ScoreService homeworkScoreService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private HomeworkContentService homeworkContentService;
    @Autowired
    private ChapterService chapterService;

    //作业题库
    @RequestMapping("/teacher/homework/questionBank")
    public String questionBank(HttpSession session, ModelMap model){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        List<Map<String,Object>> list = teacherService.selectAllCourseByTeacherId(id);
        model.addAttribute("course",list);
         return "teacher/homework/questionBank";
    }
    //选择章节
    @RequestMapping("/teacher/homework/questionBankTeachId")
    public String questionBankChapter(HttpSession session, HttpServletRequest request,ModelMap modelMap){
        int homeworkCourseId = Integer.parseInt(request.getParameter("courseId"));
        session.setAttribute("HomeworkCourseId",homeworkCourseId);
        List<Map<String,Object>> chapter = chapterService.selectByCourseId(homeworkCourseId);
        modelMap.addAttribute("chapter",chapter);
         return "teacher/homework/result";
    }

    //每个章节具体的题目
    @RequestMapping("/teacher/homework/homeworkQuestion")
    public String homeworkQuestion(int chapter, HttpSession session){
        int id = (int) session.getAttribute("HomeworkCourseId");
        session.setAttribute("HomeworkChapter",chapter);
         return "teacher/homework/homeworkQuestion";
    }
    @RequestMapping("/teacher/homework/homeworkQuestionList")
    public void questionBankDetail(ModelMap model,HttpSession session,int pageNum){
        int chapter = (int) session.getAttribute("HomeworkChapter");
        int id = (int) session.getAttribute("HomeworkCourseId");
        PageInfo<HomeworkQuestion> question = homeworkQuestionService.selectAllByCourseIdAndChapter(id,chapter,pageNum,5);
        model.addAttribute("question",question);
    }

    //编辑题目
    //1.显示
    @RequestMapping("/teacher/homework/edit")
    public String editQuestion(HttpServletRequest request,ModelMap model){
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        HomeworkQuestion homeworkQuestion = homeworkQuestionService.selectAllByQuestionId(questionId);
        model.addAttribute("question",homeworkQuestion);
         return "teacher/homework/edit";
    }
    //2.编辑，上传
    @PostMapping("/teacher/homework/doEdit/{id}")
    @ResponseBody
    public int doEditQuestion(@PathVariable Integer id, String detail,String answer, HttpSession session){
        int sign;
        try{
            homeworkQuestionService.update(detail,answer,id);
            sign=1;//成功
        }catch (Exception e){
            sign=0;//失败
        }
        return sign;
    }

    //增加题目
    //1.显示
    @RequestMapping("/teacher/homework/insert")
    public String insertQuestion(){
         return "teacher/homework/insert";
    }
    //2.增加
    @PostMapping("/teacher/homework/doInsert")
    @ResponseBody
    public int doInsertQuestion(String detail,String answer,HttpSession session){
        int sign;
        int courseId = (int) session.getAttribute("HomeworkCourseId");
        int chapter = (int) session.getAttribute("HomeworkChapter");
        HomeworkQuestion homeworkQuestion = new HomeworkQuestion();
        if(courseId!=0&&chapter!=0){
            homeworkQuestion.setCourseId(courseId);
            homeworkQuestion.setHomeworkQuestionDetail(detail);
            homeworkQuestion.setHomeworkQuestionChapter(chapter);
            homeworkQuestion.setHomeworkQuestionAnswer(answer);
            homeworkQuestion.setHomeworkQuestionCorrect(0.0f);
            homeworkQuestion.setHomeworkQuestionNum(0);
            try{
                homeworkQuestionService.insert(homeworkQuestion);
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
    @PostMapping("/teacher/homework/delete")
    @ResponseBody
    public int deleteQuestion(Integer id){
        int sign;
        if(id!=0) {
            homeworkQuestionService.deleteByQuestionId(id);
            sign=1;
        }
        else{
            sign=0;
        }
        return sign;
    }

    //布置作业
    //主页
    //显示课程
    @RequestMapping("/teacher/homework/homeworkBank")
    public String homeworkBank(HttpSession session, ModelMap model){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        List<Map<String,Object>> list = teacherService.selectAllCourseByTeacherId(id);
        model.addAttribute("course",list);
         return "teacher/homework/homeworkBank";
    }

    @RequestMapping("/teacher/homework/homeworkOnline")
    public String homeworkOnline(int courseId,HttpSession session){
        session.setAttribute("homeworkOnlineCourseId",courseId);
         return "teacher/homework/homeworkOnline";
    }
    //测试列表
    @RequestMapping("/teacher/homework/homeworkOnlineList")
    public void homeworkOnlineList(HttpSession session,ModelMap model,int pageNum,int sign){
        int id = (int) session.getAttribute("homeworkOnlineCourseId");
        PageInfo<Map<String,Object>> list = homeworkService.homeworkOnlineList(id,pageNum,5,sign);
        model.addAttribute("list",list);
    }

    //增加作业
    //第一项：选择课程
    @RequestMapping("/teacher/homework/addHomework")
    public String addHomework(ModelMap model,HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        List<Map<String,Object>> list = teacherService.selectAllCourseByTeacherId(id);
        model.addAttribute("course",list);
         return "teacher/homework/addHomework";
    }

    //第二项：选择班级和日期
    @RequestMapping("/teacher/homework/addHomeworkClassAndDate")
    public void doAddHomework(int courseId,ModelMap model,HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int teacherId = teacher.getTeacherId();
        session.setAttribute("courseId",courseId);
        List<Map<String,Object>> class1 = teachService.selectClassInformation(courseId,teacherId);
        //model.addAttribute("question",question);
        model.addAttribute("class1",class1);
    }
    //第三项：选择题库中的题目
    @RequestMapping("/teacher/homework/addHomeworkQuestion")
    public String addHomeworkQuestion(int classId,String start,HttpSession session,ModelMap modelMap){
        session.setAttribute("homeworkClassId",classId);
        session.setAttribute("start",start);
        int courseId = (int) session.getAttribute("courseId");
        List<Map<String,Object>> chapter = chapterService.selectByCourseId(courseId);
        modelMap.addAttribute("chapter",chapter);
         return "teacher/homework/addHomeworkQuestion";
    }

    @RequestMapping("/teacher/homework/addHomeworkQuestionList")
    public void addHomeworkQuestionList(int chapter,int pageNum,HttpSession session,ModelMap modelMap){
        int courseId = (int) session.getAttribute("courseId");
        session.setAttribute("homeworkChapter",chapter);
        PageInfo<HomeworkQuestion> question = homeworkQuestionService.selectAllByCourseIdAndChapter(courseId,chapter,pageNum,5);
        modelMap.addAttribute("question",question);
    }

    //执行插入操作
    @PostMapping("/teacher/homework/insert")
    @ResponseBody
    public int insertTest(String[] question,HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int teacherId = teacher.getTeacherId();
        int courseId = (int) session.getAttribute("courseId");
        int classId = (int) session.getAttribute("homeworkClassId");
        int chapter = (int) session.getAttribute("homeworkChapter");
        String start = (String) session.getAttribute("start");
        int sign;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        try {
            startTime = simpleDateFormat.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String testQuestionContain= StringUtils.join(question,",");
        //Teach teach = teachService.selectTeachId(courseId,teacherId,classId);
        int teachId = teachService.selectTeachId(courseId, classId);
        Homework homework = new Homework();
        homework.setTeachId(teachId);
        homework.setContainQuestionId(testQuestionContain);
        homework.setSubmitTime(startTime);
        homework.setHomeworkChapter(chapter);
        try {
            homeworkService.insert(homework);
            sign = 1;
            return sign;
        }catch(Exception ex){
            sign=0;
            return sign;
        }
    }

    //删除作业
    @RequestMapping("/teacher/homework/deleteOnline")
    @ResponseBody
    public int delete(String homeworkId){
        int id = Integer.parseInt(homeworkId);
        int sign;
        try{
            homeworkService.deleteByHomeworkId(id);
            sign=1;
            return sign;
        }catch (Exception ex){
            sign = 0;
            return sign;
        }
    }

    //批改作业
    //显示该作业所有学生的批改状况
    @RequestMapping("/teacher/homework/correctHomework")
    public String correctHomework(int homeworkId,HttpSession session){
        session.setAttribute("correctHomeworkId",homeworkId);
         return "teacher/homework/correctHomework";
    }
    @RequestMapping("/teacher/homework/correctHomeworkList")
    public void correctHomeworkList(HttpSession session, ModelMap model,int pageNum){
        int homeworkId = (int) session.getAttribute("correctHomeworkId");
        PageInfo<Map<String,Object>> homework = homeworkScoreService.selectByQuestionId(homeworkId,pageNum,1);
        model.addAttribute("list",homework);
    }
    //批改作业加显示批改过的作业
    @RequestMapping("/teacher/homework/doCorrectHomework")
    public String doCorrectHomework(ModelMap model,HttpServletRequest request){
        int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String score = request.getParameter("score");
        List<Map<String,Object>> homework = homeworkContentService.selectHomeworkContent(studentId,homeworkId);
        HomeworkScore homeworkScore = homeworkScoreService.selectByHomeworkIdAndStudentId(homeworkId,studentId);
        model.addAttribute("homeworkScore",homeworkScore);
        model.addAttribute("homework",homework);
        model.addAttribute("score",score);
         return "teacher/homework/doCorrectHomework";
    }

    @RequestMapping("/teacher/homework/update")
    @ResponseBody
    public int updateScore(int questionScoreId,String score){
        int sign;
        try {
            homeworkScoreService.updateByHomeworkScoreId(questionScoreId, score);
            sign=1;
            return sign;
        }catch(Exception ex){
            sign=0;
            return sign;
        }

    }


}
