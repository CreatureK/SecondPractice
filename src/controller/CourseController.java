package controller;

import model.DynContent;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CourseController类 - 用于处理课程管理相关请求的Servlet控制器
 */
@WebServlet(name = "CourseController", urlPatterns = "/course")
public class CourseController extends HttpServlet {

  /**
   * 处理POST请求
   * 1. 处理退选课程请求
   * 2. 处理课程管理请求
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

      // 从会话中获取DynContent对象
      HttpSession session = request.getSession();
      DynContent dynContent = (DynContent) session.getAttribute("dynContent");

      // 如果会话中没有DynContent对象，则从请求属性中获取
      if (dynContent == null) {
        dynContent = (DynContent) request.getAttribute("dynContent");
      }

      // 如果还是没有找到DynContent对象，则返回登录页面
      if (dynContent == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
      }

      // 处理不同的动作请求
      if ("withdraw".equals(action)) {
        // 处理退选课程请求
        handleWithdrawCourses(request, dynContent);
      } else if ("manage".equals(action)) {
        // 处理课程管理请求
        // 这里可以添加其他课程管理相关的功能
      }

      // 将更新后的DynContent对象保存到会话中
      session.setAttribute("dynContent", dynContent);

      // 将DynContent对象设置为请求属性，用于页面渲染
      request.setAttribute("dynContent", dynContent);

      // 转发回课程管理页面
      request.getRequestDispatcher("/courseMng.jsp").forward(request, response);
    } catch (Exception e) {
      // 异常处理
      e.printStackTrace();
      response.getWriter().println("发生错误：" + e.getMessage());
    }
  }

  /**
   * 处理退选课程请求
   * 
   * @param request    HTTP请求
   * @param dynContent 动态内容对象
   */
  private void handleWithdrawCourses(HttpServletRequest request, DynContent dynContent) {
    // 获取选中的课程索引
    String[] selectedCoursesArray = request.getParameterValues("selectedCourses");

    // 如果没有选中任何课程，则直接返回
    if (selectedCoursesArray == null || selectedCoursesArray.length == 0) {
      return;
    }

    // 将选中的课程索引转换为整数列表，并按降序排序（从大到小，避免删除时索引变化）
    List<Integer> selectedIndices = Arrays.stream(selectedCoursesArray)
        .map(Integer::parseInt)
        .sorted((a, b) -> b - a) // 降序排序
        .collect(Collectors.toList());

    // 获取原有的课程名称和分数列表
    List<String> courseNames = new ArrayList<>(dynContent.getCourseNames());
    List<Double> scores = new ArrayList<>(dynContent.getScores());

    // 从列表中删除选中的课程
    for (int index : selectedIndices) {
      if (index >= 0 && index < courseNames.size()) {
        courseNames.remove(index);
        scores.remove(index);
      }
    }

    // 更新DynContent对象中的课程信息
    dynContent.setCourseNames(courseNames);
    dynContent.setScores(scores);
  }
}