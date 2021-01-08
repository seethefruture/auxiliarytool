package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.HomeworkQuestion;
import com.cxxy.edu.entity.HomeworkScore;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: HomeWord_ScoreMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1323:22
 */
public interface HomeworkScoreMapper {
    @Insert("insert into homework_score (homework_id,student_id,score,submit_time) values" +
            "(#{homeworkId},#{studentId},#{score},#{submitTime})")
    void insert(HomeworkScore homeWork_score);

    @Select("select * from homework_score where homework_score_id = #{homeworkScoreId}")
    @Results({
            @Result(property = "homeworkId", column = "homework_id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "score", column = "score"),
            @Result(property = "submitTime", column = "submit_time"),
    })
    HomeworkScore queryById(int id);

    //统计已回答的人数
    @Select("SELECT count(homework_id)\n" +
            "FROM homework_score\n" +
            "WHERE homework_id=#{homeworkId}\n" +
            "GROUP BY homework_id\n")
    Long selectByHomeworkId(int homeworkId);

    //统计已批改的人数
    @Select("SELECT count(homework_id)\n" +
            "FROM homework_score\n" +
            "WHERE homework_id=#{homeworkId} AND score!=\"\"\n" +
            "GROUP BY homework_id")
    Long selectCount(int homeworkId);

    @Select("SELECT *\n" +
            "FROM homework_score\n" +
            "WHERE homework_id=#{homeworkId}\n")
    List<HomeworkScore> selectByQuestionId(Integer homeworkId);

    @Select("SELECT *\n" +
            "FROM homework_score\n" +
            "WHERE homework_id=#{homeworkId} AND student_id=#{studentId}")
    HomeworkScore selectByHomeworkIdAndStudentId(Integer homeworkId, Integer studentId);

    @Update("UPDATE homework_score\n" +
            "SET score=#{score}\n" +
            "WHERE homework_score_id=#{homeworkScoreId}")
    void updateByHomeworkScoreId(Integer homeworkScoreId, String score);

    @Select("SELECT\n" +
            "  `score`\n" +
            "FROM\n" +
            "  `homework_score`,\n" +
            "  `student`\n" +
            "WHERE\n" +
            "  `homework_score`.`student_id` = `student`.`student_id` AND `student`.`student_username` = #{username} AND `homework_score_id` = #{homeworkId}")
    String queryScoreByTestId(String username, int homeworkId);

    //统计该questionId下的学生的回答情况
    @Select("SELECT (SELECT student_no FROM student WHERE student_id=homework_score.student_id)student_no,\n" +
            "\t(SELECT student_name FROM student WHERE student_id=homework_score.student_id)student_name,\n" +
            "\thomework_score.student_id,IFNULL(score,\"\")score,homework_score_id,homework_score.submit_time,homework.homework_id\n" +
            "FROM teach,homework,class,homework_score,student\n" +
            "WHERE teach.teach_id=homework.teach_id AND teach.class_id=class.class_id\n" +
            "\tAND homework.homework_id=16 AND class.class_id=student.class_id AND homework.homework_id=homework_score.homework_id\n" +
            "GROUP BY homework_score.student_id")
    List<Map<String,Object>> selectAnswerStudentByQuestionId(int homeworkId);
}
