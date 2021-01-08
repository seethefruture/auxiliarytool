/*
 * 文件名 StudentDoubtController
 * 创建人 吴昊
 * 创建日期 2019/9/23
 * 版权
 */

package com.cxxy.edu.controller;

import com.cxxy.edu.CustomException.InternalServerError;
import com.cxxy.edu.config.FileManager;
import com.cxxy.edu.entity.FileStoreInfo;
import com.cxxy.edu.service.*;
import com.cxxy.edu.utils.Utils;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class StudentDoubtController {
    @Autowired
    private DoubtService doubtService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeachService teachService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private FileManager fileManager;

    @RequestMapping("/getDoubt")
    public String getDoubt(HttpSession session, Model model) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> rawResult = studentService.getAllCourseByUsername(username);
            List<Map<String, Object>> result = new ArrayList<>();
            List<String> existCourseName = new ArrayList<>();

            for (Map<String, Object> map : rawResult) {
                String tempCourseName = map.get("course_name").toString();
                //新出现的课程，增加一个map
                if (!existCourseName.contains(tempCourseName)) {
                    existCourseName.add(tempCourseName);

                    Map<String, Object> pieceInfo = new HashMap<>();
                    pieceInfo.put("courseName", tempCourseName);
                    List<String> teacherName = new ArrayList<>();
                    teacherName.add(map.get("teacher_name").toString());
                    pieceInfo.put("teacherName", teacherName);
                    result.add(pieceInfo);
                }
                //已存在的课程，在其中的map key:teacherName value:list中增加
                else {
                    for (Map<String, Object> resultMap : result) {
                        if (resultMap.get("courseName").equals(tempCourseName)) {
                            List<String> teacherName = (List<String>) resultMap.get("teacherName");
                            teacherName.add(map.get("teacher_name").toString());
                            resultMap.put("teacherName", teacherName);
                        }
                    }
                }
            }
            model.addAttribute("resourceInfos", result);
            System.out.println(result);
        }
        return "student/innerHtml/doubt";
    }

    @PostMapping("/getDoubtAJAX")
    @ResponseBody
    public List<Map<String, Object>> getDoubtAJAX(HttpServletResponse response, String courseName, String teacherName) {
        List<Map<String, Object>> result = doubtService.getDoubtByCourseNameAndTeacherName(courseName, teacherName);
        Utils.timeFormat(result);
        response.setHeader("result", "success");
        return result;
    }

    @PostMapping("/insertDoubt")
    public String insertDoubt(HttpSession session, String doubt_question, @RequestParam(required = false, name = "questionImg") MultipartFile questionImg,
                              String courseName, String teacherName, Model model) throws IOException, InternalServerError {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            String sqlPath = "";
            if (!questionImg.isEmpty()) {

                //文件名或路径（相对）
                String fileName =  new SimpleDateFormat("yyyy-MM-dd HH_mm_ss").format(new Date()) + ".png";
                //根据类型和文件名、路径获取FileStoreInfo类
                FileStoreInfo fileStoreInfo = fileManager.getFileStoreInfo(FileManager.FileType.DOUBT, fileName);
                //得到待存储文件的sql路径
                sqlPath = fileStoreInfo.getSQLPath();
                //得到待存储文件的存储路径
                String filePath = fileStoreInfo.getStorePath();

                File imgFile = new File(filePath);
                if (!imgFile.exists()) {
                    boolean result = imgFile.createNewFile();
                    if (!result) {
                        throw new InternalServerError("创建文件失败");
                    }
                }
                if (questionImg.getSize() > 1024 * 1024 * 2) {//文件大于2M流式读取
                    FileOutputStream os = new FileOutputStream(imgFile);
                    InputStream is = questionImg.getInputStream();
                    byte[] buffer = new byte[1024 * 256];//256K缓存
                    while (is.read(buffer) != -1) {
                        os.write(buffer);
                    }
                } else {
                    questionImg.transferTo(new File(imgFile.getAbsolutePath()));
                }
            }
            int result = doubtService.insert1(doubt_question, courseName, teacherName, username, sqlPath);
            if (result > 0) {
                //成功
                model.addAttribute("result", "提交成功");
            } else {
                //失败
                model.addAttribute("result", "提交失败");
            }
        }
        return "student/innerHtml/resultResponse";
    }
}
