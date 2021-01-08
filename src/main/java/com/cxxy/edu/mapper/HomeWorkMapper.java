package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.Homework;
import com.cxxy.edu.entity.Test;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: HomeWorkMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/2818:54
 */

public interface HomeWorkMapper {

    @Insert("insert into homework (teach_id,homework_chapter,contain_question_id,submit_time) values" +
            "(#{teachId},#{homeworkChapter},#{containQuestionId},#{submitTime})")
    void insert(Homework homework);

//    @Select("SELECT\n" +
//            "  `course_name`,\n" +
//            "  `teacher_name`,\n" +
//            "  `class_grade`,\n" +
//            "  `class_name`,\n" +
//            "  `class_number`,\n" +
//            "  `student_name`,\n" +
//            "  COUNT(`homework_id`)\n" +
//            "FROM\n" +
//            "  `teach`,\n" +
//            "  `course`,\n" +
//            "  `teacher`,\n" +
//            "  `class`,\n" +
//            "  `student`,\n" +
//            "  `homework`\n" +
//            "WHERE\n" +
//            "  `course`.`course_id` = `teach`.`course_id`\n" +
//            "    AND `course`.`teacher_id` = `teacher`.`teacher_id`\n" +
//            "    AND `teach`.`class_id` = `class`.`class_id`\n" +
//            "    AND `teach`.`class_id` = `student`.`class_id`\n" +
//            "    AND `teach`.`teach_id` = `homework`.`teach_id`\n" +
//            "    AND NOW() > `homework`.`submit_time`\n" +
//            "    ORDER BY `homework`.`submit_time`\n" +
//            "    AND `student`.`student_username` = #{username}")
   // List<Map<String, Object>> getAllCourseForHistoryHomeWorkByUsername(String username);

    @Select("SELECT\n" +
            "  `homework_id`,\n" +
            "  `teacher_name`,\n" +
            "  `course_name`,\n" +
            "  `homework_chapter`,\n" +
            "  `submit_time`\n" +
            "FROM\n" +
            "  `teach`,\n" +
            "  `course`,\n" +
            "  `teacher`,\n" +
            "  `class`,\n" +
            "  `student`,\n" +
            "  `homework`\n" +
            "WHERE\n" +
            "  `course`.`course_id` = `teach`.`course_id`\n" +
            "    AND `course`.`teacher_id` = `teacher`.`teacher_id`\n" +
            "    AND `teach`.`class_id` = `class`.`class_id`\n" +
            "    AND `teach`.`class_id` = `student`.`class_id`\n" +
            "    AND `teach`.`teach_id` = `homework`.`teach_id`\n" +
            "    AND NOW() > `submit_time`\n" +
            "    AND `student`.`student_username` = #{username}\n" +
            "    AND `teacher_name` = #{teacherName}\n" +
            "    AND `course_name` = #{courseName}\n" +
            "UNION\n" +
            "SELECT\n" +
            "  `homework_id`,\n" +
            "  `teacher_name`,\n" +
            "  `course_name`,\n" +
            "  `homework_chapter`,\n" +
            "  `submit_time`\n" +
            "FROM\n" +
            "  `teach`,\n" +
            "  `course`,\n" +
            "  `teacher`,\n" +
            "  `class`,\n" +
            "  `student`,\n" +
            "  `homework`\n" +
            "WHERE\n" +
            "  `course`.`course_id` = `teach`.`course_id`\n" +
            "    AND `course`.`teacher_id` = `teacher`.`teacher_id`\n" +
            "    AND `teach`.`class_id` = `class`.`class_id`\n" +
            "    AND `teach`.`class_id` = `student`.`class_id`\n" +
            "    AND `teach`.`teach_id` = `homework`.`teach_id`\n" +
            "    AND NOW() < `submit_time`\n" +
            "    AND `student`.`student_username` = #{username}\n" +
            "    AND `teacher_name` = #{teacherName}\n" +
            "    AND `course_name` = #{courseName}\n" +
            "    AND `homework`.`homework_id` IN (SELECT\n" +
            "      `homework`.`homework_id`\n" +
            "    FROM\n" +
            "      `homework`,\n" +
            "      `homework_score`,\n" +
            "      `student`\n" +
            "    WHERE\n" +
            "      `homework`.`homework_id` = `homework_score`.`homework_id` AND `student`.`student_id` = `homework_score`.`student_id` AND `student`.`student_username` = #{username})\n" +
            "ORDER BY `submit_time`")
    List<Map<String, Object>> getAllHistoryHomeWorkByUsernameAndCourseNameAndTeacherName(String username, String courseName, String teacherName);

    @Select("SELECT\n" +
            "  `homework_id`,\n" +
            "  `teacher_name`,\n" +
            "  `course_name`,\n" +
            "  `homework_chapter`,\n" +
            "  `submit_time`\n" +
            "FROM\n" +
            "  `teach`,\n" +
            "  `course`,\n" +
            "  `teacher`,\n" +
            "  `class`,\n" +
            "  `student`,\n" +
            "  `homework`\n" +
            "WHERE\n" +
            "  `course`.`course_id` = `teach`.`course_id`\n" +
            "    AND `course`.`teacher_id` = `teacher`.`teacher_id`\n" +
            "    AND `teach`.`class_id` = `class`.`class_id`\n" +
            "    AND `teach`.`class_id` = `student`.`class_id`\n" +
            "    AND `teach`.`teach_id` = `homework`.`teach_id`\n" +
            "    AND NOW() < `submit_time`\n" +
            "    AND `student`.`student_username` = #{username}\n" +
            "    AND `teacher_name` = #{teacherName}\n" +
            "    AND `course_name` = #{courseName}\n" +
            "    AND `homework`.`homework_id`  NOT IN (SELECT\n" +
            "      `homework`.`homework_id`\n" +
            "    FROM\n" +
            "      `homework`,\n" +
            "      `homework_score`,\n" +
            "      `student`\n" +
            "    WHERE\n" +
            "      `homework`.`homework_id` = `homework_score`.`homework_id` AND `student`.`student_id` = `homework_score`.`student_id` AND `student`.`student_username` = #{username})\n" +
            "ORDER BY `submit_time`")
    List<Map<String, Object>> getAllHomeWorkByUsernameAndCourseNameAndTeacherName(String username, String courseName, String teacherName);

    @Select("SELECT *\n" +
            "FROM homework\n" +
            "WHERE teach_id=#{teachId}\n" +
            "ORDER BY submit_time DESC")
    List<Homework> selectByTeachId(Integer teachId);

    @Delete("DELETE \n" +
            "FROM homework\n" +
            "WHERE homework_id=#{homeworkId}")
    void deleteByHomeworkId(Integer homeworkId);

    @Select("SELECT *\n" +
            "FROM homework\n" +
            "WHERE homework_id=#{homeworkId}")
    Homework selectByHomeworkId(Integer homeworkId);


}
