package com.cxxy.edu.service;

import com.cxxy.edu.entity.Faculty;
import com.cxxy.edu.mapper.FacultyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @title: FacultyService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1320:11
 */
@Service
public class FacultyService {
    @Autowired
    private FacultyMapper mapper;

    public void insert(Faculty faculty) {
        mapper.insert(faculty);
    }

    public void delete(String name) {
        mapper.delete(name);
    }

    public Faculty queryById(int id) {
        return mapper.queryById(id);
    }

    public List<Faculty> selectByCollegeId(Integer id){
        return mapper.selectByCollegeId(id);
    }

    public void deleteById(Integer id){
        mapper.deleteById(id);
    }

    public void update(String name,Integer id){
        mapper.update(name,id);
    }
}
