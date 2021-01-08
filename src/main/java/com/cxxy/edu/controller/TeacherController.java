package com.cxxy.edu.controller;

import com.cxxy.edu.config.FileManager;
import com.cxxy.edu.entity.FileStoreInfo;
import com.cxxy.edu.entity.Resource;
import com.cxxy.edu.entity.Teacher;
import com.cxxy.edu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private TeachService teachService;
    @Autowired
    private ThreadExecute threadExecute;
    @Autowired
    private CourseService courseService;

    @Autowired
    private FileManager fileManager;

    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/teacher/doLogin")
    @ResponseBody
    public String teacherDoLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        Teacher teacher = teacherService.selectByUsernameAndPassword(username, password);
        //错误信息，登陆失败
        String msg = "";
        if (teacher != null) {
            session.setAttribute("teacher", teacher);
            return msg;
        } else {
            msg = "登陆失败！";
            return msg;
        }
    }

    //主页面
    @RequestMapping("/teacher/home")
    public String welcome() {
        return "teacher/home";
    }

    @RequestMapping("/teacher/homepage")
    public String homePage() {
        return "teacher/homepage";
    }

    @RequestMapping("/teacher/mainInterface")
    public String mainInterface(ModelMap model, HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        model.addAttribute("teacher", teacher);
        return "teacher/mainInterface";
    }

    //退出
    @RequestMapping("/teacher/exit")
    @ResponseBody
    public int exit(HttpSession session) {
        int sign;
        if (session.getAttribute("teacher") != null) {
            session.removeAttribute("teacher");
            sign = 1;
        } else {
            sign = 0;
        }
        return sign;
    }

    //个人信息修改
    @RequestMapping("/teacher/modify")
    public String modifyTeacher() {
        return "teacher/modify";
    }

    @RequestMapping("/teacher/doModify")
    @ResponseBody
    public String modifyTeacher(@RequestParam("teacherName") String name, @RequestParam("password") String password, HttpSession session) {
        String msg = "";
        String teacherName = "";
        String teacherPassword = "";
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        if (name == null || name == "") {
            teacherName = teacher.getTeacherName();
        } else teacherName = name;
        if (password == null || password == "") {
            teacherPassword = teacher.getTeacherPassword();
        } else teacherPassword = password;
        teacherService.updateById(teacherName, teacherPassword, id);
        msg = "修改成功";
        return msg;
    }

    //查看所教课程信息
    @RequestMapping("/teacher/classInformation/classInformation")
    public String classInformation() {
        return "teacher/classInformation/classInformation";
    }

    @RequestMapping("/teacher/classInformation/doInformation")
    public void doInformation(HttpSession session, ModelMap model) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        List<Map<String, Object>> list = teacherService.selectByTeachId(id);
        model.addAttribute("information", list);
    }

    //查看课程资源
//    @RequestMapping("/teacher/resource/resource")
//    public String resource() {
//        return "teacher/resource/resource";
//    }

    //1.查看自己的课程资源
    @RequestMapping("/teacher/resource/courseList")
    public String resource(HttpSession session,ModelMap model){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        List<Map<String,Object>> list = teacherService.selectAllCourseByTeacherId(id);
        model.addAttribute("course",list);
        return "teacher/resource/courseList";
    }
    @RequestMapping("/teacher/resource/resourceSelf")
    public String resourceSelf(int courseId,HttpSession session) {
        session.setAttribute("resourceCourseId",courseId);
        return "teacher/resource/resourceSelf";
    }

    @RequestMapping("/teacher/resource/doResourceSelf")
    public void resourceBySelf(HttpSession session, ModelMap model) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        int courseId = (int) session.getAttribute("resourceCourseId");
        List<Map<String, Object>> list = resourceService.selectResourceBySelf(id,courseId);
        model.addAttribute("resource", list);
    }

    //2.查看公共资源
    @RequestMapping("/teacher/resource/resourcePublic")
    public String resourcePublic(ModelMap model) {
        List<String> list = resourceService.selectAllCourseName();
        model.addAttribute("courseList", list);
        return "teacher/resource/resourcePublic";
    }

    @RequestMapping("/teacher/resource/doResourcePublic")
    public void resourceByPublic(HttpSession session, ModelMap model, @RequestParam("option") String course) {
        List<Map<String, Object>> list = resourceService.selectResourceByPublic(course);
        model.addAttribute("resource", list);
    }

    //删除资源
    @PostMapping("/teacher/resource/delete")
    @ResponseBody
    public int deleteResource(Integer id) {
        int sign;
        if (id != null || id != 0) {
            resourceService.deleteByResourceId(id);
            sign = 1;
            return sign;
        } else {
            sign = 0;
            return sign;
        }
    }

    //增加资源
    @RequestMapping("/teacher/resource/insert")
    public String insert(ModelMap model, HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        List<Map<String, Object>> list = teacherService.selectByTeachId(id);

        model.addAttribute("course", list);
        //model.addAttribute("chapter", chapter);
        return "teacher/resource/insert";
    }

    @PostMapping("/resourceChapterShow")
    @ResponseBody
    public List<Map<String, Object>> showChapter(ModelMap modelMap, HttpSession session, int courseId, HttpServletResponse response){
        List<Map<String,Object>> chapter = chapterService.selectByCourseId(courseId);
        response.setHeader("result", "success");
        return chapter;
    }

