package controller;

import model.DynContent;
import model.Login;
import model.LoginStatus;
import service.CourseService;
import service.LoginService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginController类 - 用于处理登录请求的Servlet控制器
 * 仅支持GET请求方式
 */
@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {

  // 业务逻辑服务
  private LoginService loginService;
  private CourseService courseService;

  /**
   * 初始化Servlet
   */
  @Override
  public void init() throws ServletException {
    super.init();
    loginService = new LoginService();
    courseService = new CourseService();
  }

  /**
   * 处理GET请求
   * 1. 接收用户请求参数
   * 2. 封装成Login对象
   * 3. 调用LoginService进行验证
   * 4. 将结果封装成DynContent对象
   * 5. 转发到courseMng.jsp页面进行渲染
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 设置请求和响应的字符编码
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    try {
      // 1.获取请求参数
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      String college = request.getParameter("college");
      String department = request.getParameter("department");

      // 2.封装成Login对象
      Login login = new Login(username, password, college, department);

      // 3.调用LoginService进行验证
      LoginStatus loginStatus = loginService.validateLogin(login);

      // 如果验证失败（没有找到用户），则返回登录页面
      if (loginStatus == null || loginStatus.getCourseCount() == 0) {
        // 检查是否是通过数据库验证的
        if (login.getCollege() == null || login.getDepartment() == null) {
          request.setAttribute("errorMessage", "用户名或密码错误，请重新登录");
          request.getRequestDispatcher("/login.jsp").forward(request, response);
          return;
        }
      }

      // 将用户ID保存到会话中，用于后续操作
      HttpSession session = request.getSession();
      session.setAttribute("userId", username);

      // 4.从数据库获取用户的课程信息
      DynContent dynContent = courseService.getUserCourseInfo(username);

      // 如果没有获取到课程信息，创建一个空的DynContent对象
      if (dynContent == null) {
        dynContent = new DynContent(username, login.getCollege());
      }

      // 5.将dynContent对象保存到会话中，以便在其他页面可以使用
      session.setAttribute("dynContent", dynContent);

      // 6.将dynContent对象设置为请求属性
      request.setAttribute("dynContent", dynContent);

      // 7.转发到courseMng.jsp
      request.getRequestDispatcher("/courseMng.jsp").forward(request, response);

    } catch (Exception e) {
      // 异常处理
      e.printStackTrace();
      response.getWriter().println("发生错误：" + e.getMessage());
    }
  }
}