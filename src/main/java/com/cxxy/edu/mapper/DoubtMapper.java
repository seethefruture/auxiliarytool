package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.Doubt;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: DoubtMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1423:07
 */

public interface DoubtMapper {

    @Insert("insert into doubt (teach_id,doubt_question,doubt_question_path,student_id"
            + ",doubt_answer,doubt_answer_path,doubt_time) values (#{teachId},#{doubtQuestion},#{doubtQuestionPath}"
            + ",#{studentId},#{doubtAnswer},#{doubtAnswerPath},#{doubtTime})")
    void insert(Doubt doubt);

    @Insert("INSERT INTO doubt ( doubt_question, doubt_question_path,teach_id, student_id, doubt_time )\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t#{doubt_question},\n" +
            "\t#{question_path},\n" +
            "\t(\n" +
            "SELECT\n" +
            "\tteach_id \n" +
            "FROM\n" +
            "\tteach \n" +
            "WHERE\n" +
            "\tclass_id = ( SELECT class_id FROM student WHERE student_username = #{username} ) \n" +
            "\tAND course_id = ( SELECT course_id FROM course, teacher WHERE course_name = #{courseName} AND teacher_name = #{teacherName} AND course.teacher_id = teacher.teacher_id ) \n" +
            "\t),\n" +
            "\t( SELECT student_id FROM student WHERE student_username = #{username} ),\n" +
            "\tCURRENT_TIME ( ) \n" +
            "\t)")
    int insert1(String doubt_question, String courseName, String teacherName, String username, String question_path);

    @Update("update doubt set doubt_answer = #{doubtAnswer},doubt_answer_path = #{doubtAnswerPath}")
    void updateAnswer(String doubt_answer, String doubt_answer_path);

    @Select("select * from doubt where student_id = #{studentId} and tech_id = {techId}")
    @Results({
            @Result(id = true, property = "doubtId", column = "doubt_id"),
            @Result(property = "techId", column = "tech_id"),
            @Result(property = "doubtQuestion", column = "doubt_question"),
            @Result(property = "doubtQuestionPath", column = "doubt_question_path"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "teacherId", column = "teacher_id"),
            @Result(property = "doubtAnswer", column = "doubt_answer"),
            @Result(property = "doubtAnswerPath", column = "doubt_answer_path"),

    })
    List<Doubt> query(int student_id, int tech_id);

    @Delete("delete from doubt where id = #{doubtId}")
    void delete(int id);

    @Select("SELECT\n" +
            "\tdoubt_question,\n" +
            "\tdoubt_answer,\n" +
            "\tdoubt_question_path,\n" +
            "\tdoubt_answer_path,\n" +
            "\tteacher_name,\n" +
            "\tstudent_name, \n" +
            "\tdoubt_time \n" +
            "FROM\n" +
            "\tdoubt,\n" +
            "\tteach,\n" +
            "\tcourse,\n" +
            "\tteacher,\n" +
            "\tstudent \n" +
            "WHERE\n" +
            "\tdoubt.teach_id = teach.teach_id \n" +
            "\tAND teach.course_id = course.course_id \n" +
            "\tAND doubt.student_id = student.student_id \n" +
            "\tAND teacher_name = #{teacherName} \n" +
            "\tAND course_name = #{courseName}")
    List<Map<String, Object>> getDoubtByCourseNameAndTeacherName(String courseName, String teacherName);
}
