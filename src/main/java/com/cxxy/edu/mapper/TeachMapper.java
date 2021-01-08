package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.Teach;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: TeachMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/2819:05
 */
public interface TeachMapper {

    @Insert("insert into teach (course_id,class_id,teach_time) values" +
            "(#{courseId},#{classId},#{teachTime})")
    void insert(Teach teach);

    @Select("select * from teach where class_id = #{classId}")
    List<Teach> queryByClassId(int classId);

//    @Select("SELECT teach_id \n" +
//            "FROM teach,course\n" +
//            "WHERE teach.course_id=course.course_id AND course_name=#{name} AND teacher_id=#{id}")
//    Integer selectByCourseNameAndTeacherId(String name,Integer id);

    //    @Select("SELECT *\n" +
//            "FROM teach\n" +
//            "WHERE course_id=#{courseId} AND teacher_id=#{teacherId}")
//    List<Teach> selectByCourseIdAndTeacherId(Integer courseId,Integer teacherId);
    @Select("SELECT class_id\n" +
            "FROM course,teach\n" +
            "WHERE course.course_id=teach.course_id AND course.course_id=#{courseId} AND teacher_id=#{teacherId}")
    List<Integer> selectByCourseIdAndTeacherId(Integer courseId, Integer teacherId);

    @Select("SELECT *\n" +
            "FROM teach\n" +
            "WHERE course_id=#{courseId} AND class_id=#{classId}")
    Teach selectTeach(String courseId, String classId);

    @Select("SELECT teach_id\n" +
            "FROM teach,course\n" +
            "WHERE teach.course_id=course.course_id AND course.course_id=#{courseId} AND class_id=#{classId}")
    Integer selectTeachId(Integer courseId, Integer classId);

    @Select("SELECT *\n" +
            "FROM teach,teacher,course,class\n" +
            "WHERE course.teacher_id=teacher.teacher_id AND teach.course_id=course.course_id \n" +
            "\t\t\tAND teach.class_id=class.class_id AND course_name LIKE CONCAT('%',#{courseName},'%') AND class_name LIKE CONCAT('%',#{className},'%')")
    List<Map<String, Object>> selectTeachMessage(String courseName, String className);

    @Select("SELECT *\n" +
            "FROM teach,teacher,course,class\n" +
            "WHERE teach.teacher_id=teacher.teacher_id AND teach.course_id=course.course_id \n" +
            "\t\t\tAND teach.class_id=class.class_id ")
    List<Map<String, Object>> selectAllTeachMessage();

    @Delete("DELETE \n" +
            "FROM teach\n" +
            "WHERE teach_id=#{teachId}")
    void delete(Integer teachId);

    //找到这学期上这门课的班级信息
    @Select("SELECT class.class_id,class_name,class_number,class_grade\n" +
            "FROM teach,class,course\n" +
            "WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id AND course.course_id=#{courseId} AND teacher_id=#{teacherId}\n" +
            " AND teach.teach_time=CONCAT_WS(',',(SELECT YEAR(CURDATE())-class_grade+1),(SELECT IF((SELECT MONTH(CURDATE())>7),1,2)))")
    List<Map<String, Object>> selectClassInformation(Integer courseId, Integer teacherId);

    @Select("SELECT *\n" +
            "FROM class,teacher,teach,course\n" +
            "WHERE teach.class_id=class.class_id AND teach.course_id=course.course_id AND course.teacher_id=teacher.teacher_id\n" +
            " AND teach_id=#{teachId}")
    Map<String,Object> selectByTeachId(Integer teachId);

    @Update("UPDATE teach\n" +
            "SET course_id =#{courseId} , class_id=#{classId} , teach_time=#{teachTime} \n" +
            "WHERE teach_id=#{teachId}")
    void update(Integer teachId,Integer classId,Integer courseId,String teachTime);


}
