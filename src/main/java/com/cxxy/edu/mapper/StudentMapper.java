package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: StudentMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1322:40
 */
public interface StudentMapper {
    @Insert("insert into student (student_no,student_username,student_password,student_name,class_id) values "
            + "(#{studentNo},#{studentUsername},#{studentPassword},#{studentName},#{classId})")
    void insert(Student student);

    @Delete("deleteByName from student where student_username=#{studentUsername}")
    void delete(String username);

    @Select("select * from student where student_id = #{studentId}")
    Student queryById(int id);

    @Select("select * from student where student_username=#{student_Username}")
    Student queryByUsername(String username);

    /**
     * 某位学生的全部课程待完成作业信息
     *
     * @param username
     * @return
     */
    @Select("SELECT\n" +
            "\t`student_name`,\n" +
            "\t`course_name`,\n" +
            "\t`teacher_name`,\n" +
            "\t`class_name`,\n" +
            "\t`class_grade`,\n" +
            "\t`class_number`,\n" +
            "\tCOUNT( `homework_id` ) AS 'count' \n" +
            "FROM\n" +
            "\t`student`,\n" +
            "\t`course`,\n" +
            "\t`teacher`,\n" +
            "\t`class`,\n" +
            "\t`teach`,\n" +
            "\t`homework` \n" +
            "WHERE\n" +
            "\t`student`.`class_id` = `class`.`class_id` \n" +
            "\tAND `class`.`class_id` = `teach`.`class_id` \n" +
            "\tAND `teach`.`course_id` = `course`.`course_id` \n" +
            "\tAND `teacher`.`teacher_id` = `course`.`teacher_id` \n" +
            "\tAND `teach`.`teach_id` = `homework`.`teach_id` \n" +
            "\tAND `student`.`student_username` = #{username} \n" +
            "\tAND `teach_time` = CONCAT_WS(\n" +
            "\t',',\n" +
            "\t( SELECT YEAR ( CURDATE( ) ) +1 - class_grade ),\n" +
            "\t( SELECT IF ( ( SELECT MONTH ( CURDATE( ) ) > 7 ), 1, 2 ) ) \n" +
            "\t) \n" +
            "\tAND NOW( ) < `homework`.`submit_time` \n" +
            "\tAND `homework`.`homework_id` NOT IN (\n" +
            "SELECT\n" +
            "\t`homework`.`homework_id` \n" +
            "FROM\n" +
            "\t`homework`,\n" +
            "\t`homework_score`,\n" +
            "\t`student` \n" +
            "WHERE\n" +
            "\t`homework`.`homework_id` = `homework_score`.`homework_id` \n" +
            "\tAND `student`.`student_id` = `homework_score`.`student_id` \n" +
            "\tAND `student_username` =#{username} \n" +
            "\t) \n" +
            "GROUP BY\n" +
            "\t`teach`.`teach_id`")
    List<Map<String, Object>> getAllCourseForHomeWorkByUsername(String username);

    /**
     * 某位学生的全部课程历史作业信息
     *
     * @param username
     * @return
     */
    @Select("SELECT\n" +
            "\t`course_name`,\n" +
            "\t`teacher_name`,\n" +
            "\t`class_grade`,\n" +
            "\t`class_name`,\n" +
            "\t`class_number`,\n" +
            "\t`student_name`,\n" +
            "\tCOUNT( `homework_id` ) AS 'count' \n" +
            "FROM\n" +
            "\t`teach`,\n" +
            "\t`course`,\n" +
            "\t`teacher`,\n" +
            "\t`class`,\n" +
            "\t`student`,\n" +
            "\t`homework` \n" +
            "WHERE\n" +
            "\t`course`.`course_id` = `teach`.`course_id` \n" +
            "\tAND `teach`.`course_id` = `course`.`course_id` \n" +
            "\tAND `course`.`teacher_id` = `teacher`.`teacher_id` \n" +
            "\tAND `teach`.`class_id` = `class`.`class_id` \n" +
            "\tAND `teach`.`class_id` = `student`.`class_id` \n" +
            "\tAND `teach`.`teach_id` = `homework`.`teach_id` \n" +
            "\tAND `student`.`student_username` = #{username} \n" +
            "\tAND `teach_time` = CONCAT_WS(\n" +
            "\t',',\n" +
            "\t( SELECT YEAR ( CURDATE( ) ) +1 - class_grade ),\n" +
            "\t( SELECT IF ( ( SELECT MONTH ( CURDATE( ) ) > 7 ), 1, 2 ) ) \n" +
            "\t) \n" +
            "\tAND (\n" +
            "\tNOW( ) > `submit_time` \n" +
            "\tOR (\n" +
            "\tNOW( ) < `submit_time` \n" +
            "\tAND `homework`.`homework_id` IN ( SELECT `homework_id` FROM `homework_score`, `student` WHERE `student_username` = #{username} AND `student`.`student_id` = `homework_score`.`student_id` ) \n" +
            "\t) \n" +
            "\t) \n" +
            "GROUP BY\n" +
            "\t`teach`.`teach_id`")
    List<Map<String, Object>> getAllCourseForHistoryHomeWorkByUsername(String username);

