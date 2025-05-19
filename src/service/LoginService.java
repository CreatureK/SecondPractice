package service;

import dao.LoginDAO;
import model.Login;
import model.LoginStatus;

/**
 * LoginService类 - 用于处理登录业务逻辑
 */
public class LoginService {

  private LoginDAO loginDAO;

  public LoginService() {
    this.loginDAO = new LoginDAO();
  }

  /**
   * 验证登录信息，并返回对应的课程信息
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

    // 调用DAO进行数据库验证
    Login validatedUser = loginDAO.validateUser(login.getUsername(), login.getPassword());

    // 如果验证失败，返回空的LoginStatus对象
    if (validatedUser == null) {
      return status;
    }

    // 验证成功，将用户信息更新为数据库中的信息
    login.setCollege(validatedUser.getCollege());
    login.setDepartment(validatedUser.getDepartment());

    // 这里不再需要根据学院和系来设置课程信息，因为课程信息将从数据库中获取
    // 返回空的LoginStatus对象，实际课程信息将由CourseService处理
    return status;
  }
}