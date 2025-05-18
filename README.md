# 学生登录与课程管理系统

## 项目概述
本项目是一个基于MVC设计模式的Web应用，实现了学生登录和课程信息管理功能。系统采用JSP作为视图层(View)，Servlet作为控制器(Controller)，普通Java类作为服务层(Service)，同时使用POJO类进行数据交换。

## 系统架构
- **前端**：JSP + JavaScript
- **后端**：Servlet + Java
- **设计模式**：MVC (Model-View-Controller)

## 功能模块
1. **登录模块**
   - 前端登录表单(login.jsp)
   - 密码验证(包含数字和字母)
   - 学院和系的下拉框联动

2. **业务处理模块**
   - 登录控制器(LoginController.java)
   - 登录业务逻辑(LoginService.java)
   
3. **课程展示模块**
   - 课程管理视图(courseMng.jsp)
   - 课程退选功能
   - 课程管理功能

## 数据模型
1. **Login**：用于传递登录信息的POJO类
   - 用户名(username)
   - 密码(password)
   - 所在学院(college)
   - 所在系(department)

2. **LoginStatus**：用于传递登录验证反馈的POJO类
   - 课程名称(courseName)
   - 分数(score)

3. **DynContent**：用于向视图传递数据的POJO类
   - 用户名(username)
   - 所在学院(college) 
   - 课程信息(courses，包含课程名称和分数)

## 项目结构
```
/
├── src/
│   ├── controller/
│   │   ├── LoginController.java
│   │   └── CourseController.java
│   ├── model/
│   │   ├── Login.java
│   │   ├── LoginStatus.java
│   │   └── DynContent.java
│   └── service/
│       └── LoginService.java
└── web/
    ├── WEB-INF/
    │   └── web.xml
    ├── login.jsp
    ├── courseMng.jsp
    └── js/
        └── validation.js
```

## 使用说明
1. 在登录页面输入用户名和密码
2. 选择所在学院和系
3. 提交表单后，系统会根据输入信息显示对应的课程信息
4. 在课程管理页面，可以选择课程进行退选操作
5. 点击课程管理按钮可以进行其他课程管理功能

## 业务规则
- 计算机学院且软件工程系的学生：显示4组课程信息
- 非计算机学院的学生：显示第2组课程信息
- 退选课程：勾选需要退选的课程，点击退选按钮即可移除选中的课程