    /**
     * 某位学生的全部课程待完成测试信息
     *
     * @param username
     * @return
     */
    @Select("SELECT\n" +
            "`course_name`,\n" +
            "`teacher_name`,\n" +
            "`class_grade`,\n" +
            "`class_name`,\n" +
            "`class_number`,\n" +
            "`student_name`,\n" +
            "COUNT( `test_id` ) AS 'count' \n" +
            "FROM\n" +
            "\t`teach`,\n" +
            "\t`course`,\n" +
            "\t`teacher`,\n" +
            "\t`class`,\n" +
            "\t`student`,\n" +
            "\t`test` \n" +
            "WHERE\n" +
            "\t`course`.`course_id` = `teach`.`course_id` \n" +
            "\tAND `teach`.`course_id` = `course`.`course_id` \n" +
            "\tAND `course`.`teacher_id` = `teacher`.`teacher_id` \n" +
            "\tAND `teach`.`class_id` = `class`.`class_id` \n" +
            "\tAND `teach`.`class_id` = `student`.`class_id` \n" +
            "\tAND `teach`.`teach_id` = `test`.`teach_id` \n" +
            "\tAND NOW( ) > `test_time` \n" +
            "\tAND NOW( ) < `submit_time` \n" +
            "\tAND `student`.`student_username` = #{username} \n" +
            "\tAND `teach_time` = CONCAT_WS(\n" +
            "\t',',\n" +
            "\t( SELECT YEAR ( CURDATE( ) ) +1 - class_grade ),\n" +
            "\t( SELECT IF ( ( SELECT MONTH ( CURDATE( ) ) > 7 ), 1, 2 ) ) \n" +
            "\t) \n" +
            "\tAND `test`.`test_id` NOT IN ( SELECT `test_id` FROM `test_score`, `student` WHERE `student_username` = #{username} AND `student`.`student_id` = `test_score`.`student_id` ) \n" +
            "GROUP BY\n" +
            "\t`course`.`course_id`")
    List<Map<String, Object>> getAllCourseForTestByUsername(String username);


    /**
     * 某位学生的全部课程历史测试信息
     *
     * @param username
     * @return
     */
    @Select("SELECT\n" +
            "`course_name`,\n" +
            "`teacher_name`,\n" +
            "`class_grade`,\n" +
            "`class_name`,\n" +
            "`class_number`,\n" +
            "`student_name`,\n" +
            "COUNT( `test_id` ) AS 'count' \n" +
            "FROM\n" +
            "\t`teach`,\n" +
            "\t`course`,\n" +
            "\t`teacher`,\n" +
            "\t`class`,\n" +
            "\t`student`,\n" +
            "\t`test` \n" +
            "WHERE\n" +
            "\t`course`.`course_id` = `teach`.`course_id` \n" +
            "\tAND `teach`.`course_id` = `course`.`course_id` \n" +
            "\tAND `course`.`teacher_id` = `teacher`.`teacher_id` \n" +
            "\tAND `teach`.`class_id` = `class`.`class_id` \n" +
            "\tAND `teach`.`class_id` = `student`.`class_id` \n" +
            "\tAND `teach`.`teach_id` = `test`.`teach_id` \n" +
            "\tAND `student`.`student_username` = #{username} \n" +
            "\tAND `teach_time` = CONCAT_WS(\n" +
            "\t',',\n" +
            "\t( SELECT YEAR ( CURDATE( ) ) +1 - class_grade ),\n" +
            "\t( SELECT IF ( ( SELECT MONTH ( CURDATE( ) ) > 7 ), 1, 2 ) ) \n" +
            "\t) \n" +
            "\tAND (\n" +
            "\tNOW( ) > `submit_time` \n" +
            "\tOR (\n" +
            "\tNOW( ) > `test_time` \n" +
            "\tAND NOW( ) < `submit_time` \n" +
            "\tAND `test`.`test_id` IN ( SELECT `test_id` FROM `test_score`, `student` WHERE `student_username` = #{username} AND `student`.`student_id` = `test_score`.`student_id` ) \n" +
            "\t) \n" +
            "\t) \n" +
            "GROUP BY\n" +
            "\t`teach`.`teach_id`")
    List<Map<String, Object>> getAllCourseForHistoryTestByUsername(String username);

