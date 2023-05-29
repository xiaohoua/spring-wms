# 企业级软件开发大作业：零食管理系统系统
### 一、集成开发环境
+ 此项目已经部署到服务器上：http://47.97.23.87/  
  + 提供三个账号进行测试：
  +  1.超级管理员 账号：288960 密码：123
  +  2.普通管理员 账号：255 密码：255
  +  3.用户 账号：ccccc 密码：123
+ 此项目已经上传到github链接：https://github.com/xiaohoua/spring-wms

后端：IDEA maven
前端：node、vue
数据库：mysql

+ 1.获取代码放置到相应目录（src/main放置的后端代码、wms_web/src放置的前端代码）
+ 2.后端代码处理
  + 用IDEA打开，
  + 检查jdk版本：SDK：1.8.0-261、language level：8-Lambdas
  + maven配置：Maven 3
  + 执行数据库脚本和修改数据库链接配置：
    + 利用`src/sql/table.txt`中的sql语句生成数据库中的各个表
    + 更改application.yml里的url中的数据库名称，用户名密码。
  + 刷新maven（没有maven按钮的话：右键pom.xml --add as Maven Project）
  + 启动Application服务即可
+ 3.前端代码处理
  + 在IDEA中终端打开wms-web文件夹
  + 加载依赖：输入命令 npm i（会在wms-web文件夹生成node_modules文件夹）
  + 配置执行环境：
    + 打开Run/Debug Configurations 
    + package.json选择 你的目录\wms-web\package.json
    + Command:run
    + Scripts:serve
  + 启动serve即可
  