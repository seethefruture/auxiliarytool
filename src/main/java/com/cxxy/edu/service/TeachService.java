package com.cxxy.edu.service;

import com.cxxy.edu.entity.Teach;
import com.cxxy.edu.mapper.ClassMapper;
import com.cxxy.edu.mapper.TeachMapper;
import com.cxxy.edu.mapper.TeacherMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: TeachService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/2819:11
 */
@Service
public class TeachService {
    @Autowired
    private TeachMapper mapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    public void insert(Teach teach) {
        mapper.insert(teach);
    }

    public List<Teach> queryByClassId(int classId) {
        return mapper.queryByClassId(classId);
    }

    //通过课程id和教师id找到相对应的班级名和班级id
    public List<Map<String, Object>> selectClassInformation(Integer courseId, Integer teacherId) {
        return mapper.selectClassInformation(courseId, teacherId);
    }

    public Integer selectTeachId(Integer courseId, Integer classId) {
        return mapper.selectTeachId(courseId, classId);
    }

    public PageInfo<Map<String,Object>> selectTeachMessage(String courseName, String className,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> courseList = mapper.selectTeachMessage(courseName, className);
        PageInfo<Map<String,Object>> coursePageInfo = new PageInfo<Map<String,Object>>(courseList);;
        return coursePageInfo;
    }

    public List<Map<String, Object>> selectAllTeachMessage() {
        return mapper.selectAllTeachMessage();
    }

    public void delete(Integer teachId) {
        mapper.delete(teachId);
    }

    public Teach selectTeach(String courseId, String classId) {
        return mapper.selectTeach(courseId, classId);
    }

    public Map<String,Object> selectByTeachId(Integer teachId){
        return mapper.selectByTeachId(teachId);
    }

    public void update(Integer teachId,Integer classId,Integer courseId,String teachTime){
        mapper.update(teachId, classId, courseId, teachTime);
    }
}
