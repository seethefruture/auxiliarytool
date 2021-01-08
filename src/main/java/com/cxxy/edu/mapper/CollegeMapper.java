package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.College;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Administrator
 * @title: CollegeMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1319:42
 */
public interface CollegeMapper {

    @Insert("insert into college (college_name) values (#{collegeName})")
    void insert(College college);

    @Delete("delete from college where college_name=#{collegeName}")
    void deleteByName(String name);

    @Delete("delete from college where college_id=#{id}")
    void deleteById(int id);

    @Select("select * from college where college_id=#{collegeId}")
    @Results({
            @Result(property = "collegeName", column = "college_name")
    })
    College queryById(int id);

    @Select("SELECT *\n" +
            "FROM college")
    List<College> selectAll();

    @Update("UPDATE college\n" +
            "SET college_name=#{name}\n" +
            "WHERE college_id=#{id}")
    void update(Integer id,String name);

}
