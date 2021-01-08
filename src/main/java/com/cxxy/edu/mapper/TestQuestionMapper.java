package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.TestQuestion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TestQuestionMapper {

    @Select("SELECT question_id,test_question_detail,test_question_a,test_question_b,test_question_c,test_question_d,\n" +
            "            test_question_answer,test_question_type,test_question_correct,test_question_num,test_question.course_id,test_question_chapter\n" +
            "            FROM test_question,course,chapter\n" +
            "            WHERE test_question.course_id=course.course_id AND course.course_id=chapter.chapter_course_id\n" +
            "            AND course.course_id=#{id} AND test_question_chapter=#{chapter}\n" +
            "            GROUP BY question_id")
    List<TestQuestion> selectAllByCourseIdAndChapter(Integer id, Integer chapter);

    @Select("SELECT *\n" +
            "FROM test_question\n" +
            "WHERE question_id=#{questionId}")
    TestQuestion selectAllByQuestionId(Integer questionId);

    @Insert("INSERT INTO test_question(course_id,test_question_detail,test_question_a,test_question_b,test_question_c,test_question_d,test_question_answer,test_question_type,test_question_chapter,test_question_num,test_question_correct) " +
            "VALUES(#{courseId},#{testQuestionDetail},#{testQuestionA},#{testQuestionB},#{testQuestionC},#{testQuestionD},#{testQuestionAnswer},#{testQuestionType},#{testQuestionChapter},#{testQuestionNum},#{testQuestionCorrect})")
    void insert(TestQuestion testQuestion);

    @Update("UPDATE test_question\n" +
            "SET test_question_detail=#{detail},test_question_a=#{questionA},test_question_b=#{questionB},test_question_c=#{questionC},test_question_d=#{questionD},test_question_type=#{type},test_question_answer=#{answer}\n" +
            "WHERE question_id=#{id}")
    void update(String detail, String questionA, String questionB, String questionC, String questionD, String type, String answer, Integer id);

    @Delete("DELETE FROM test_question WHERE question_id=#{questionId}")
    void deleteByQuestionId(Integer questionId);

    @Update("update test_question set test_question_correct = #{correct},test_question_num = #{num} where question_id = #{test_question_id}")
    int updateQuestionCorrect(int test_question_id, float correct, int num);

}
