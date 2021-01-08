package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.TestFalseAnswer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: TestFalseAnswerMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/5/2721:43
 */
public interface TestFalseAnswerMapper {
    @Insert("insert into test_false_answer (test_id,student_id,false_question_id,false_answer_choose) " +
            "values (#{testId},#{studentId},#{falseQuestionId},#{falseAnswerChoose})")
    int insert(TestFalseAnswer testFalseAnswer);

    @Select("select false_question_id,false_answer_choose from test_false_answer where student_id = #{student_id} and test_id = #{test_id}")
    List<Map<String,Object>> getFalseRecordByStudentIdAndTestId(int student_id, int test_id);
}
