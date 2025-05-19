<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="model.DynContent" %>
    <!DOCTYPE html>
    <html>

    <head>
      <title>课程管理系统</title>
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

        .info-panel {
          margin-bottom: 20px;
          padding: 10px;
          background-color: #f8f9fa;
          border-radius: 4px;
        }

        .info-item {
          margin-bottom: 5px;
          display: inline-block;
          margin-right: 30px;
        }

        .info-label {
          font-weight: bold;
          display: inline-block;
          width: 80px;
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

        .button.red {
          background-color: #f44336;
        }

        .button.red:hover {
          background-color: #d32f2f;
        }

        .button.blue {
          background-color: #2196F3;
        }

        .button.blue:hover {
          background-color: #0b7dda;
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

        .logout {
          margin-top: 20px;
          text-align: center;
        }

        .logout a {
          color: #666;
          text-decoration: underline;
        }
      </style>
    </head>

    <body>
      <div class="container">
        <h2>课程管理系统</h2>
<%--         获取请求属性中的DynContent对象--%>
        <%  DynContent dynContent=(DynContent)request.getAttribute("dynContent"); if (dynContent
          !=null) { %>
          <div class="info-panel">
            <div class="info-item">
              <span class="info-label">学号：</span>
              <span>
                <%= dynContent.getUsername() %>
              </span>
            </div>
            <div class="info-item">
              <span class="info-label">姓名：</span>
              <span>
                <%= dynContent.getStudentName() !=null ? dynContent.getStudentName() : "" %>
              </span>
            </div>
          </div>

          <h3>课程信息</h3>

          <% if (dynContent.getCourseCount()> 0) { %>
            <form id="courseForm" action="<%=request.getContextPath()%>/course" method="post">
              <table>
                <thead>
                  <tr>
                    <th>选择</th>
                    <th>序号</th>
                    <th>课程名称</th>
                    <th>分数</th>
                  </tr>
                </thead>
                <tbody>
                  <% for (int i=0; i < dynContent.getCourseCount(); i++) { %>
                    <tr>
                      <td>
                        <input type="checkbox" id="course<%=i%>" name="selectedCourses" value="<%= i %>"
                          class="checkbox">
                        <label for="course<%=i%>" class="sr-only">选择课程 <%= i+1 %></label>
                      </td>
                      <td>
                        <%= (i+1) %>
                      </td>
                      <td>
                        <%= dynContent.getCourseName(i) %>
                      </td>
                      <td>
                        <%= dynContent.getScore(i) %>
                      </td>
                    </tr>
                    <% } %>
                </tbody>
              </table>

              <div class="button-container">
                <button type="submit" name="action" value="withdraw" class="button red">退选</button>
                <button type="submit" name="action" value="manage" class="button">课程管理</button>
                <a href="<%=request.getContextPath()%>/course?action=electiveSubject" class="button blue">选修课程</a>
              </div>
            </form>
            <% } else { %>
              <div class="no-data">没有课程信息可显示</div>
              <div class="button-container">
                <a href="<%=request.getContextPath()%>/course?action=electiveSubject" class="button blue">选修课程</a>
              </div>
              <% } %>

                <div class="logout">
                  <a href="<%=request.getContextPath()%>/login.jsp">返回登录</a>
                </div>
                <% } else { %>
                  <div class="no-data">
                    <p>无法获取课程数据，请重新登录</p>
                    <div class="logout">
                      <a href="<%=request.getContextPath()%>/login.jsp">返回登录</a>
                    </div>
                  </div>
                  <% } %>
      </div>

      <script>
        // 添加JavaScript函数来处理复选框选择
        document.addEventListener('DOMContentLoaded', function () {
          // 获取表单提交按钮
          let withdrawBtn = document.querySelector('button[value="withdraw"]');
          if (withdrawBtn) {
            withdrawBtn.addEventListener('click', function (e) {
              // 检查是否有选中的课程
              let selectedCourses = document.querySelectorAll('input[name="selectedCourses"]:checked');
              if (selectedCourses.length === 0) {
                e.preventDefault();
                alert('请至少选择一门要退选的课程');
                return false;
              }

              if (!confirm('确定要退选选中的课程吗？')) {
                e.preventDefault();
                return false;
              }
            });
          }
        });
      </script>
    </body>

    </html>