//    @RequestMapping("/teacher/resource/doInsert")
//    @ResponseBody
//    public String insertResource(String course, MultipartFile resourcePath, String resourceChapter, String resourceSecrecy) {
//        Integer secrecy = Integer.parseInt(resourceSecrecy);
//        String msg = "";
//        String path = "111";
//        List<Integer> teachId = teachService.selectByCourseName(course);
//        Resource resource = new Resource();
//        resource.setResourceChapter(resourceChapter);
//        resource.setResourcePath(path);
//        resource.setResourceSecrecy(secrecy);
//        for (Integer id : teachId) {
//            resource.setTeachId(id);
//            try {
//                resourceService.insertResource(resource);
//                msg = "成功";
//            } catch (Exception ex) {
//                msg = "失败";
//            }
//        }
//        return msg;
//    }

    @RequestMapping("/up")
    public String up() {
        return "teacher/resource/up";
    }

    /**
     * 文件上传类
     * 文件会自动绑定到MultipartFile中
     *
     * @param request     获取请求信
     * @param file        上传的文件
     * @return 上传成功或失败结果
     * @throws IOException
     * @throws IllegalStateException
     */

    @PostMapping("/teacher/resource/doInsert")
    public String upload(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpSession session,
                         @RequestParam("course")int course,@RequestParam("chapter")int resourceChapter,@RequestParam("public")String resourceSecrecy) throws IllegalStateException, IOException {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int id = teacher.getTeacherId();
        //Integer courseId = courseService.selectByCourseNameAndTeacherId(course,id);
        Integer courseId = course;
        Integer secrecy = Integer.parseInt(resourceSecrecy);
        String msg = "";
        String fileType = file.getContentType();

//        // 测试MultipartFile接口的各个方法
//        System.out.println("文件类型ContentType=" + file.getContentType());
//        System.out.println("文件组件名称Name=" + file.getName());
//        System.out.println("文件原名称OriginalFileName=" + file.getOriginalFilename());
//        System.out.println("文件大小Size=" + file.getSize() / 1024 + "KB");

        //String fileType = "";
        //包括后缀的文件名
        String realFileName = "";
        String fileName="";
        //String chapter="";
        //String ffmpegFileName="";
        realFileName = file.getOriginalFilename();

        if (file != null) {
//            realFileName = file.getOriginalFilename();
//            int pos = realFileName.lastIndexOf(".");
//            //int begin = realFileName.lastIndexOf("\\");
//            if (pos > -1) {
//                fileType = realFileName.substring(pos);
//                //ffmpegFileName = realFileName.substring(0,pos)+".mp4";
//                //fileName = realFileName.substring(begin+1);
//            }
            //获取第x单元中x字符串
            int end,start=0;
//            end = resourceChapter.indexOf("单元");
//            chapter = resourceChapter.substring(1,end);
            //String filePath = "/resources/upload" +"/"+ courseId +"/"+ chapter + "/";
            String filePath =  courseId +"/"+ resourceChapter + "/";

            int chapterId = chapterService.selectByChapterNumAndCourseId(resourceChapter,courseId);

            String ffmpegPath = "ffmpegPath/";

            //存储临时文件的，即为转换之前的视频
            String tempPath = "temp/";

            //获取target目录
            //String basePath = this.getClass().getResource("/").getPath().substring(1) + "/static/";
            //String basePath = fileManagerBeans.getResourcePath();
            //String basePath = fileManager.getResourceLocalStorePath();
            FileStoreInfo fileStoreInfo = fileManager.getFileStoreInfo(FileManager.FileType.RESOURCE, filePath);
            String basePath = fileStoreInfo.getStorePath();

            //文件夹的存储路径
            //String saveFilePath = basePath + filePath;
            String saveFilePath = basePath;

            File targetFile = new File(saveFilePath);
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();//创建父级文件路径
            }
            targetFile.mkdirs();//创建文件路径

            //转换过的文件的存储路径
            String savePath = saveFilePath + realFileName;
            //转换前的文件的存储路径
            String tempFilePath = saveFilePath + tempPath + realFileName;
            if(fileType.contains("video")) {
                //临时文件夹的创建
                File tempTargetFile = new File(saveFilePath + tempPath);
                tempTargetFile.mkdirs();

                File dest = new File(tempFilePath);
                try {
                    //保存该文件
                    file.transferTo(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //不是视频文件 无需创建temp文件夹
            else{
                File dest = new File(savePath);
                try {
                    //保存该文件
                    file.transferTo(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //数据库的访问路径
            //String dataBasePath = "target/classes/static"+filePath + realFileName;
            //String dataBasePath = fileManagerBeans.getResourceHandler()+filePath + realFileName;
            //String dataBasePath = fileManager.getResourceSQLStorePath()+filePath + realFileName;
            String dataBasePath = fileStoreInfo.getSQLPath()+ realFileName;

            //保存
            //疑问：数据库的存储应该在转换格式之后

            Resource resource = new Resource();
            resource.setResourceChapter(chapterId);
            resource.setResourcePath(dataBasePath);
            resource.setResourceSecrecy(secrecy);
            resource.setCourseId(courseId);
            resource.setResourceSign(0);
            try {
                resourceService.insertResource(resource);
                System.out.println("成功");
            } catch (Exception ex) {
                System.out.println("失败");
            }

            if(fileType.contains("video")) {
                threadExecute.Execute(tempFilePath, savePath);
            }
//
//            System.out.println("文件名"+realFileName);
//            System.out.println("文件存储路径"+savePath);
//            System.out.println("数据库访问路径"+dataBasePath);
        }
        return "teacher/resource/courseList";

    }

}
