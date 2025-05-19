<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="model.Course" %>
      <!DOCTYPE html>
      <html>

      <head>
        <title>选修课程</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
          body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
          }

          .container {
            width: 800px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
          }

          h2 {
            text-align: center;
            color: #333;
          }

          table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
          }

          th,
          td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
          }

          th {
            background-color: #4CAF50;
            color: white;
          }

          tr:hover {
            background-color: #f5f5f5;
          }

          .button-container {
            margin-top: 20px;
            text-align: center;
          }

          .button {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            margin: 0 10px;
          }

          .button:hover {
            background-color: #3e8e41;
          }

          .no-data {
            text-align: center;
            padding: 20px;
            color: #666;
          }

          .checkbox {
            width: 20px;
            height: 20px;
          }

          .sr-only {
            position: absolute;
            width: 1px;
            height: 1px;
            padding: 0;
            margin: -1px;
            overflow: hidden;
            clip: rect(0, 0, 0, 0);
            border: 0;
          }
        </style>
      </head>

      <body>
        <div class="container">
          <h2>选修课程</h2>
<%--// 获取可选修的课程列表--%>
          <%  List<Course> notElectedCourses = (List<Course>)request.getAttribute("notElectedCourses");

              if (notElectedCourses != null && !notElectedCourses.isEmpty()) {
              %>
              <form id="electiveForm" action="<%=request.getContextPath()%>/course" method="post">
                <input type="hidden" name="action" value="addElective">

                <table>
                  <thead>
                    <tr>
                      <th>选择</th>
                      <th>课程ID</th>
                      <th>课程名称</th>
                      <th>课程类型</th>
                    </tr>
                  </thead>
                  <tbody>
                    <% for (int i=0; i < notElectedCourses.size(); i++) { Course course=notElectedCourses.get(i); %>
                      <tr>
                        <td>
                          <input type="checkbox" id="course<%=i%>" name="selectedCourses"
                            value="<%= course.getCourseId() %>" class="checkbox">
                          <label for="course<%=i%>" class="sr-only">选择课程 <%= course.getCourseName() %></label>
                        </td>
                        <td>
                          <%= course.getCourseId() %>
                        </td>
                        <td>
                          <%= course.getCourseName() %>
                        </td>
                        <td>
                          <%= course.getCourseType() %>
                        </td>
                      </tr>
                      <% } %>
                  </tbody>
                </table>

                <div class="button-container">
                  <button type="submit" class="button">添加选修课程</button>
                  <a href="<%=request.getContextPath()%>/course" class="button">返回课程管理</a>
                </div>
              </form>
              <% } else { %>
                <div class="no-data">
                  <p>没有可选修的课程</p>
                  <div class="button-container">
                    <a href="<%=request.getContextPath()%>/course" class="button">返回课程管理</a>
                  </div>
                </div>
                <% } %>
        </div>

        <script>
          // 添加JavaScript函数来处理表单提交
          document.addEventListener('DOMContentLoaded', function () {
            let electiveForm = document.getElementById('electiveForm');
            if (electiveForm) {
              electiveForm.addEventListener('submit', function (e) {
                // 检查是否有选中的课程
                let selectedCourses = document.querySelectorAll('input[name="selectedCourses"]:checked');
                if (selectedCourses.length === 0) {
                  e.preventDefault();
                  alert('请至少选择一门要选修的课程');
                  return false;
                }
              });
            }
          });
        </script>
      </body>

      </html>