package com.cxxy.edu.service;

import com.cxxy.edu.entity.Class;
import com.cxxy.edu.entity.Teacher;
import com.cxxy.edu.mapper.ClassMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: ClassService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1422:59
 */
@Service
public class ClassService {
    @Autowired
    private ClassMapper mapper;

    public void insert(Class clas) {
        mapper.insert(clas);
    }

    public void delete(int id) {
        mapper.deleteById(id);
    }

    public Class query(int faculty_id, int class_grade, int class_number) {
        return mapper.queryClass1(faculty_id, class_grade, class_number);
    }

    public Class query(int id) {
        return mapper.queryClass2(id);
    }

    public List<Class> queryAll(){
        return mapper.queryAll();
    }

    public List<Class> selectClass(String classname){
        return mapper.selectClass(classname);
    }

    public PageInfo<Map<String,Object>> selectByCollegeId(Integer id,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> teacherList = mapper.selectByCollegeId(id);
        PageInfo<Map<String,Object>> teacherPageInfo = new PageInfo<Map<String,Object>>(teacherList);;
        return teacherPageInfo;
    }

    public Class queryClass2(int id){
        return mapper.queryClass2(id);
    }

    public void update(String name,Integer facultyId,Integer grade,Integer num,Integer id){
        mapper.update(name,facultyId,grade,num,id);
    }

    public int selectClass1(String className,int classNumber,int classGrade){
       return mapper.selectClass1(className,classNumber,classGrade);
    }
}
