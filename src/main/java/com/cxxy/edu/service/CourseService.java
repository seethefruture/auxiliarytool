package com.cxxy.edu.service;

import com.cxxy.edu.entity.Course;
import com.cxxy.edu.mapper.CourseMapper;
import com.cxxy.edu.mapper.TeacherMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: CourseService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1320:02
 */
@Service
public class CourseService {
    @Autowired
    private CourseMapper mapper;
    @Autowired
    private TeacherMapper teacherMapper;

    public void deleteByName(String name) {
        mapper.delete(name);
    }

    public Course queryById(int id) {
        return mapper.queryById(id);
    }

    public Map<String,Object> selectByCourseId(Integer courseId){
        return mapper.selectByCourseId(courseId);
    }

    public PageInfo<Map<String,Object>> selectAll(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> courseList = mapper.selectAll();
        PageInfo<Map<String,Object>> coursePageInfo = new PageInfo<Map<String,Object>>(courseList);;
        return coursePageInfo;
    }

    public int update(Integer id,String courseName,String teacherName)throws SQLException{
        int sign = judgeRepeat(courseName,teacherName,2,id);
        return sign;

    }

    public void deleteById(int id){
        mapper.deleteById(id);
    }

    public List<Course> selectByCourseName(String courseName){
        return mapper.selectByCourseName(courseName);
    }

    public Integer selectByCourseNameAndTeacherId(String name,Integer id){
        return mapper.selectByCourseNameAndTeacherId(name,id);
    }

    public List<Map<String,Object>> selectByCourseNameORTeacherName(String teacherName,String courseName){
        return mapper.selectByCourseNameORTeacherName(teacherName,courseName);
    }


    //判断是否有重复的老师或课程
    public int judgeRepeat(String courseName,String teacherName,int sign,int courseId)throws SQLException{
        List<Integer> nameList = teacherMapper.selectByTeacherNameOnly(teacherName);
        if(nameList.size()==0){
            //没有该老师
            return 1;
        }
        if(nameList.size()==1){
            //只有一条记录，且没有重名
            int teacherId = nameList.get(0);
            //查找是否有该课程
            Integer count = mapper.selectByCourseNameAndTeacherId(courseName,teacherId);
            //没有，则插入
            if(count==null){
                if(sign==1) {
                    mapper.insert(courseName, teacherId);
                }
                if(sign==2){
                    mapper.update(courseId,courseName,teacherId);
                }
                return 4; //未知错误
            }
            else{
                return 3; //有该课程
            }
        }
        if(nameList.size()>1){
            //有重名，要求输入用户名
            return 2;
        }
        return 5;
    }
    public int insertCourse(String courseName,String teacherName)throws SQLException {
        int sign = judgeRepeat(courseName,teacherName,1,0);
        return sign;
    }

    public int insertByUsername(String courseName,String username)throws SQLException{
        Integer teacherId = teacherMapper.selectByTeacherUsername(username);
        //不存在该用户名的老师
        if(teacherId == null){
            return 1;
        }
        else {
            Integer id = mapper.selectByCourseNameAndUsername(courseName,username);
            //没有该课程
            if(id==null){
                mapper.insert(courseName,teacherId);
                return 3;
            }
            //该课程已经存在
            else{
                return 2;
            }
        }
    }

    public int selectByCourseNameAndTeacherName(String courseName,String teacherName){
        return mapper.selectByCourseNameAndTeacherName(courseName,teacherName);
    }

}
