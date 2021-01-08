package com.cxxy.edu.service;

import com.cxxy.edu.entity.College;
import com.cxxy.edu.mapper.CollegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @title: CollegeService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1319:51
 */
@Service
public class CollegeService {
    @Autowired
    private CollegeMapper mapper;

    public void insert(College college) {
        mapper.insert(college);
    }

    public void deleteByName(String name) {
        mapper.deleteByName(name);
    }

    public List<College> selectAll(){
        return mapper.selectAll();
    }

    public void update(Integer id,String name){
        mapper.update(id,name);
    }

    public College queryById(int id){
        return mapper.queryById(id);
    }

    public void deleteById(int id){
        mapper.deleteById(id);
    }
}
