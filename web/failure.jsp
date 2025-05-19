<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <!DOCTYPE html>
  <html>

  <head>
    <title>操作失败</title>
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
        width: 600px;
        margin: 50px auto;
        background: #fff;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        text-align: center;
      }

      h2 {
        color: #e74c3c;
      }

      .message {
        margin: 20px 0;
        padding: 15px;
        background-color: #f8d7da;
        color: #721c24;
        border-radius: 4px;
      }

      .button {
        display: inline-block;
        background-color: #4CAF50;
        color: white;
        padding: 10px 20px;
        text-decoration: none;
        border-radius: 4px;
        margin-top: 20px;
      }

      .button:hover {
        background-color: #3e8e41;
      }
    </style>
  </head>

  <body>
    <div class="container">
      <h2>操作失败</h2>

      <div class="message">
        <% String errorMessage=(String)request.getAttribute("errorMessage"); if (errorMessage !=null &&
          !errorMessage.isEmpty()) { out.println(errorMessage); } else { out.println("发生未知错误，请稍后重试"); } %>
      </div>

      <a href="<%=request.getContextPath()%>/course" class="button">返回课程管理</a>
    </div>
  </body>

  </html>