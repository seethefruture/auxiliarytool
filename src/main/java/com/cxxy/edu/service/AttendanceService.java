package com.cxxy.edu.service;

import com.cxxy.edu.entity.Attendance;
import com.cxxy.edu.mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @title: AttendanceService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1323:15
 */
@Service
public class AttendanceService {
    @Autowired
    private AttendanceMapper mapper;

    public void insert(Attendance attendance) {
        mapper.insert(attendance);
    }

    public void deleteById(int id) {
        mapper.deleteById(id);
    }

    public Attendance queryById(int id) {
        return mapper.queryById(id);
    }

}
