package com.cxxy.edu.mapper;


import com.cxxy.edu.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: AdministratorMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1317:27
 */
public interface AdministratorMapper {

    @Update("update admin set admin_name=#{adminName},admin_password=#{adminPassword}"
            + ",admin_level=#{adminLevel},college_id=#{collegeId},admin_username=#{adminUsername} where admin_id=#{adminId}")
    void updateByAdminId(Admin administrator);

    @Update("update admin set admin_name=#{name},admin_password=#{password}"
            + ",admin_username=#{userName} where admin_id=#{id}")
    void update(String userName, String password,String name,Integer id);

    @Select("select * from admin where admin_username= #{adminUsername}")
    @Results({
            @Result(id = true, property = "adminId", column = "admin_id"),
            @Result(property = "adminUsername", column = "admin_username"),
            @Result(property = "adminName", column = "admin_name"),
            @Result(property = "adminPassword", column = "admin_password"),
            @Result(property = "adminLevel", column = "admin_level"),
            @Result(property = "collegeId", column = "college_id"),
    })
    Admin queryByUserName(String username);

    @Insert("insert into admin (admin_username,admin_name,admin_password,admin_level,college_id)values (#{adminUsername},"
            + "#{adminName},#{adminPassword},#{adminLevel},#{collegeId})")
    void insert(Admin administrator);

    @Select("SELECT *\n" +
            "FROM admin\n" +
            "WHERE admin_username=#{username} AND admin_password=#{password}")
    Admin selectByUsernameAndPassword(String username,String password);

    @Select("select *\n" +
            "from admin,college\n" +
            "WHERE admin.college_id=college.college_id")
    List<Map<String,Object>> selectAdminAndCollege();

    @Delete("DELETE\n" +
            "FROM admin\n" +
            "WHERE admin_id=#{adminId}")
    void deleteByAdminId(Integer adminId);

    @Select("SELECT *\n" +
            "from college,admin\n" +
            "WHERE college.college_id=admin.college_id AND admin_id=#{adminId}")
    Map<String,Object> selectAdminAndCollegeByAdminId(Integer adminId);

    @Select("SELECT *\n" +
            "FROM admin\n" +
            "WHERE admin_id=#{adminId}")
    Admin selectByAdminId(Integer adminId);



}
