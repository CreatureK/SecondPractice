# 学生登录与课程管理系统

## 项目概述
本项目是一个基于MVC设计模式的Web应用，实现了学生登录和课程信息管理功能。系统采用JSP作为视图层(View)，Servlet作为控制器(Controller)，普通Java类作为业务层(Service)和数据访问层(DAO)，同时使用POJO类进行数据交换。数据存储采用MySQL数据库。

## 系统架构
- **前端**：JSP + JavaScript
- **后端**：Servlet + Java
- **数据库**：MySQL
- **设计模式**：MVC (Model-View-Controller)

## 功能模块
1. **登录模块**
   - 学生通过学号和密码登录系统
   - 验证学生身份信息

2. **课程管理模块**
   - 展示学生选修课程信息（课程名和分数）
   - 支持退选课程功能
   - 支持选修新课程功能

## 数据模型
1. **Login**：用于传递登录信息的实体类
   - 学号(username)
   - 密码(password)
   - 所在学院(college)
   - 所在系(department)

2. **Course**：用于封装课程信息的实体类
   - 课程ID(courseId)
   - 课程名称(courseName)
   - 课程类型(courseType)

3. **ElectiveSub**：用于封装选课信息的实体类
   - 学生ID(userId)
   - 课程ID(courseId)
   - 分数(grade)
   - 课程名称(courseName)
   - 课程类型(courseType)

4. **DynContent**：用于向视图传递数据的实体类
   - 学号(username)
   - 学生姓名(studentName)
   - 所在学院(college)
   - 课程信息(courses，包含课程名称和分数)

## 数据库设计
1. **user表**：存储学生信息
   - uId：学生学号（主键）
   - uName：学生姓名
   - uPw：登录密码
   - uSchool：所在学院
   - uDepartment：所在系

2. **course表**：存储课程信息
   - cId：课程ID（主键）
   - cName：课程名称
   - cType：课程类型（选修/必修）

3. **electiveSub表**：存储选课信息
   - uId：学生ID（外键）
   - cId：课程ID（外键）
   - grade：课程成绩
   - 联合主键：(uId, cId)

## 项目结构
```
/
├── src/
│   ├── controller/
│   │   ├── LoginController.java
│   │   └── CourseController.java
│   ├── service/
│   │   ├── LoginService.java
│   │   └── CourseService.java
│   ├── dao/
│   │   ├── LoginDAO.java
│   │   └── CourseDAO.java
│   ├── model/
│   │   ├── Login.java
│   │   ├── Course.java
│   │   ├── ElectiveSub.java
│   │   ├── LoginStatus.java
│   │   └── DynContent.java
│   └── util/
│       └── DBUtil.java
├── web/
│   ├── WEB-INF/
│   │   └── web.xml
│   ├── login.jsp
│   ├── courseMng.jsp
│   ├── addElectiveSubject.jsp
│   └── failure.jsp
└── db_init.sql
```

## 使用说明
1. 运行db_init.sql脚本创建数据库和表，并插入初始数据
2. 在登录页面输入学号和密码（例如：学号：2021001，密码：123456）
3. 登录成功后，系统会显示学生选修的课程信息
4. 在课程管理页面，可以：
   - 勾选课程，点击"退选"按钮退选已选课程
   - 点击"选修课程"按钮添加新的选修课程
5. 在选修课程页面，可以选择要选修的课程，点击"添加选修课程"按钮确认选课

## 业务逻辑流程
1. **登录流程**：
   - 用户输入学号和密码
   - LoginController 接收请求，调用 LoginService 进行验证
   - LoginService 调用 LoginDAO 查询数据库
   - 验证成功后，显示课程管理页面

2. **课程管理流程**：
   - 用户查看选修课程信息
   - 用户可以退选课程或添加新的选修课程
   - CourseController 处理请求，调用 CourseService 进行业务处理
   - CourseService 调用 CourseDAO 操作数据库

## 默认测试数据
系统预设了三名学生的数据用于测试：
1. 学号：2021001，密码：123a，姓名：张三，学院：计算机学院，系：软件工程
2. 学号：2021002，密码：123a，姓名：李四，学院：电子信息学院，系：电子工程
3. 学号：2021003，密码：123a，姓名：王五，学院：管理学院，系：工商管理

