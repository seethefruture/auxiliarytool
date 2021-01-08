/*
 * 文件名 ResourceViewMapper
 * 创建人 吴昊
 * 创建日期 2019/9/9
 * 版权
 */
package com.cxxy.edu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

public interface ResourceViewMapper {


    //页面停留时间累加 ，视频观看进度取最大值
    @Update("INSERT INTO resource_view ( resource_id, student_id, resource_timeout, resource_current_time )\n" +
            "VALUES\n" +
            "\t(#{resourceId}, ( SELECT student_id FROM student WHERE student_username = #{username} ), #{timeout}, #{currentTime} ) \n" +
            "\tON DUPLICATE KEY UPDATE resource_timeout = resource_timeout + #{timeout},\n" +
            "\tresource_current_time = GREATEST( resource_current_time, #{currentTime} )")
    void insertViewProgress(String username, String resourceId, String timeout, String currentTime);
}
