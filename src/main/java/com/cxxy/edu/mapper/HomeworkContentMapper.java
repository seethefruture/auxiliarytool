package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.HomeworkContent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface HomeworkContentMapper {
    @Select("SELECT *\n" +
            "FROM homework_content\n" +
            "WHERE homework_question_id=#{homeworkQuestionId} AND student_id=#{studentId} AND homework_id=#{homeworkId}")
    HomeworkContent selectByHomeworkQuestionIdAndStudentId(Integer homeworkQuestionId, Integer studentId,Integer homeworkId);

    @Insert("insert into homework_content (student_id,homework_id,homework_question_id,homework_content_answer) values " +
            "(#{studentId},#{homeworkId},#{homeworkQuestionId},#{homeworkContentAnswer})")
    void insert(HomeworkContent homeworkContent);
}
