/*
 * 文件名 ResourceView
 * 创建人 吴昊
 * 创建日期 2019/9/9
 * 版权
 */

package com.cxxy.edu.entity;

import lombok.Data;

@Data
public class ResourceView {
    private Integer resourceId;
    private Float resourceTimeout;
    private Float resourceCurrentTime;
    private Integer student_id;
}
