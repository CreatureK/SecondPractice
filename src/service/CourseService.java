package service;

import dao.CourseDAO;
import dao.LoginDAO;
import model.Course;
import model.DynContent;
import model.ElectiveSub;
import model.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseService类 - 处理课程相关的业务逻辑
 */
public class CourseService {

  private CourseDAO courseDAO;
  private LoginDAO loginDAO;

  public CourseService() {
    this.courseDAO = new CourseDAO();
    this.loginDAO = new LoginDAO();
  }

  /**
   * 获取用户选修的课程信息，并封装为DynContent对象
   * 
   * @param userId 用户ID
   * @return DynContent对象，包含用户信息和选课信息
   */
  public DynContent getUserCourseInfo(String userId) {
    // 获取用户信息
    Login userInfo = loginDAO.getUserById(userId);
    if (userInfo == null) {
      return null;
    }

    String userName = loginDAO.getUserName(userId);

    // 创建DynContent对象
    DynContent dynContent = new DynContent(userId, userInfo.getCollege());
    dynContent.setStudentName(userName); // 设置学生姓名，用于显示

    // 获取用户选修课程列表
    List<ElectiveSub> electiveList = courseDAO.getElectiveSubsByUserId(userId);

    // 将选课信息添加到DynContent对象中
    for (ElectiveSub elective : electiveList) {
      dynContent.addCourse(elective.getCourseName(), elective.getGrade());
    }

    return dynContent;
  }

  /**
   * 获取用户未选修的课程
   * 
   * @param userId 用户ID
   * @return 未选修的课程列表
   */
  public List<Course> getNotElectedCourses(String userId) {
    return courseDAO.getNotElectedCourses(userId);
  }

  /**
   * 批量添加选修课程
   * 
   * @param userId    用户ID
   * @param courseIds 课程ID列表
   * @return 添加结果信息
   */
  public String addElectiveCourses(String userId, List<String> courseIds) {
    if (courseIds == null || courseIds.isEmpty()) {
      return "未选择任何课程";
    }

    int successCount = courseDAO.addElectiveSubs(userId, courseIds);

    if (successCount == courseIds.size()) {
      return "成功添加 " + successCount + " 门课程";
    } else if (successCount > 0) {
      return "部分课程添加成功，成功添加 " + successCount + " 门课程";
    } else {
      return "添加课程失败，请检查是否已经选修了这些课程";
    }
  }

  /**
   * 删除选修课程
   * 
   * @param userId           用户ID
   * @param electiveIndices  要删除的课程索引列表
   * @param currentElectives 当前选修的课程列表
   * @return 删除结果信息
   */
  public String deleteElectiveCourses(String userId, List<String> electiveIndices, List<ElectiveSub> currentElectives) {
    if (electiveIndices == null || electiveIndices.isEmpty()) {
      return "未选择任何课程";
    }

    List<String> courseIds = new ArrayList<>();

    // 根据索引获取对应的课程ID
    for (String indexStr : electiveIndices) {
      try {
        int index = Integer.parseInt(indexStr);
        if (index >= 0 && index < currentElectives.size()) {
          courseIds.add(currentElectives.get(index).getCourseId());
        }
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }

    if (courseIds.isEmpty()) {
      return "无效的课程选择";
    }

    int successCount = courseDAO.deleteElectiveSubs(userId, courseIds);

    if (successCount == courseIds.size()) {
      return "成功退选 " + successCount + " 门课程";
    } else if (successCount > 0) {
      return "部分课程退选成功，成功退选 " + successCount + " 门课程";
    } else {
      return "退选课程失败，请稍后重试";
    }
  }
}