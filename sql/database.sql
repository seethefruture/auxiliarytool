# Host: 39.99.203.128  (Version 5.7.29)
# Date: 2020-06-24 22:00:37
# Generator: MySQL-Front 6.1  (Build 1.26)


#
# Structure for table "admin"
#

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_username` varchar(50) NOT NULL DEFAULT '',
  `admin_name` varchar(50) NOT NULL DEFAULT '',
  `admin_password` varchar(100) NOT NULL DEFAULT '',
  `admin_level` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '0:总管理员 1:分管理员',
  `college_id` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='管理员账户表';

#
# Data for table "admin"
#

INSERT INTO `admin` VALUES (1,'root','root','root',1,1),(7,'admin','admin','admin',0,0),(8,'智慧管理员','智慧管理员','111111',1,2);

#
# Structure for table "attendance"
#

CREATE TABLE `attendance` (
  `attendance_id` int(11) NOT NULL,
  `tech_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `lessonNum` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `attend_tag` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`attendance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#
# Data for table "attendance"
#


#
# Structure for table "chapter"
#

CREATE TABLE `chapter` (
  `chapter_id` int(11) NOT NULL AUTO_INCREMENT,
  `chapter_num` int(11) DEFAULT NULL COMMENT '章节',
  `chapter_course_id` int(11) DEFAULT NULL,
  `chapter_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#
# Data for table "chapter"
#

INSERT INTO `chapter` VALUES (1,1,1,'第一章'),(2,2,1,'第二章'),(3,3,1,'第三章'),(4,4,1,'第四章'),(5,1,5,'第一章'),(6,2,5,'第二章'),(7,1,2,'第一章');

#
# Structure for table "class"
#

