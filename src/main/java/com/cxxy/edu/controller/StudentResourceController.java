package com.cxxy.edu.controller;

import com.cxxy.edu.CustomException.InternalServerError;
import com.cxxy.edu.config.FileManager;
import com.cxxy.edu.entity.FileStoreInfo;
import com.cxxy.edu.messegeQueue.MQSender;
import com.cxxy.edu.service.ResourceService;
import com.cxxy.edu.service.StudentService;
import com.cxxy.edu.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: StuedntResourceContoller
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/5/1222:55
 */
@Controller
public class StudentResourceController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private MQSender sender;
    @Autowired
    private FileManager fileManager;

    /**
     * 返回指定课程的可选章节
     *
     * @param session
     * @param courseName
     * @param teacherName
     * @param model
     * @return
     */
    @RequestMapping("/getCourseResourcesChapter")
    public String getCourseResourcesChapter(HttpSession session, String courseName, String teacherName, Model model) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> resourceInfo = resourceService.selectResourceChapterByCourseNameAndTeacherName(teacherName, courseName);
            model.addAttribute("chapterInfos", resourceInfo);
        }

        return "student/innerHtml/courseResources";
    }

    @RequestMapping("/getCourseResourcesChapterAJAX")
    @ResponseBody
    public List<Map<String, Object>> getCourseResourcesChapterAJAX(HttpSession session, String courseName, String teacherName, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> resourceInfo = resourceService.selectResourceChapterByCourseNameAndTeacherName(teacherName, courseName);
            response.setHeader("result", "success");
            return resourceInfo;
        }
        response.setHeader("result", "fail");
        return null;
    }

    /**
     * 返回指定课程的指定章节的教学资源信息
     *
     * @param session
     * @param chapter
     * @param courseName
     * @param teacherName
     * @param response
     * @return
     * @throws InternalServerError
     */
    @RequestMapping("/getCourseResourcesAJAX")
    @ResponseBody
    public List<Map<String, Object>> getCourseResourcesAJAX(HttpSession session, String chapter, String courseName, String teacherName, HttpServletResponse response) throws InternalServerError {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> resourceInfo = resourceService.selectResourceByCourseNameAndTeacherNameAndChapter(teacherName, courseName, chapter);
            FileStoreInfo fileStoreInfo = fileManager.getFileStoreInfo(FileManager.FileType.RESOURCE, "");
            for (Map<String, Object> pieceResource : resourceInfo) {
                String filePath = fileStoreInfo.getStorePath().substring(0, fileStoreInfo.getStorePath().lastIndexOf("resource")) + pieceResource.get("resource_path").toString();
                File resourceFile = new File(filePath);
                if (resourceFile.exists()) {
                    pieceResource.put("resource_id", pieceResource.get("resource_id"));
                    pieceResource.put("file_name", pieceResource.get("resource_path").toString());
                    pieceResource.put("file_size", Utils.convertFileSize(resourceFile.length()));
                } else {
                    System.out.println("not-exit");
                    response.addHeader("result", " internalServerError");//本地存储异常
                    throw new InternalServerError(pieceResource.get("resource_path").toString() + "下找不到指定文件");
                }
            }
            response.addHeader("result", " success");
            return resourceInfo;
        }
        response.addHeader("result", "fail");
        return null;
    }
