package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.Resource;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ResourceMapper {
    //查找自己的所有资源信息
    @Select("SELECT course_name,resource_chapter,resource_path,resource.resource_id,resource_sign,chapter_num,chapter_name\n" +
            "FROM resource,course,chapter\n" +
            "WHERE resource.course_id=course.course_id AND teacher_id=#{id} AND course.course_id=#{courseId} AND chapter.chapter_id=resource.resource_chapter\n" +
            "ORDER BY resource.course_id,chapter_num")
    List<Map<String, Object>> selectResourceBySelf(Integer id,Integer courseId);

    //查找公共资源
    @Select("SELECT course_name\n" +
            "FROM resource,course\n" +
            "WHERE resource.course_id=course.course_id AND resource_secrecy=1\n" +
            "GROUP BY resource.course_id")
    List<String> selectAllCourseName();

    @Select("SELECT course_name,resource_chapter,resource_path,teacher_name\n" +
            "FROM resource,course,teacher\n" +
            "WHERE resource.course_id=course.course_id AND course.teacher_id=teacher.teacher_id " +
            "AND resource_secrecy=1 AND course_name=#{course_name}\n" +
            "ORDER BY resource.course_id,resource_chapter")
    List<Map<String, Object>> selectResourceByPublic(String course_name);

    //删除该资源
    @Delete("DELETE FROM resource WHERE resource_id=#{resourceId}")
    void deleteByResourceId(Integer resourceId);

    //增加资源
    @Insert("INSERT INTO resource(course_id,resource_path,resource_chapter,resource_secrecy,resource_sign) " +
            "VALUES(#{courseId},#{resourcePath},#{resourceChapter},#{resourceSecrecy},#{resourceSign})")
    void insertResource(Resource resource);

    @Select("SELECT\n" +
            "  `resource_path`, `resource_chapter`,`resource_id`\n" +
            "FROM\n" +
            "  `resource`\n" +
            "WHERE\n" +
            "  `resource_id` IN (SELECT\n" +
            "    `resource_id`\n" +
            "  FROM\n" +
            "    `teacher`,\n" +
            "    `resource`,\n" +
            "    `course`\n" +
            "  WHERE\n" +
            "    `teacher_name` = #{teacherName}" +
            "      AND course.teacher_id = teacher.teacher_id " +
            "      AND resource.course_id = course.course_id " +
            "      AND `course_name` = #{courseName})" +
            "      AND `resource_secrecy` = '1'" +
            "    order by `resource_chapter`")
    List<Map<String, Object>> selectPublicResourceByCourseNameAndTeacherName(String teacherName, String courseName);

    @Select("SELECT\n" +
            "`resource_chapter`\n" +
            "FROM\n" +
            "  `resource`\n" +
            "WHERE\n" +
            "  `resource_id` IN (SELECT\n" +
            "    `resource_id`\n" +
            "  FROM\n" +
            "    `resource`,\n" +
            "    `teacher`,\n" +
            "    `course`\n" +
            "  WHERE\n" +
            "      `teacher_name` = #{teacherName}" +
            "      AND `course_name` = #{courseName} " +
            "      AND course.teacher_id = teacher.teacher_id " +
            "      AND `resource`.`course_id` = `course`.`course_id`)" +
            "    group by `resource_chapter`" +
            "    order by `resource_chapter`")
    List<Map<String, Object>> selectResourceChapterByCourseNameAndTeacherName(String teacherName, String courseName);

    @Select("SELECT\n" +
            "`resource_path`\n" +
            "FROM\n" +
            "  `resource`\n" +
            "WHERE\n" +
            "  `resource_id` IN (SELECT\n" +
            "    `resource_id`\n" +
            "  FROM\n" +
            "    `resource`,\n" +
            "    `teacher`,\n" +
            "    `course`\n" +
            "  WHERE\n" +
            "    `resource`.`course_id` = `course`.`course_id`" +
            "      AND `teacher_name` = #{teacherName}" +
            "      AND `course_name` = #{courseName}" +
            "      AND course.teacher_id = teacher.teacher_id " +
            "      AND `resource_chapter` = #{chapter})")
    List<Map<String, Object>> selectAllResourcePathByCourseNameAndTeacherNameAndChapter(String teacherName, String courseName, String chapter);

    @Select("SELECT\n" +
            "`resource_path`,`resource_chapter`,`resource_id`\n" +
            "FROM\n" +
            "  `resource`\n" +
            "WHERE\n" +
            "  `resource_id` IN (SELECT\n" +
            "    `resource_id`\n" +
            "  FROM\n" +
            "    `teacher`,\n" +
            "    `course`,\n" +
            "    `resource`\n" +
            "  WHERE\n" +
            "      `teacher_name` = #{teacherName}" +
            "      AND `course_name` = #{courseName}" +
            "      AND `resource_chapter` = #{chapter}" +
            "      AND course.teacher_id = teacher.teacher_id " +
            "      AND `resource`.`course_id` = `course`.`course_id`)" +
            "    order by `resource_chapter`")
    List<Map<String, Object>> selectResourceByCourseNameAndTeacherNameAndChapter(String teacherName, String courseName, String chapter);

    @Select("SELECT\n" +
            "  `resource_path`, `resource_chapter`\n" +
            "FROM\n" +
            "  `resource`\n" +
            "WHERE\n" +
            "  `resource_id` IN (SELECT\n" +
            "    `resource_id`\n" +
            "  FROM\n" +
            "    `teacher`,\n" +
            "    `course`,\n" +
            "    `resource`,\n" +
            "  WHERE\n" +
            "    `resource`.`course_id` = `course`.`course_id`" +
            "      AND course.teacher_id = teacher.teacher_id " +
            "      AND `teacher_name` = #{teacherName}" +
            "      AND `course_name` = #{courseName})" +
            "      AND `resource_secrecy` = '0'")
    List<Map<String, Object>> selectPrivateResourceByCourseNameAndTeacherName(String teacherName, String courseName);


    @Select("select \n" +
            "course_name,\n" +
            "teacher_name\n" +
            "from \n" +
            "resource,\n" +
            "course,\n" +
            "teacher\n" +
            "where \n" +
            "resource_secrecy=1\n" +
            "and resource.course_id=course.course_id\n" +
            "and teacher.teacher_id=course.teacher_id\n" +
            "GROUP BY\n" +
            "\tcourse_name,\n" +
            "\tteacher_name " +
            "ORDER BY course_name")
    List<Map<String, Object>> selectAllPublicCourseInfo();

    @Select("SELECT\n" +
            "\tresource_path \n" +
            "FROM\n" +
            "\tresource \n" +
            "WHERE\n" +
            "\tresource_id = #{resourceId}")
    Map<String, String> selectResourcePathByCourseId(String resourceId);
}
