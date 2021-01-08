package com.cxxy.edu.service;

import com.cxxy.edu.entity.Student;
import com.cxxy.edu.mapper.StudentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: StudentService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1322:54
 */
@Service
public class StudentService {
    @Autowired
    private StudentMapper mapper;

    public void insert(Student student) {
        mapper.insert(student);
    }

    public void delete(String username) {
        mapper.delete(username);
    }

    public Student queryById(int id) {
        return mapper.queryById(id);
    }

    public Student queryByUsername(String username) {
        return mapper.queryByUsername(username);
    }

    public List<Map<String, Object>> getAllCourseForHomeWorkByUsername(String username) {
        return mapper.getAllCourseForHomeWorkByUsername(username);
    }

    public List<Map<String, Object>> getAllCourseForHistoryHomeWorkByUsername(String username) {
        return mapper.getAllCourseForHistoryHomeWorkByUsername(username);
    }

    public List<Map<String, Object>> getAllCourseForTestByUsername(String username) {
        return mapper.getAllCourseForTestByUsername(username);
    }

    public List<Map<String, Object>> getAllCourseForHistoryTestByUsername(String username) {
        return mapper.getAllCourseForHistoryTestByUsername(username);
    }

    public List<Map<String, Object>> getAllCourseByUsername(String username) {
        List<Map<String, Object>> result = mapper.getAllCourseByUsername(username);
        for (Map<String, Object> map : result) {
            if (map.get("teach_time") != null) {
                String[] time = map.get("teach_time").toString().split(",");
                map.put("teach_time", "第" + time[0] + "学年 第" + time[1] + "学期");
            }
        }
        return result;
    }

    public List<Map<String, Object>> getOtherCourseByUsername(String username) {
        List<Map<String, Object>> result = mapper.getOtherCourseByUsername(username);
        for (Map<String, Object> map : result) {
            if (map.get("teach_time") != null) {
                String[] time = map.get("teach_time").toString().split(",");
                map.put("teach_time", "第" + time[0] + "学年 第" + time[1] + "学期（非本学期）");
            }
        }
        return result;
    }

    public PageInfo<Student> selectStudentByClassId(Integer classId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Student> studentList = mapper.selectStudentByClassId(classId);
        PageInfo<Student> studentPageInfo = new PageInfo<Student>(studentList);;
        return studentPageInfo;
    }

    public List<Student> selectStudentByClassId(int classId){
        return mapper.selectStudentByClassId(classId);
    }

}
