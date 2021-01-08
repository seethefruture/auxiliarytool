package com.cxxy.edu.service;

import com.cxxy.edu.entity.Resource;
import com.cxxy.edu.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    public List<Map<String, Object>> selectResourceBySelf(Integer id,Integer courseId) {
        return resourceMapper.selectResourceBySelf(id,courseId);
    }

    public List<Map<String, Object>> selectResourceByPublic(String course_name) {
        return resourceMapper.selectResourceByPublic(course_name);
    }

    public List<String> selectAllCourseName() {
        return resourceMapper.selectAllCourseName();
    }

    public void deleteByResourceId(Integer resourceId) {
        resourceMapper.deleteByResourceId(resourceId);
    }

    public void insertResource(Resource resource) {
        resourceMapper.insertResource(resource);
    }

    public List<Map<String, Object>> selectPublicResourceByCourseNameAndTeacherName(String teacherName, String courseName) {
        return resourceMapper.selectPublicResourceByCourseNameAndTeacherName(teacherName, courseName);
    }

    public List<Map<String, Object>> selectPrivateResourceByCourseNameAndTeacherName(String teacherName, String courseName) {
        return resourceMapper.selectPrivateResourceByCourseNameAndTeacherName(teacherName, courseName);
    }

    public List<Map<String, Object>> selectResourceChapterByCourseNameAndTeacherName(String teacherName, String courseName) {
        return resourceMapper.selectResourceChapterByCourseNameAndTeacherName(teacherName, courseName);
    }

    public List<Map<String, Object>> selectResourceByCourseNameAndTeacherNameAndChapter(String teacherName, String courseName, String chapter) {
        return resourceMapper.selectResourceByCourseNameAndTeacherNameAndChapter(teacherName, courseName, chapter);
    }

    public List<Map<String, Object>> selectAllResourcePathByCourseNameAndTeacherNameAndChapter(String teacherName, String courseName, String chapter) {
        return resourceMapper.selectAllResourcePathByCourseNameAndTeacherNameAndChapter(teacherName, courseName, chapter);
    }

    public List<Map<String, Object>> selectAllPublicCourseInfo() {
        return resourceMapper.selectAllPublicCourseInfo();
    }

    public Map<String, String> selectResourcePathByCourseId(String resourceId) {
        return resourceMapper.selectResourcePathByCourseId(resourceId);
    }
}
