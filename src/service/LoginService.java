package service;

import model.Login;
import model.LoginStatus;

/**
 * LoginService类 - 用于处理登录业务逻辑
 */
public class LoginService {

  /**
   * 验证登录信息，并返回对应的课程信息
   * 业务逻辑：
   * 1. 如果所在学院为计算机学院且所在系为软件工程，则返回4组课程信息
   * 2. 如果所在学院非计算机学院，则返回表格中的第2组值
   * 
   * @param login 登录信息
   * @return LoginStatus对象，包含课程信息
   */
  public LoginStatus validateLogin(Login login) {
    LoginStatus status = new LoginStatus();

    // 检查登录信息
    if (login == null) {
      return status; // 返回空的LoginStatus对象
    }

    String college = login.getCollege();
    String department = login.getDepartment();

    // 判断学院和系的条件，并设置相应的课程信息
    if ("计算机学院".equals(college) && "软件工程".equals(department)) {
      // 计算机学院且软件工程系，返回4组值
      status.addCourse("Java程序设计", 85.5);
      status.addCourse("Web开发技术", 90.0);
      status.addCourse("数据结构", 78.5);
      status.addCourse("软件工程", 88.0);
    } else if (!"计算机学院".equals(college)) {
      // 非计算机学院，返回第2组值
      status.addCourse("Web开发技术", 90.0);
    }

    return status;
  }
}