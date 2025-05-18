package model;

import java.util.ArrayList;
import java.util.List;

/**
 * LoginStatus类 - 用于保存用户登录验证反馈信息的POJO类
 * 包含课程名称和分数
 */
public class LoginStatus {
  private List<String> courseNames; // 课程名称列表
  private List<Double> scores; // 分数列表

  // 默认构造函数
  public LoginStatus() {
    this.courseNames = new ArrayList<>();
    this.scores = new ArrayList<>();
  }

  // 添加单个课程信息
  public void addCourse(String courseName, Double score) {
    this.courseNames.add(courseName);
    this.scores.add(score);
  }

  // Getter和Setter方法
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
    StringBuilder sb = new StringBuilder("LoginStatus{courses=[");
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