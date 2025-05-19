package controller;

import model.Course;
import model.DynContent;
import model.ElectiveSub;
import service.CourseService;
import dao.CourseDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseController类 - 用于处理课程管理相关请求的Servlet控制器
 */
@WebServlet(name = "CourseController", urlPatterns = "/course")
public class CourseController extends HttpServlet {

  // 课程业务逻辑服务
  private CourseService courseService;
  private CourseDAO courseDAO;

  /**
   * 初始化Servlet
   */
  @Override
  public void init() throws ServletException {
    super.init();
    courseService = new CourseService();
    courseDAO = new CourseDAO();
  }

  /**
   * 处理GET请求
   * 用于处理课程查询请求
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 设置请求和响应的字符编码
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    try {
      // 获取操作类型
      String action = request.getParameter("action");

      // 从会话中获取用户ID
      HttpSession session = request.getSession();
      String userId = (String) session.getAttribute("userId");

      // 如果未登录，则重定向到登录页面
      if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
      }

      // 处理选修课程请求
      if ("electiveSubject".equals(action)) {
        // 获取用户未选修的课程列表
        List<Course> notElectedCourses = courseService.getNotElectedCourses(userId);

        // 将课程列表保存到请求属性中
        request.setAttribute("notElectedCourses", notElectedCourses);

        // 转发到选修课程页面
        request.getRequestDispatcher("/addElectiveSubject.jsp").forward(request, response);
      } else {
        // 默认情况下，显示用户的课程信息
        DynContent dynContent = courseService.getUserCourseInfo(userId);

        // 如果找不到用户课程信息，则重定向到登录页面
        if (dynContent == null) {
          response.sendRedirect(request.getContextPath() + "/login.jsp");
          return;
        }

        // 将DynContent对象保存到会话和请求属性中
        session.setAttribute("dynContent", dynContent);
        request.setAttribute("dynContent", dynContent);

        // 转发到课程管理页面
        request.getRequestDispatcher("/courseMng.jsp").forward(request, response);
      }
    } catch (Exception e) {
      // 异常处理
      e.printStackTrace();
      response.getWriter().println("发生错误：" + e.getMessage());
    }
  }

  /**
   * 处理POST请求
   * 1. 处理退选课程请求
   * 2. 处理添加选修课程请求
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 设置请求和响应的字符编码
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    try {
      // 获取请求中的动作参数
      String action = request.getParameter("action");

      // 从会话中获取用户ID
      HttpSession session = request.getSession();
      String userId = (String) session.getAttribute("userId");

      // 如果未登录，则重定向到登录页面
      if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
      }

      // 处理不同的动作请求
      if ("withdraw".equals(action)) {
        // 处理退选课程请求
        handleWithdrawCourses(request, response, userId);
      } else if ("addElective".equals(action)) {
        // 处理添加选修课程请求
        handleAddElectiveCourses(request, response, userId);
      } else if ("manage".equals(action)) {
        // 处理课程管理请求，这里可以添加其他课程管理相关的功能
        response.sendRedirect(request.getContextPath() + "/course");
      }
    } catch (Exception e) {
      // 异常处理
      e.printStackTrace();
      handleFailure(request, response, "操作失败：" + e.getMessage());
    }
  }

  /**
   * 处理退选课程请求
   * 
   * @param request  HTTP请求
   * @param response HTTP响应
   * @param userId   用户ID
   */
  private void handleWithdrawCourses(HttpServletRequest request, HttpServletResponse response, String userId)
      throws ServletException, IOException {
    // 获取选中的课程索引
    String[] selectedCoursesArray = request.getParameterValues("selectedCourses");

    // 如果没有选中任何课程，则直接返回课程管理页面
    if (selectedCoursesArray == null || selectedCoursesArray.length == 0) {
      response.sendRedirect(request.getContextPath() + "/course");
      return;
    }

    // 将选中的课程索引转换为字符串列表
    List<String> selectedIndices = new ArrayList<>();
    for (String index : selectedCoursesArray) {
      selectedIndices.add(index);
    }

    // 获取当前用户的选修课程列表
    List<ElectiveSub> currentElectives = courseDAO.getElectiveSubsByUserId(userId);

    // 退选课程
    String result = courseService.deleteElectiveCourses(userId, selectedIndices, currentElectives);

    // 处理操作结果
    if (result.startsWith("成功")) {
      // 如果操作成功，重新获取课程信息并转发到课程管理页面
      DynContent dynContent = courseService.getUserCourseInfo(userId);
      HttpSession session = request.getSession();
      session.setAttribute("dynContent", dynContent);
      request.setAttribute("dynContent", dynContent);
      request.getRequestDispatcher("/courseMng.jsp").forward(request, response);
    } else {
      // 如果操作失败，转发到失败页面
      handleFailure(request, response, result);
    }
  }

  /**
   * 处理添加选修课程请求
   * 
   * @param request  HTTP请求
   * @param response HTTP响应
   * @param userId   用户ID
   */
  private void handleAddElectiveCourses(HttpServletRequest request, HttpServletResponse response, String userId)
      throws ServletException, IOException {
    // 获取选中的课程ID
    String[] selectedCoursesArray = request.getParameterValues("selectedCourses");

    // 如果没有选中任何课程，则显示失败信息
    if (selectedCoursesArray == null || selectedCoursesArray.length == 0) {
      handleFailure(request, response, "请选择至少一门课程");
      return;
    }

    // 将选中的课程ID转换为列表
    List<String> selectedCourseIds = new ArrayList<>();
    for (String courseId : selectedCoursesArray) {
      selectedCourseIds.add(courseId);
    }

    // 添加选修课程
    String result = courseService.addElectiveCourses(userId, selectedCourseIds);

    // 处理操作结果
    if (result.startsWith("成功")) {
      // 如果操作成功，重新获取课程信息并转发到课程管理页面
      DynContent dynContent = courseService.getUserCourseInfo(userId);
      HttpSession session = request.getSession();
      session.setAttribute("dynContent", dynContent);
      request.setAttribute("dynContent", dynContent);
      request.getRequestDispatcher("/courseMng.jsp").forward(request, response);
    } else {
      // 如果操作失败，转发到失败页面
      handleFailure(request, response, result);
    }
  }

  /**
   * 处理操作失败情况
   * 
   * @param request  HTTP请求
   * @param response HTTP响应
   * @param message  失败信息
   */
  private void handleFailure(HttpServletRequest request, HttpServletResponse response, String message)
      throws ServletException, IOException {
    request.setAttribute("errorMessage", message);
    request.getRequestDispatcher("/failure.jsp").forward(request, response);
  }
}