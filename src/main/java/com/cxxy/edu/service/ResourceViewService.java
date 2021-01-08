/*
 * 文件名 ResourceViewService
 * 创建人 吴昊
 * 创建日期 2019/9/11
 * 版权
 */

package com.cxxy.edu.service;

import com.cxxy.edu.mapper.ResourceViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceViewService implements ResourceViewMapper {

    @Autowired
    private ResourceViewMapper resourceViewMapper;

    @Override
    public void insertViewProgress(String username, String resourceId, String timeout, String currentTime) {
        resourceViewMapper.insertViewProgress(username, resourceId, timeout, currentTime);
    }
}
