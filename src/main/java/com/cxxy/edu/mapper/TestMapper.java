package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.Test;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TestMapper {
    @Select("SELECT *\n" +
            "FROM test\n" +
            "WHERE teach_id=#{teachId}\n" +
            "ORDER BY test_time DESC")
    List<Test> selectByTeachId(Integer teachId);

    @Select("SELECT *\n" +
            "FROM test\n" +
            "WHERE test_id=#{testId}")
    Test selectByTestId(Integer testId);

    @Insert("INSERT INTO test(test_question_contain,test_time,teach_id,submit_time,test_num)\n" +
            "VALUES(#{testQuestionContain},#{testTime},#{teachId},#{submitTime},#{testNum})")
    void insertTest(Test test);

    @Delete("DELETE \n" +
            "FROM test\n" +
            "WHERE test_id=#{testId}")
    void deleteByTestId(Integer testId);

    @Select("SELECT\n" +
            "  `test_id`,\n" +
            "  `student_name`,\n" +
            "  `teacher_name`,\n" +
            "  `course_name`,\n" +
            "  `test_question_contain`,\n" +
            "  `submit_time`,\n" +
            "  `test_time`,\n" +
            "  `test_num`\n" +
            "FROM\n" +
            "  `teach`,\n" +
            "  `course`,\n" +
            "  `teacher`,\n" +
            "  `class`,\n" +
            "  `student`,\n" +
            "  `test`\n" +
            "WHERE\n" +
            "  `course`.`course_id` = `teach`.`course_id`\n" +
            "    AND `course`.`teacher_id` = `teacher`.`teacher_id`\n" +
            "    AND `teach`.`class_id` = `class`.`class_id`\n" +
            "    AND `teach`.`class_id` = `student`.`class_id`\n" +
            "    AND `teach`.`teach_id` = `test`.`teach_id`\n" +
            "    AND `student`.`student_username` = #{username}\n" +
            "    AND `teacher_name` = #{teacherName}\n" +
            "    AND `course_name` = #{courseName}\n" +
            "    AND (NOW() > `submit_time` OR (NOW() < `submit_time` AND NOW() > `test_time` AND `test`.`test_id` IN (SELECT\n" +
            "      `test_id`\n" +
            "    FROM\n" +
            "      `test_score`,\n" +
            "      `student`\n" +
            "    WHERE\n" +
            "      `student_username` = #{username} AND `student`.`student_id` = `test_score`.`student_id`)))\n" +
            "ORDER BY `submit_time`")
    List<Map<String, Object>> getAllHistoryTestQuestionByUsernameAndCourseNameAndTeacherName(String username, String courseName, String teacherName);

    @Select("   SELECT\n" +
            "      `test_id`,\n" +
            "      `student_name`,\n" +
            "      `teacher_name`,\n" +
            "      `course_name`,\n" +
            "      `test_question_contain`,\n" +
            "      `test_time`,\n" +
            "      `submit_time`,\n" +
            "      `test_num`\n" +
            "    FROM\n" +
            "      `teach`,\n" +
            "      `course`,\n" +
            "      `teacher`,\n" +
            "      `class`,\n" +
            "      `student`,\n" +
            "      `test`\n" +
            "    WHERE\n" +
            "      `course`.`course_id` = `teach`.`course_id`\n" +
            "        AND `course`.`teacher_id` = `teacher`.`teacher_id`\n" +
            "        AND `teach`.`class_id` = `class`.`class_id`\n" +
            "        AND `teach`.`class_id` = `student`.`class_id`\n" +
            "        AND `teach`.`teach_id` = `test`.`teach_id`\n" +
            "        AND NOW() < `submit_time`\n" +
            "        AND NOW() > `test_time`\n" +
            "        AND `student`.`student_username` = #{username}\n" +
            "        AND `teacher_name` = #{teacherName}\n" +
            "        AND `course_name` = #{courseName}\n" +
            " AND `test`.`test_id` NOT IN (SELECT\n" +
            "      `test_id`\n" +
            "    FROM\n" +
            "      `test_score`,\n" +
            "      `student`\n" +
            "    WHERE\n" +
            "      `student_username` = #{username} AND `student`.`student_id` = `test_score`.`student_id`)" +
            "  ORDER BY `submit_time`")
    List<Map<String, Object>> getAllTestQuestionByUsernameAndCourseNameAndTeacherName(String username, String courseName, String teacherName);


    //选出所有与该课程相关的所有test信息
    //所有
    @Select("SELECT course.course_id,teach.teach_id,course.course_name,class.class_name,class.class_grade,class_number,class.class_id,submit_time,test_time,test_id,test_num\n" +
            "FROM class,course,teach\n" +
            "\tRIGHT JOIN test ON test.teach_id=teach.teach_id\n" +
            "WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id AND course.course_id=#{courseId}\n" +
            "ORDER BY submit_time DESC")
    List<Map<String,Object>> selectByCourseId(Integer courseId);
    //当前学期
    @Select("SELECT course.course_id,teach.teach_id,course.course_name,class.class_name,class.class_grade,class_number,class.class_id,submit_time,test_time,test_id,test_num\n" +
            "FROM class,course,teach\n" +
            "\tRIGHT JOIN test ON test.teach_id=teach.teach_id\n" +
            "WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id AND course.course_id=#{courseId}\n" +
            "AND teach.teach_time=CONCAT_WS(',',(SELECT YEAR(CURDATE())-class_grade+1),(SELECT IF((SELECT MONTH(CURDATE())>7),1,2)))\n" +
            "ORDER BY submit_time DESC")
    List<Map<String,Object>> selectTermByCourseId(Integer courseId);
}
