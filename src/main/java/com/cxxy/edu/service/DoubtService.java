package com.cxxy.edu.service;

import com.cxxy.edu.entity.Doubt;
import com.cxxy.edu.mapper.DoubtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: DoubtService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1423:44
 */
@Service
public class DoubtService {
    @Autowired
    private DoubtMapper mapper;

    public void insert(Doubt doubt) {
        mapper.insert(doubt);
    }

    public int insert1(String doubt_question, String courseName, String teacherName, String username, String question_path) {
        return mapper.insert1(doubt_question, courseName, teacherName, username, question_path);
    }

    public List<Doubt> query(int student_id, int tech_id) {
        return mapper.query(student_id, tech_id);
    }

    public void delete(int id) {
        mapper.delete(id);
    }

    public void update(String doubt_answer, String doubt_answer_path) {
        mapper.updateAnswer(doubt_answer, doubt_answer_path);
    }

    public List<Map<String, Object>> getDoubtByCourseNameAndTeacherName(String courseName, String teacherName) {
        return mapper.getDoubtByCourseNameAndTeacherName(courseName, teacherName);
    }
}
