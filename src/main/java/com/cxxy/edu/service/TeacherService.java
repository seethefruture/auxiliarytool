package com.cxxy.edu.service;

import com.cxxy.edu.entity.Teacher;
import com.cxxy.edu.mapper.TeacherMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public Teacher selectByUsernameAndPassword(String username, String password){
        return teacherMapper.selectByUsernameAndPassword(username,password);
    }

    public void updateById(String name,String password,Integer id){
        teacherMapper.updateById(name, password, id);
    }

    public List<Map<String,Object>> selectByTeachId(Integer id){
        return teacherMapper.selectByTeachId(id);
    }

    public PageInfo<Teacher> selectAll(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Teacher> teacherList = teacherMapper.selectAll();
        PageInfo<Teacher> teacherPageInfo = new PageInfo<Teacher>(teacherList);;
        return teacherPageInfo;
    }

    public List<Teacher> selectByTeacherName(String name){
        return teacherMapper.selectByTeacherName(name);
    }

    public void insertTeacher(Teacher teacher){
        teacherMapper.insertTeacher(teacher);
    }

    public void deleteByTeacherId(Integer id){
        teacherMapper.deleteByTeacherId(id);
    }

    public Teacher selectByTeacherId(Integer id){
        return teacherMapper.selectByTeacherId(id);
    }

    public void updateByTeacherId(String name, String username, Integer id){
        teacherMapper.updateByTeacherId(name,username,id);
    }

    public List<Map<String,Object>> selectAllCourseByTeacherId(Integer teacherId){
        return teacherMapper.selectAllCourseByTeacherId(teacherId);
    }
}
