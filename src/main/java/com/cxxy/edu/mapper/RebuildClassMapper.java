package com.cxxy.edu.mapper;

import org.apache.ibatis.annotations.Select;

public interface RebuildClassMapper {
    //统计该课程重修班人的总人数
    @Select("SELECT count(teach_id) as num\n" +
            "FROM rebuild_class\n" +
            "WHERE teach_id=#{teachId}\n" +
            "ORDER BY teach_id")
    Long selectCountNum(Integer teachId);
}
