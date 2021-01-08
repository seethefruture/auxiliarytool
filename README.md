# Auxiliarytool 教学辅助平台
[![Java Style Guide](https://img.shields.io/badge/code_style-standard-brightgreen.svg)](https://standardjs.com)
[![Read the Docs (version)](https://img.shields.io/readthedocs/pip/stable.svg)](https://github.com/seethefruture/auxiliarytool_1)<br>

# 预览<br>
入口地址：http://106.13.168.81:9091/<br>
账号：<br>
> username-username(学生)<br>
> username-username(教师)<br>
> admin-admin(总管理员)  <br>
> root-root(管理员)  <br>

# 部署运行
## 准备环境

* 准备docker环境 (组件建议构建在docker中，当然你也可以自己外部搭建)<br>
>首先安装配置完docker

* 1.准备mysql <br>
>使用docker镜像pull mysql:5.7,下面是执行指令 (当然你也可以使用自己的mysql)<br>
>1.**docker pull mysql:5.7**<br>
>2.**docker run -d -p 3306:3306 --name mysql  -e MYSQL_ROOT_PASSWORD=root mysql:5.7**<br>
>3.导入/sql下的sql文件<br>
>
* 2.准备redis <br>
>使用docker镜像pull redis,下面是执行指令 (当然你也可以使用自己的redis)<br>
>1.**docker pull redis:6.0.5**<br>
>2.**docker run -d --name redis -p 6379:6379 redis:6.0.5**<br>

* 3.准备rabbitmq <br>
>使用docker镜像pull rabbitmq,下面是执行指令 (当然你也可以使用自己的rabbitmq)<br>
>1.**docker pull rabbitmq:3.8.2-management**<br>
>2.**docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=root rabbitmq:3.8.2-management**<br>

* 5.修改配置 <br>
>修改/resources下的fileConfig.properties的三个路径（根路径，图片路径，教学资源路径）
>如果你使用了自己的mysql，rabbitmq或者redis，修改application.properties下的其他配置，并导入mysql的sql文件
* 4.启动项目 <br>
>enjoy!

***注意看logs确保每个组件正确启动运行！***

## 使用组件介绍
* springboot 快速构建工具
* thymeleaf 视图渲染模板
* redis 缓存
* rabbitmq 消息中间件
* mysql SqlDB
* websocket 
