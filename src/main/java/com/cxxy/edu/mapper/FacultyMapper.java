package com.cxxy.edu.mapper;


import com.cxxy.edu.entity.Faculty;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Administrator
 * @title: FacultyMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1320:06
 */
public interface FacultyMapper {
    @Insert("insert into faculty (faculty_name,college_id) values (#{facultyName},#{collegeId})")
    void insert(Faculty faculty);

    @Delete("delete from faculty where faculty_name = #{facultyName}")
    void delete(String name);

    @Delete("delete from faculty where faculty_id = #{id}")
    void deleteById(Integer id);

    @Select("select * from faculty where faculty_id = #{facultyId}")
    @Results({
            @Result(property = "facultyName",column = "faculty_name"),
            @Result(property = "collegeId",column = "college_id")
    })
    Faculty queryById(int id);

    @Select("SELECT *\n" +
            "FROM faculty\n" +
            "WHERE college_id=#{id}")
    List<Faculty> selectByCollegeId(Integer id);

    @Update("UPDATE faculty\n" +
            "SET faculty_name=#{name}\n" +
            "WHERE faculty_id=#{id}")
    void update(String name,Integer id);
}
