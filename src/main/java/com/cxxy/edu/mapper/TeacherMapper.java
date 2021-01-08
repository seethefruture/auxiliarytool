package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface TeacherMapper {
    //根据教师登录时的用户名和密码查找
    @Select("select * from teacher where teacher_username=#{username} and teacher_password=#{password} ")
    Teacher selectByUsernameAndPassword(String username, String password);

    //修改账户信息，只允许修改姓名和密码，用户名不能修改
    @Update("UPDATE teacher SET teacher_name=#{name} , teacher_password=#{password} " +
            "WHERE teacher_id=#{id}")
    void updateById(String name, String password, Integer id);

    //查询所教的课程信息，包括班级，年级，课程名  此处只查询教师所教课程，并不包括班级信息。 需改动
//    @Select("SELECT course.course_name,class.class_name,class.class_grade,class_number,class.class_id,course.course_id\n" +
//            "FROM teach,class,course\n" +
//            "WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id AND course.teacher_id=#{id} AND teach.teach_time=#{time} AND class.class_grade=#{grade}\n" +
//            "GROUP BY class_grade\n")
//    List<Map<String, Object>> selectByTeachId(Integer id,String time,int grade);
    @Select("SELECT course.course_name,course.course_id\n" +
            "FROM teach,class,course\n" +
            "WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id\n" +
            " AND course.teacher_id=#{id} \n" +
            " AND teach.teach_time=CONCAT_WS(',',(SELECT YEAR(CURDATE())-class_grade+1),(SELECT IF((SELECT MONTH(CURDATE())>7),1,2)))\n" +
            "GROUP BY class_grade")
    List<Map<String, Object>> selectByTeachIdByTerm(Integer id);

    @Select("SELECT course.course_name,course.course_id\n" +
            "FROM teach,class,course\n" +
            "WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id\n" +
            "AND course.teacher_id=#{id} AND teach.teach_time=CONCAT_WS(',',(SELECT YEAR(CURDATE())-class_grade+1),(SELECT IF((SELECT MONTH(CURDATE())>7),1,2)))\n" +
            "GROUP BY course.course_id\n")
    List<Map<String, Object>> selectByTeachId(Integer id);

    @Select("SELECT course_name,course.course_id\n" +
            "FROM teach RIGHT JOIN course ON teach.course_id=course.course_id\n" +
            "WHERE teacher_id=#{teacherId}\n" +
            "GROUP BY teach.course_id")
    List<Map<String,Object>> selectAllCourseByTeacherId(Integer teacherId);

//    //有限制学期
//    @Select("SELECT course.course_id,teach.teach_id,course.course_name,class.class_name,class.class_grade,class_number,class.class_id\n" +
//            "            FROM teach,class,course\n" +
//            "            WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id\n" +
//            "            AND course.teacher_id=#{id}\n" +
//            "            AND teach.teach_time=CONCAT_WS(',',(SELECT YEAR(CURDATE())-class_grade+1),(SELECT IF((SELECT MONTH(CURDATE())>7),1,2)))")
//    List<Map<String, Object>> selectTeachByTeacherIdByTerm(Integer id);
//
    @Select("SELECT course.course_id,teach.teach_id,course.course_name,class.class_name,class.class_grade,class_number,class.class_id\n" +
            "            FROM teach,class,course\n" +
            "            WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id\n" +
            "            AND course.teacher_id=#{id}\n" )
    List<Map<String, Object>> selectTeachByTeacherId(Integer id);

    @Select("SELECT course.course_id,teach.teach_id,course.course_name,class.class_name,class.class_grade,class_number,class.class_id,homework_chapter,submit_time,homework_id\n" +
            "FROM class,course,teach\n" +
            "\tRIGHT JOIN homework ON homework.teach_id=teach.teach_id\n" +
            "WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id AND course.course_id=#{courseId}\n"+
            "ORDER BY submit_time DESC")
    List<Map<String,Object>> selectByCourseId(Integer courseId);

    @Select("SELECT course.course_id,teach.teach_id,course.course_name,class.class_name,class.class_grade,class_number,class.class_id,homework_chapter,submit_time,homework_id\n" +
            "FROM class,course,teach\n" +
            "\tRIGHT JOIN homework ON homework.teach_id=teach.teach_id\n" +
            "WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id AND course.course_id=#{courseId}\n"+
            "AND teach.teach_time=CONCAT_WS(',',(SELECT YEAR(CURDATE())-class_grade+1),(SELECT IF((SELECT MONTH(CURDATE())>7),1,2)))\n" +
            "ORDER BY submit_time DESC")
    List<Map<String,Object>> selectByCourseIdByTerm(Integer courseId);

    //查询教师所教课程的所有班级 当前学期
    @Select("SELECT course.course_name,class.class_name,class.class_grade,class_number,class.class_id,course.course_id,teach_id\n" +
            "FROM teach,class,course\n" +
            "WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id AND course.teacher_id=#{id} AND teach.teach_time=#{time} AND class.class_grade=#{grade}\n")
    List<Map<String, Object>> selectTeachIdByTeacherIdAndTimeAndGrade(Integer id,String time,int grade);

    @Select("SELECT *\n" +
            "FROM teacher")
    List<Teacher> selectAll();

    @Select("SELECT *\n" +
            "FROM teacher\n" +
            "WHERE teacher_name like CONCAT('%',#{name},'%')")
    List<Teacher> selectByTeacherName(String name);

    @Insert("INSERT into teacher(teacher_username,teacher_name,teacher_password) \n" +
            "VALUES(#{teacherUsername},#{teacherName},#{teacherPassword})")
    void insertTeacher(Teacher teacher);

    @Delete("DELETE \n" +
            "FROM teacher\n" +
            "WHERE teacher_id=#{id}")
    void deleteByTeacherId(Integer id);

    @Select("SELECT *\n" +
            "FROM teacher\n" +
            "WHERE teacher_id=#{id}")
    Teacher selectByTeacherId(Integer id);

    @Update("UPDATE teacher SET teacher_name=#{name} , teacher_username=#{username} " +
            "WHERE teacher_id=#{id}")
    void updateByTeacherId(String name, String username, Integer id);

    //根据教师id找到对应的课程的班级的所有的入学年份
    @Select("SELECT class_grade\n" +
            "FROM class,course,teach\n" +
            "WHERE class.class_id=teach.class_id AND teach.course_id=course.course_id AND teacher_id=#{teacherId}\n" +
            "GROUP BY class_grade")
    List<Integer> selectGradeByTeacherId(int teacherId);

    @Select("SELECT teacher_id\n" +
            "FROM teacher\n" +
            "WHERE teacher_name=#{name}")
    List<Integer> selectByTeacherNameOnly(String name);

    @Select("SELECT teacher_id\n" +
            "FROM teacher\n" +
            "WHERE teacher_username=#{name}")
    Integer selectByTeacherUsername(String name);






}
