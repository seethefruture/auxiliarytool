package com.cxxy.edu.service;

import com.cxxy.edu.entity.Admin;
import com.cxxy.edu.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: AdministratorService
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/1318:12
 */
@Service
public class AdministratorService {
    @Autowired
    private AdministratorMapper mapper;

    public void insert(Admin administrator) {
        mapper.insert(administrator);
    }

    public void updateByAdminId(Admin administrator) {
        mapper.updateByAdminId(administrator);
    }

    public Admin queryByUserName(String username) {
        return mapper.queryByUserName(username);
    }

    public Admin selectByUsernameAndPassword(String username,String password){
        return mapper.selectByUsernameAndPassword(username,password);
    }

    public List<Map<String,Object>> selectAdminAndCollege(){
        return mapper.selectAdminAndCollege();
    }

    public void deleteByAdminId(Integer adminId){
        mapper.deleteByAdminId(adminId);
    }

    public Map<String,Object> selectAdminAndCollegeByAdminId(Integer adminId){
        return mapper.selectAdminAndCollegeByAdminId(adminId);
    }

    public Admin selectByAdminId(Integer adminId){
        return mapper.selectByAdminId(adminId);
    }

    public void update(String userName, String password,String name,Integer id){
        mapper.update(userName,password,name,id);
    }


}
