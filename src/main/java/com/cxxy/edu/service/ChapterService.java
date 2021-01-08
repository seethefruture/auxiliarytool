package com.cxxy.edu.service;

import com.cxxy.edu.mapper.ChapterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.color.CMMException;
import java.util.List;
import java.util.Map;

@Service
public class ChapterService {
    @Autowired
    private ChapterMapper chapterMapper;

    public Integer selectByChapterNumAndCourseId(int chapterNum,Integer courseId){
        return chapterMapper.selectByChapterNumAndCourseId(chapterNum,courseId);
    }

    public List<Map<String,Object>> selectByCourseId(int courseId){
        return chapterMapper.selectByCourseId(courseId);
    }

    public List<Map<String,Object>> selectByTeacherId(int teacherId){
        return chapterMapper.selectByTeacherId(teacherId);
    }
}