CREATE TABLE `class` (
  `class_id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(50) NOT NULL DEFAULT '',
  `class_grade` int(11) NOT NULL DEFAULT '0' COMMENT '入学时间',
  `class_number` int(11) NOT NULL DEFAULT '0' COMMENT '1班 2班...',
  `faculty_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='班级表';

#
# Data for table "class"
#

INSERT INTO `class` VALUES (1,'电子工程A系',2016,1,1),(2,'历史文化班',2016,1,2),(3,'生物班',2016,1,3),(4,'电子工程A系',2017,1,1),(8,'电子工程D系',2016,1,6),(10,'生物班',2016,2,7);

#
# Structure for table "college"
#

CREATE TABLE `college` (
  `college_id` int(11) NOT NULL AUTO_INCREMENT,
  `college_name` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`college_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='学院信息表';

#
# Data for table "college"
#

INSERT INTO `college` VALUES (1,'电子与计算机工程学院'),(2,'智慧教育学院'),(3,'马克思学院');

#
# Structure for table "course"
#

CREATE TABLE `course` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程管理',
  `course_name` varchar(50) NOT NULL DEFAULT '',
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='课程信息表';

#
# Data for table "course"
#

INSERT INTO `course` VALUES (1,'高数1',1),(2,'物理',2),(5,'数据结构',5),(7,'化学',4);

#
# Structure for table "doubt"
#

CREATE TABLE `doubt` (
  `doubt_id` int(11) NOT NULL AUTO_INCREMENT,
  `teach_id` int(11) NOT NULL DEFAULT '0',
  `doubt_question` varchar(100) NOT NULL DEFAULT '',
  `doubt_question_path` varchar(100) DEFAULT NULL COMMENT '图片路径',
  `student_id` int(11) NOT NULL DEFAULT '0',
  `doubt_answer` varchar(100) NOT NULL DEFAULT '',
  `doubt_answer_path` varchar(100) DEFAULT NULL COMMENT '解答图片路径',
  `doubt_time` datetime DEFAULT NULL,
  PRIMARY KEY (`doubt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='存放所有的答疑';

#
# Data for table "doubt"
#

INSERT INTO `doubt` VALUES (1,2,'1+2=','',2,'',NULL,'2020-03-29 13:21:37');

#
# Structure for table "faculty"
#

CREATE TABLE `faculty` (
  `faculty_id` int(11) NOT NULL AUTO_INCREMENT,
  `faculty_name` varchar(20) NOT NULL DEFAULT '',
  `college_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`faculty_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='专业表';

#
# Data for table "faculty"
#

INSERT INTO `faculty` VALUES (1,'电子工程A系',1),(2,'历史文化系',0),(3,'生物系',0),(4,'电子工程B系',1),(5,'电子工程C系',1),(6,'电子工程D系',1);

#
# Structure for table "filestoreinfo"
#

CREATE TABLE `filestoreinfo` (
  `file_path` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '1',
  `sql_path` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `custom_path` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`file_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#
# Data for table "filestoreinfo"
#


#
# Structure for table "homework"
#

CREATE TABLE `homework` (
  `homework_id` int(11) NOT NULL AUTO_INCREMENT,
  `teach_id` int(11) DEFAULT NULL,
  `homework_chapter` int(11) DEFAULT NULL,
  `contain_question_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `submit_time` datetime DEFAULT NULL COMMENT '截至时间',
  PRIMARY KEY (`homework_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#
# Data for table "homework"
#

INSERT INTO `homework` VALUES (1,2,1,'1,2','2020-04-10 23:59:00'),(2,3,5,'6','2020-04-29 00:59:00'),(3,2,1,'1,2','2020-06-01 23:15:00'),(4,2,1,'2','2020-04-17 06:40:00'),(6,2,1,'2,3','2020-06-10 06:42:00');

#
# Structure for table "homework_content"
#

CREATE TABLE `homework_content` (
  `homework_content_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '一次学生做的作业的答案信息表',
  `student_id` int(11) DEFAULT NULL,
  `homework_id` int(11) DEFAULT NULL,
  `homework_question_id` int(11) DEFAULT NULL,
  `homework_content_answer` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`homework_content_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#
# Data for table "homework_content"
#

INSERT INTO `homework_content` VALUES (9,3,3,1,'y=x-1'),(10,3,3,2,'对'),(11,2,4,2,'是'),(12,2,3,1,'1'),(13,2,3,2,'1');

#
# Structure for table "homework_question"
#

CREATE TABLE `homework_question` (
  `homework_question_id` int(11) NOT NULL AUTO_INCREMENT,
  `homework_question_detail` varchar(100) NOT NULL DEFAULT '',
  `homework_question_answer` varchar(100) NOT NULL DEFAULT '',
  `course_id` int(11) NOT NULL,
  `homework_question_chapter` int(20) NOT NULL,
  `homework_question_num` int(10) NOT NULL,
  `homework_question_correct` float(3,0) NOT NULL,
  PRIMARY KEY (`homework_question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='作业题目表';

#
# Data for table "homework_question"
#

INSERT INTO `homework_question` VALUES (1,'曲线y=xlnyx的平行于直线x-y+1=0的切线方程为\n\n\n','y=x-1',1,1,0,0),(2,'数列有界是数列收敛的','必要条件',1,1,0,0),(3,'设函数fx=|x|,则函数在点x=0处','连续不可导',1,1,0,0),(4,'初等函数在其定义域内必可导正确吗？','错误',1,1,0,0),(9,'1+1','2',1,1,0,0);

#
# Structure for table "homework_score"
#

CREATE TABLE `homework_score` (
  `homework_score_id` int(11) NOT NULL AUTO_INCREMENT,
  `homework_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `score` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `submit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`homework_score_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#
# Data for table "homework_score"
#

INSERT INTO `homework_score` VALUES (4,1,2,'回答有问题','2020-04-01 10:20:35'),(5,3,3,'OK','2020-04-14 06:31:50'),(6,4,2,'可以','2020-04-17 02:43:23'),(7,3,2,NULL,'2020-05-06 09:21:40');

#
# Structure for table "notification"
#

CREATE TABLE `notification` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `notification_detail` varchar(100) NOT NULL DEFAULT '',
  `course_id` int(11) NOT NULL DEFAULT '0',
  `teacher_id` int(11) NOT NULL DEFAULT '0',
  `student_id` varchar(255) DEFAULT '' COMMENT '所有将接受到通知的学生，隔开',
  `class_id` varchar(255) DEFAULT '' COMMENT '所有将接受到通知的班级,隔开',
  PRIMARY KEY (`notification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知信息表';

#
# Data for table "notification"
#


#
# Structure for table "rebuildclass"
#

CREATE TABLE `rebuildclass` (
  `rebuild_class_id` int(11) NOT NULL,
  `teach_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`rebuild_class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#
# Data for table "rebuildclass"
#


#
# Structure for table "resource"
#

CREATE TABLE `resource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL DEFAULT '0',
  `resource_path` varchar(100) NOT NULL DEFAULT '',
  `resource_chapter` int(10) NOT NULL DEFAULT '0',
  `resource_secrecy` int(10) DEFAULT NULL,
  `resource_sign` int(10) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='教学资源表';

#
# Data for table "resource"
#

INSERT INTO `resource` VALUES (19,1,'resource/1/1/1.jpg',1,1,0),(20,1,'resource/1/2/fpic.jpg',2,1,0),(21,1,'resource/1/3/class.xls',3,1,0),(22,1,'resource/1/3/修改.docx',3,1,0),(24,1,'resource/1/4/総合練習.docx',4,1,0);

#
# Structure for table "resource_view"
#

CREATE TABLE `resource_view` (
  `resource_id` int(11) NOT NULL,
  `resource_timeout` float(5,0) DEFAULT NULL,
  `resource_current_time` float(5,0) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#
# Data for table "resource_view"
#

INSERT INTO `resource_view` VALUES (6,4,0,2);

#
# Structure for table "student"
#

CREATE TABLE `student` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_username` varchar(50) NOT NULL DEFAULT '',
  `student_no` varchar(15) NOT NULL DEFAULT '',
  `student_password` varchar(100) NOT NULL DEFAULT '',
  `student_name` varchar(10) NOT NULL DEFAULT '',
  `class_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='学生表';

#
# Data for table "student"
#

INSERT INTO `student` VALUES (2,'username','238216258','username','吴昊',1),(3,'皮卡丘','16090251','971224','祝美茹',1),(4,'陈果','16090250','123','陈果',3);

#
# Structure for table "teach"
#

CREATE TABLE `teach` (
  `teach_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL DEFAULT '0',
  `class_id` int(11) NOT NULL DEFAULT '0',
  `teach_introduction` varchar(255) DEFAULT NULL COMMENT '课程简介',
  `teach_time` varchar(3) NOT NULL DEFAULT '0,0',
  PRIMARY KEY (`teach_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='教师教授课程表';

#
# Data for table "teach"
#

INSERT INTO `teach` VALUES (1,1,4,NULL,'4,2'),(2,1,1,NULL,'4,2'),(3,5,8,NULL,'4,2'),(4,5,2,NULL,'4,2'),(5,7,10,NULL,'4,2'),(6,1,3,NULL,'4,2'),(8,2,4,NULL,'4,2'),(9,5,4,NULL,'4,2');

#
# Structure for table "teacher"
#

CREATE TABLE `teacher` (
  `teacher_id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_username` varchar(50) DEFAULT '',
  `teacher_name` varchar(50) DEFAULT '',
  `teacher_password` varchar(100) DEFAULT '',
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='教师表';

#
# Data for table "teacher"
#

INSERT INTO `teacher` VALUES (1,'王老师','王晓','123'),(2,'16090200','李晨','123'),(3,'16090201','陈晨','123'),(4,'16090202','秦晨','123'),(5,'16090203','吴晨','123'),(6,'16090204','朱晓星','111111'),(7,'陈晓冉','陈晓冉','111111');

#
# Structure for table "test"
#

CREATE TABLE `test` (
  `test_id` int(11) NOT NULL AUTO_INCREMENT,
  `test_question_contain` varchar(101) NOT NULL DEFAULT '0' COMMENT '最大数量20！',
  `test_time` datetime DEFAULT NULL,
  `teach_id` int(4) NOT NULL,
  `submit_time` datetime DEFAULT NULL,
  `test_num` int(5) DEFAULT NULL,
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='某次测试 5-10条题目';

#
# Data for table "test"
#

INSERT INTO `test` VALUES (2,'1','2020-03-29 00:59:00',2,'2020-06-01 01:59:00',0),(3,'1,3','2020-03-30 07:25:00',2,'2020-06-01 23:25:00',0),(4,'3','2020-04-13 10:28:00',2,'2020-06-01 23:33:00',0),(5,'4','2020-05-09 05:48:00',2,'2020-06-10 05:48:00',0);

#
# Structure for table "test_false_answer"
#

CREATE TABLE `test_false_answer` (
  `test_false_answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `test_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `false_question_id` int(11) DEFAULT NULL,
  `false_answer_choose` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`test_false_answer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#
# Data for table "test_false_answer"
#

INSERT INTO `test_false_answer` VALUES (1,3,2,1,'B'),(2,4,2,3,'A');

#
# Structure for table "test_question"
#

CREATE TABLE `test_question` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL DEFAULT '0',
  `test_question_detail` varchar(400) NOT NULL DEFAULT '' COMMENT '问题描述',
  `test_question_a` varchar(100) NOT NULL DEFAULT '' COMMENT 'a选项描述',
  `test_question_b` varchar(100) NOT NULL DEFAULT '',
  `test_question_c` varchar(100) NOT NULL DEFAULT '',
  `test_question_d` varchar(100) NOT NULL DEFAULT '',
  `test_question_answer` varchar(10) NOT NULL DEFAULT '' COMMENT '正确选项',
  `test_question_type` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '0:单选 1：多选',
  `test_question_num` int(10) NOT NULL,
  `test_question_correct` float(10,0) NOT NULL,
  `test_question_chapter` int(10) NOT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='测试题目表';

#
# Data for table "test_question"
#

INSERT INTO `test_question` VALUES (1,1,'曲线y=xlnyx的平行于直线x-y+1=0的切线方程为','y=x-1','y=-x-1','y=x+1','y=x-2','A',0,3,1,1),(2,5,'1+2=','3','2','4','5','A',0,0,0,5),(3,1,'设函数fx=|x|,则函数在点x=0处','连续且可导','连续且可微','连续不可导','连续不可微','C',0,2,0,1),(4,1,'下列关于区间或集合说法正确的是？','区间本质上是一个集合，是实数集的一个子集','集合本质上是一个区间','集合本质上不是一个区间','不是实数集的一个子集','A',0,0,0,1);

#
# Structure for table "test_score"
#

CREATE TABLE `test_score` (
  `test_score_id` int(11) NOT NULL AUTO_INCREMENT,
  `test_id` int(11) NOT NULL DEFAULT '0',
  `student_id` int(11) NOT NULL DEFAULT '0',
  `score` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`test_score_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='测试成绩表';

#
# Data for table "test_score"
#

INSERT INTO `test_score` VALUES (1,2,2,'100'),(2,3,2,'50'),(3,4,2,'0');
