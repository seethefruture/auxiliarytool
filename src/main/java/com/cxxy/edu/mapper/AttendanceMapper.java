package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.Attendance;
import org.apache.ibatis.annotations.*;

/**
 * @author Administrator
 * @title: AttendanceMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1323:02
 */
public interface AttendanceMapper {

    @Insert("insert into attendance (tech_id,date,lesson_num,student_id,attend_tag) values "
            +"(#{techId},#{date},#{lessonNum},#{studentId},#{attendTag})")
    void insert(Attendance attendance);

    @Delete("delete from attendance where attendance_id = #{attendanceId}")
    void deleteById(int id);

    @Select("select * from attendance where attendance_id = #{attendanceId}")
    @Results({
            @Result(property = "attendanceId",column = "attendance_id"),
            @Result(property = "techId",column = "tech_id"),
            @Result(property = "date",column = "date"),
            @Result(property = "lessonNum",column = "lesson_num"),
            @Result(property = "studentId",column = "student_id"),
            @Result(property = "attendTag",column = "attend_tag"),
    })
    Attendance queryById(int id);
}
