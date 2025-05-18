package model;

/**
 * Login类 - 用于保存用户登录信息的POJO类
 * 包含用户名、密码、所在学院、所在系
 */
public class Login {
  private String username; // 用户名
  private String password; // 密码
  private String college; // 所在学院
  private String department;// 所在系

  // 默认构造函数
  public Login() {
  }

  // 带参数的构造函数
  public Login(String username, String password, String college, String department) {
    this.username = username;
    this.password = password;
    this.college = college;
    this.department = department;
  }

  // Getter和Setter方法
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCollege() {
    return college;
  }

  public void setCollege(String college) {
    this.college = college;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  @Override
  public String toString() {
    return "Login{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", college='" + college + '\'' +
        ", department='" + department + '\'' +
        '}';
  }
}