//
//    /**
//     * 下载或是在线浏览视频
//     *
//     * @param session
//     * @param courseName
//     * @param teacherName
//     * @param chapter
//     * @param fileName
//     * @param response
//     * @return
//     * @throws IOException
//     */
//    @RequestMapping("/getResource2")
//    @ResponseBody
//    public String downloadResource(HttpSession session, String courseName, String teacherName, String chapter,
//                                   @RequestHeader(required = false) String range, String fileName, HttpServletResponse response) throws IOException {
//        String username = session.getAttribute("username").toString();
//        if (username != null) {
//            List<Map<String, Object>> resourceInfo = resourceService.selectAllResourcePathByCourseNameAndTeacherNameAndChapter(teacherName, courseName, chapter);
//            for (Map<String, Object> pieceOfResource : resourceInfo) {
//                if (Utils.judgeFileName(pieceOfResource.get("resource_path").toString(), fileName)) {
//                    File resourceFile = new File(pieceOfResource.get("resource_path").toString());
//                    if (resourceFile.exists()) {
//                        System.out.println("start download");
//                        //开始下载位置
//                        long startByte = 0;
//                        //结束下载位置
//                        long endByte = resourceFile.length() - 1;
//                        Long contentLength = endByte - startByte + 1;
//                        //有range的话
//                        if (range != null && range.contains("bytes=") && range.contains("-")) {
//                            range = range.substring(range.lastIndexOf("=") + 1).trim();
//                            String[] ranges = range.split("-");
//                            try {
//                                //判断range的类型
//                                if (ranges.length == 1) {
//                                    //类型一：bytes=-2343
//                                    if (range.startsWith("-")) {
//                                        endByte = Long.parseLong(ranges[0]);
//                                    }
//                                    //类型二：bytes=2343-
//                                    else if (range.endsWith("-")) {
//                                        startByte = Long.parseLong(ranges[0]);
//                                    }
//                                }
//                                //类型三：bytes=22-2343
//                                else if (ranges.length == 2) {
//                                    startByte = Long.parseLong(ranges[0]);
//                                    endByte = Long.parseLong(ranges[1]);
//                                }
//                            } catch (NumberFormatException e) {
//                                startByte = 0;
//                                endByte = resourceFile.length() - 1;
//                            }
//                            //要下载的长度
//                            contentLength = endByte - startByte + 1;
//                            response.setStatus(response.SC_PARTIAL_CONTENT);        //支持断点续传的状态码 206
//                            response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + resourceFile.length());
//                        }
//                        response.setStatus(response.SC_OK);        //不断点续传的状态码 200
//                        response.addHeader("result", "success");
//                        response.setHeader("Content-type", "application/octet-stream");
//                        response.setHeader("Content-Disposition", "inline; filename=" + fileName);
//                        response.setHeader("Content-Length", String.valueOf(contentLength));
//                        OutputStream os = response.getOutputStream();//输出流
//                        byte[] buffer = new byte[5120];//缓存流0.5K
//                        FileInputStream is = new FileInputStream(resourceFile);//输入流
//                        is.skip(startByte);
//                        int len;
//                        int tempSendData = 0;
//                        while ((len = is.read(buffer)) != -1) {//读取并刷新一次流
//                            if (tempSendData >= endByte - startByte + 1) {
//                                break;
//                            } else {
//                                if (endByte - startByte + 1 - tempSendData < 5120) {
//                                    os.write(buffer, 0, (int) (endByte - startByte + 1) - tempSendData);
//                                    os.flush();
//                                    break;
//                                }
//                            }
//                            tempSendData += len;
//                            os.write(buffer, 0, len);
//                            os.flush();
//                        }
//                        is.close();
//                        os.close();
//                    } else {
//                        response.addHeader("result", "fail");
//                    }
//                }
//            }
//        }
//        return null;
//    }

    /**
     * 下载或是在线浏览视频
     *
     * @param session
     * @param resourceId
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/getResource")
    @ResponseBody
    public String getResourceById(HttpSession session, String resourceId,
                                  @RequestHeader(required = false) String range, HttpServletResponse response) throws IOException, InternalServerError {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            Map<String, String> resourceInfo = resourceService.selectResourcePathByCourseId(resourceId);
            File resourceFile = new File(fileManager.getFilePath(FileManager.FileType.RESOURCE, resourceInfo.get("resource_path").toString()));
            if (resourceFile.exists()) {
                System.out.println("start download");
                //开始下载位置
                long startByte = 0;
                //结束下载位置
                long endByte = resourceFile.length() - 1;
                Long contentLength = endByte - startByte + 1;
                //有range的话
                if (range != null && range.contains("bytes=") && range.contains("-")) {
                    range = range.substring(range.lastIndexOf("=") + 1).trim();
                    String[] ranges = range.split("-");
                    try {
                        //判断range的类型
                        if (ranges.length == 1) {
                            //类型一：bytes=-2343
                            if (range.startsWith("-")) {
                                endByte = Long.parseLong(ranges[0]);
                            }
                            //类型二：bytes=2343-
                            else if (range.endsWith("-")) {
                                startByte = Long.parseLong(ranges[0]);
                            }
                        }
                        //类型三：bytes=22-2343
                        else if (ranges.length == 2) {
                            startByte = Long.parseLong(ranges[0]);
                            endByte = Long.parseLong(ranges[1]);
                        }
                    } catch (NumberFormatException e) {
                        startByte = 0;
                        endByte = resourceFile.length() - 1;
                    }
                    //要下载的长度
                    contentLength = endByte - startByte + 1;
                    response.setStatus(response.SC_PARTIAL_CONTENT);        //支持断点续传的状态码 206
                    response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + resourceFile.length());
                }
                response.setStatus(response.SC_OK);        //不断点续传的状态码 200
                response.addHeader("result", "success");
                response.setHeader("Content-type", "application/octet-stream");
                response.setHeader("Content-Disposition", "inline; filename=" + resourceFile.getName());
                response.setHeader("Content-Length", String.valueOf(contentLength));
                OutputStream os = response.getOutputStream();//输出流
                byte[] buffer = new byte[5120];//缓存流0.5K
                FileInputStream is = new FileInputStream(resourceFile);//输入流
                is.skip(startByte);
                int len;
                int tempSendData = 0;
                while ((len = is.read(buffer)) != -1) {//读取并刷新一次流
                    if (tempSendData >= endByte - startByte + 1) {
                        break;
                    } else {
                        if (endByte - startByte + 1 - tempSendData < 5120) {
                            os.write(buffer, 0, (int) (endByte - startByte + 1) - tempSendData);
                            os.flush();
                            break;
                        }
                    }
                    tempSendData += len;
                    os.write(buffer, 0, len);
                    os.flush();
                }
                is.close();
                os.close();
            } else {
                response.addHeader("result", "fail");
                throw new InternalServerError(resourceInfo.get("resource_path").toString() + "下找不到指定文件");
            }
        }
        return null;
    }


    /**
     * 返回指定username的所有课程信息
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/getCourseInfoForResource")
    public String getCourseInfoForResource(HttpSession session, Model model) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> courseInfo = studentService.getAllCourseByUsername(username);
            model.addAttribute("courseInfos", courseInfo);
        }
        return "student/innerHtml/courseInfoForResource";
    }

    @RequestMapping("/getCourseInfoForResourceAJAX")
    @ResponseBody
    public List<Map<String, Object>> getCourseInfoForResourceAJAX(HttpSession session, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            List<Map<String, Object>> courseInfo = studentService.getAllCourseByUsername(username);
            response.setHeader("result", "success");
            return courseInfo;
        }
        response.setHeader("result", "fail");
        return null;
    }

    /**
     * 返回所有公共课程和授课教师信息
     *
     * @param model
     * @return
     */
    @RequestMapping("/getAllCourseInfoForResource")
    public String getAllCourseInfoForResource(Model model) {
        List<Map<String, Object>> rawResult = resourceService.selectAllPublicCourseInfo();
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
        return "student/innerHtml/courseInfoForAllResource";
    }

    /*
    @RequestMapping("/getAllCourseInfoForResourceAJAX")
    @ResponseBody
    public List<Map<String, Object>> getAllCourseInfoForResourceAJAX(HttpSession session, HttpServletResponse response) {
        String username = session.getAttribute("username").toString();
        if (username != null) {

            response.setHeader("result", "success");
        }
        response.setHeader("result", "fail");
        return null;
    }
*/

    /**
     * 返回指定课程的课程资源信息
     *
     * @param courseName
     * @param teacherName
     * @param response
     */
    @PostMapping("/getAllCourseInfoAJAX")
    @ResponseBody
    public List<Map<String, Object>> getAllCourseInfoAJAX(String courseName, String teacherName, HttpServletResponse response) throws InternalServerError {
        List<Map<String, Object>> resourceInfo = resourceService.selectPublicResourceByCourseNameAndTeacherName(teacherName, courseName);
        for (Map<String, Object> pieceResource : resourceInfo) {
            File resourceFile = new File(pieceResource.get("resource_path").toString());
            if (resourceFile.exists()) {
                pieceResource.put("resource_id", pieceResource.get("resource_id"));
                pieceResource.put("resource_chapter", pieceResource.get("resource_chapter"));
                pieceResource.put("file_name", resourceFile.getName());
                pieceResource.put("file_size", Utils.convertFileSize(resourceFile.length()));
            } else {
                System.out.println("not-exit");
                response.addHeader("result", " internalServerError");//本地存储异常
                throw new InternalServerError(pieceResource.get("resource_path").toString() + "下找不到指定文件");
            }
        }
        response.addHeader("result", " success");
        return resourceInfo;
    }

    @PostMapping("/returnViewProgressAJAX")
    @ResponseBody
    public String returnViewProgressAJAX(HttpSession session, String resourceId, String timeout, String currentTime) {
        String username = session.getAttribute("username").toString();
        if (username != null) {
            sender.sendViewProgress("viewProgress", username, resourceId, timeout, currentTime);
            return "success";
        }
        return "error";
    }

    @PostMapping("/resourceView")
    public String resourceView(String resourceId, Model model) {
        model.addAttribute("resourceId", resourceId);
        return "student/innerHtml/resourceView";
    }
}
