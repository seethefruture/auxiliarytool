package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.Class;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: ClassMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1422:42
 */
public interface ClassMapper {

    @Insert("insert into class (class_name,class_grade,class_number,faculty_id) values " +
            "(#{className},#{classGrade},#{classNumber},#{facultyId})")
    void insert(Class clas);

    @Select("select * from class where faculty_id = #{facultyId} and class_grade = #{classGrade}"
            + " and class_number = #{classNumber}")
    @Results({
            @Result(property = "classId", column = "class_id"),
            @Result(property = "className", column = "class_name"),
            @Result(property = "classGrade", column = "class_grade"),
            @Result(property = "classNumber", column = "class_number"),
            @Result(property = "facultyId", column = "faculty_id"),
    })
    Class queryClass1(int faculty_id, int class_grade, int class_number);


    @Select("select * from class where class_id = #{classId}")
    @Results({
            @Result(property = "classId", column = "class_id"),
            @Result(property = "className", column = "class_name"),
            @Result(property = "classGrade", column = "class_grade"),
            @Result(property = "classNumber", column = "class_number"),
            @Result(property = "facultyId", column = "faculty_id"),
    })
    Class queryClass2(int id);


    @Delete("delete from class where class_id = #{classId}")
    void deleteById(int id);

    @Select("select * from class")
    List<Class> queryAll();

    @Select("SELECT *\n" +
            "FROM class\n" +
            "WHERE class_name like CONCAT('%',#{classname},'%')")
    List<Class> selectClass(String classname);


    @Select("SELECT class_grade,class.class_id,class_name,class_number,faculty_name,count(student_id)AS num\n" +
            " FROM faculty,class LEFT JOIN student ON  student.class_id=class.class_id\n" +
            "            WHERE class.faculty_id=faculty.faculty_id\n" +
            "            AND college_id=#{id}\n" +
            "            GROUP BY class.class_id\n"+
            "            ORDER BY class_grade DESC")
    List<Map<String,Object>> selectByCollegeId(Integer id);

    @Update("UPDATE class\n" +
            "SET  class_name=#{name},faculty_id=#{facultyId},class_grade=#{grade},class_number=#{num}\n" +
            "WHERE class_id=#{id}")
    void update(String name,Integer facultyId,Integer grade,Integer num,Integer id);

    @Select("SELECT class_id\n" +
            "FROM class\n" +
            "WHERE class_grade=#{classGrade} AND class_name=#{className} AND class_number=#{classNumber}")
    int selectClass1(String className,int classNumber,int classGrade);



}
