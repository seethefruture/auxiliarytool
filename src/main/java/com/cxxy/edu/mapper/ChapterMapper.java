package com.cxxy.edu.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ChapterMapper {

    @Select("SELECT chapter_id\n" +
            "FROM chapter \n" +
            "WHERE chapter_course_id=#{courseId} AND chapter_num=#{chapterNum}")
    Integer selectByChapterNumAndCourseId(int chapterNum,Integer courseId);

    @Select("SELECT chapter_num,chapter_name,chapter_id\n" +
            "FROM chapter\n" +
            "WHERE chapter_course_id=#{courseId}")
    List<Map<String,Object>> selectByCourseId(Integer courseId);

    @Select("SELECT chapter_num,chapter_name,chapter_id\n" +
            "FROM chapter,course\n" +
            "WHERE chapter.chapter_course_id=course.course_id AND teacher_id=#{teacherId}")
    List<Map<String,Object>> selectByTeacherId(int teacherId);
}