    @Select("SELECT\n" +
            "  `course_name`,\n" +
            "  `teacher_name`,\n" +
            "  `class_grade`,\n" +
            "  `class_name`,\n" +
            "  `class_number`,\n" +
            "  `student_name`,\n" +
            "  `teach_time`\n" +
            "FROM\n" +
            "  `teach`,\n" +
            "  `course`,\n" +
            "  `teacher`,\n" +
            "  `class`,\n" +
            "  `student`\n" +
            "WHERE\n" +
            "  `course`.`course_id` = `teach`.`course_id`\n" +
            "    AND `teach`.`course_id` = `course`.`course_id`\n" +
            "    AND `course`.`teacher_id` = `teacher`.`teacher_id`\n" +
            "    AND `teach`.`class_id` = `class`.`class_id`\n" +
            "    AND `teach`.`class_id` = `student`.`class_id`\n" +
            "    AND `student`.`student_username` = #{username}" +
            "\tAND `teach_time` = CONCAT_WS(\n" +
            "\t',',\n" +
            "\t( SELECT YEAR ( CURDATE( ) ) +1 - class_grade ),\n" +
            "\t( SELECT IF ( ( SELECT MONTH ( CURDATE( ) ) > 7 ), 1, 2 ) ) \n" +
            "\t) \n" +
            "GROUP BY\n" +
            "   `teach`.`teach_id`")
    List<Map<String, Object>> getAllCourseByUsername(String username);

    @Select("SELECT\n" +
            "  `course_name`,\n" +
            "  `teacher_name`,\n" +
            "  `class_grade`,\n" +
            "  `class_name`,\n" +
            "  `class_number`,\n" +
            "  `student_name`,\n" +
            "  `teach_time`\n" +
            "FROM\n" +
            "  `teach`,\n" +
            "  `course`,\n" +
            "  `teacher`,\n" +
            "  `class`,\n" +
            "  `student`\n" +
            "WHERE\n" +
            "  `course`.`course_id` = `teach`.`course_id`\n" +
            "    AND `course`.`teacher_id` = `teacher`.`teacher_id`\n" +
            "    AND `teach`.`class_id` = `class`.`class_id`\n" +
            "    AND `teach`.`class_id` = `student`.`class_id`\n" +
            "    AND `student`.`student_username` = #{username}" +
            "\tAND `teach_time` != CONCAT_WS(\n" +
            "\t',',\n" +
            "\t( SELECT YEAR ( CURDATE( ) ) +1 - class_grade ),\n" +
            "\t( SELECT IF ( ( SELECT MONTH ( CURDATE( ) ) > 7 ), 1, 2 ) ) \n" +
            "\t) \n" +
            "GROUP BY\n" +
            "   `teach`.`teach_id`")
    List<Map<String, Object>> getOtherCourseByUsername(String username);

    //统计该班级所有人数
    @Select("SELECT count(class_id) as num\n" +
            "FROM student\n" +
            "WHERE class_id=#{classId}\n" +
            "ORDER BY class_id")
    Long selectByClassId(Integer classId);

    //查询该班级的学生
    @Select("SELECT *\n" +
            "FROM student\n" +
            "WHERE class_id=#{classId}\n"+
            "ORDER BY student_no")
    List<Student> selectStudentByClassId(int classId);

}
