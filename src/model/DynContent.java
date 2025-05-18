package model;

import java.util.ArrayList;
import java.util.List;

/**
 * DynContent类 - 用于向视图传递数据的POJO类
 * 包含用户名、所在学院、课程信息
 */
public class DynContent {
  private String username; // 用户名
  private String college; // 所在学院
  private List<String> courseNames; // 课程名称列表
  private List<Double> scores; // 分数列表

  // 默认构造函数
  public DynContent() {
    this.courseNames = new ArrayList<>();
    this.scores = new ArrayList<>();
  }

  // 带参数的构造函数
  public DynContent(String username, String college) {
    this.username = username;
    this.college = college;
    this.courseNames = new ArrayList<>();
    this.scores = new ArrayList<>();
  }

  // 从LoginStatus初始化课程信息
  public void initializeFromLoginStatus(LoginStatus loginStatus) {
    this.courseNames = new ArrayList<>(loginStatus.getCourseNames());
    this.scores = new ArrayList<>(loginStatus.getScores());
  }

  // 添加单个课程信息
  public void addCourse(String courseName, Double score) {
    this.courseNames.add(courseName);
    this.scores.add(score);
  }

  // Getter和Setter方法
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getCollege() {
    return college;
  }

  public void setCollege(String college) {
    this.college = college;
  }

  public List<String> getCourseNames() {
    return courseNames;
  }

  public void setCourseNames(List<String> courseNames) {
    this.courseNames = courseNames;
  }

  public List<Double> getScores() {
    return scores;
  }

  public void setScores(List<Double> scores) {
    this.scores = scores;
  }

  // 获取课程数量
  public int getCourseCount() {
    return courseNames.size();
  }

  // 获取指定索引的课程名称
  public String getCourseName(int index) {
    if (index >= 0 && index < courseNames.size()) {
      return courseNames.get(index);
    }
    return null;
  }

  // 获取指定索引的分数
  public Double getScore(int index) {
    if (index >= 0 && index < scores.size()) {
      return scores.get(index);
    }
    return null;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("DynContent{");
    sb.append("username='").append(username).append('\'');
    sb.append(", college='").append(college).append('\'');
    sb.append(", courses=[");
    for (int i = 0; i < courseNames.size(); i++) {
      sb.append("{courseName='").append(courseNames.get(i)).append("', ");
      sb.append("score=").append(scores.get(i)).append("}");
      if (i < courseNames.size() - 1) {
        sb.append(", ");
      }
    }
    sb.append("]}");
    return sb.toString();
  }
}