package com.cxxy.edu.mapper;

import com.cxxy.edu.entity.TestScore;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: TestScoreMapper
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1413:22
 */
public interface TestScoreMapper {
    @Insert("insert into test_score (test_id,student_id,score) values (#{testId},#{studentId},#{score})")
    int insert(TestScore test_score);

    @Select("select * from test_score where test_score_id = #{testScoreId}")
    TestScore queryById(int id);

    //返回类型count是long，score是double
    @Select("SELECT count(test_id) AS num,sum(score) AS score\n" +
            "FROM test_score\n" +
            "WHERE test_id=#{testId}\n" +
            "GROUP BY test_id")
    Map<String,Object> selectByTestId(int testId);

    @Select("SELECT *\n" +
            "FROM test_score\n" +
            "WHERE test_id=#{testId}")
    List<TestScore> selectAllByTestId(int testId);

    @Select("SELECT\n" +
            "  `score`\n" +
            "FROM\n" +
            "  `test_score`,\n" +
            "  `student`\n" +
            "WHERE\n" +
            "  `test_score`.`student_id` = `student`.`student_id` AND `student`.`student_username` = #{username} AND `test_id` = #{test_id}")
    String queryScoreByTestId(String username,int test_id);
}
