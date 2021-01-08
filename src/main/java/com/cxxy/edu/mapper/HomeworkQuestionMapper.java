package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.HomeworkQuestion;
import com.cxxy.edu.entity.TestQuestion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface HomeworkQuestionMapper {
    @Select("SELECT homework_question.homework_question_id,homework_question_detail,homework_question_answer,homework_question_num\n" +
            "FROM homework_question,course,chapter\n" +
            "WHERE homework_question.course_id=course.course_id AND course.course_id=chapter.chapter_course_id \n" +
            "\tAND course.course_id=#{id} AND chapter_id=#{chapter}")
    List<HomeworkQuestion> selectAllByCourseIdAndChapter(Integer id, Integer chapter);

    @Select("SELECT *\n" +
            "FROM homework_question\n" +
            "WHERE homework_question_id=#{questionId}")
    HomeworkQuestion selectAllByQuestionId(Integer questionId);

    @Update("UPDATE homework_question\n" +
            "SET homework_question_detail=#{detail},homework_question_answer=#{answer}\n" +
            "WHERE homework_question_id=#{id}")
    void update(String detail,String answer,Integer id);

    @Insert("INSERT INTO homework_question(course_id,homework_question_detail,homework_question_answer,homework_question_chapter,homework_question_num,homework_question_correct) " +
            "VALUES(#{courseId},#{homeworkQuestionDetail},#{homeworkQuestionAnswer},#{homeworkQuestionChapter},#{homeworkQuestionNum},#{homeworkQuestionCorrect})")
    void insert(HomeworkQuestion homeworkQuestion);

    @Delete("DELETE FROM homework_question WHERE homework_question_id=#{questionId}")
    void deleteByQuestionId(Integer questionId);

//    @Select("")
//    List<Map<String, Object>> getAllHistoryHomeWorkQuestionByUsernameAndCourseNameAndTeacherName(String username, String courseName, String teacherName);
//
//    @Select("")
//    List<Map<String, Object>> getAllHomeWorkQuestionByUsernameAndCourseNameAndTeacherName(String username, String courseName, String teacherName);
}
