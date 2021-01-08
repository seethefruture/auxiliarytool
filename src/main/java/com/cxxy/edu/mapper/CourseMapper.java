package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: CourseMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1319:58
 */
public interface CourseMapper {
    @Insert("insert into course (course_name,teacher_id) values (#{courseName},#{teacherId})")
    void insert(String courseName,Integer teacherId);

    @Delete("deleteByName from course where course_name = #{courseName}")
    void delete(String name);

    @Delete("delete from course where course_id=#{id}")
    void deleteById(int id);

    @Select("select * from course where course_id = #{courseId}")
    Course queryById(int id);

    @Select("SELECT course_name,teacher_name,course_id\n" +
            "FROM course,teacher\n" +
            "WHERE course.teacher_id=teacher.teacher_id AND course_id=#{courseId}")
    Map<String,Object> selectByCourseId(Integer courseId);

//    @Select("select * from course")
//    List<Course> selectAll();

    @Select("SELECT course_id,course_name,teacher_name\n" +
            "FROM course,teacher\n" +
            "WHERE course.teacher_id=teacher.teacher_id")
    List<Map<String,Object>> selectAll();

    @Select("SELECT course_id,course_name,teacher_name\n" +
            "            FROM course,teacher\n" +
            "            WHERE course.teacher_id=teacher.teacher_id AND teacher_name LIKE CONCAT('%',#{teacherName},'%') AND course_name LIKE CONCAT('%',#{courseName},'%')")
    List<Map<String,Object>> selectByCourseNameORTeacherName(String teacherName,String courseName);

    @Update("UPDATE course\n" +
            "SET course_name=#{name} , teacher_id=#{teacherId}\n" +
            "WHERE course_id=#{id}")
    void update(Integer id,String name,int teacherId);

    @Select("SELECT *\n" +
            "FROM course\n" +
            "WHERE course_name like CONCAT('%',#{courseName},'%')")
    List<Course> selectByCourseName(String courseName);

    @Select("SELECT course_id \n" +
            "FROM course\n" +
            "WHERE course_name=#{name} AND teacher_id=#{id}")
    Integer selectByCourseNameAndTeacherId(String name,Integer id);

    @Select("SELECT teacher.teacher_id\n" +
            "FROM teacher,course\n" +
            "WHERE teacher.teacher_id = course.teacher_id AND teacher_username=#{username} AND course_name=#{courseName}")
    Integer selectByCourseNameAndUsername(String courseName,String username);

    @Select("SELECT course_id\n" +
            "FROM course,teacher\n" +
            "WHERE course.teacher_id=teacher.teacher_id AND course_name=#{courseName} AND teacher_name=#{teacherName}")
    Integer selectByCourseNameAndTeacherName(String courseName,String teacherName);

}
