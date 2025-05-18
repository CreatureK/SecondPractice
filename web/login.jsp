<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <!DOCTYPE html>
  <html>

  <head>
    <title>学生登录系统</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${pageContext.request.contextPath}/js/validation.js"></script>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 20px;
      }

      .container {
        width: 400px;
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

      .form-group {
        margin-bottom: 15px;
      }

      label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
      }

      input,
      select {
        width: 100%;
        padding: 8px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
      }

      .error-message {
        color: red;
        font-size: 0.8em;
        height: 15px;
        margin-top: 5px;
      }

      button {
        background-color: #4CAF50;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        width: 100%;
        font-size: 16px;
      }

      button:hover {
        background-color: #45a049;
      }
    </style>
  </head>

  <body>
    <div class="container">
      <h2>学生登录</h2>
      <form id="loginForm" action="${pageContext.request.contextPath}/login" method="GET">
        <div class="form-group">
          <label for="username">用户名：</label>
          <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
          <label for="password">密码：</label>
          <input type="password" id="password" name="password" required>
          <div id="passwordError" class="error-message"></div>
        </div>

        <div class="form-group">
          <label for="college">所在学院：</label>
          <select id="college" name="college" required>
            <option value="">请选择学院</option>
          </select>
        </div>

        <div class="form-group">
          <label for="department">所在系：</label>
          <select id="department" name="department" required>
            <option value="">请选择系</option>
          </select>
        </div>

        <div class="form-group">
          <button type="submit">登录</button>
        </div>
      </form>
    </div>
  </body>

  </html>