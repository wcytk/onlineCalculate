<%--
  Created by IntelliJ IDEA.
  User: Wcytk
  Date: 2019/4/7
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link rel="stylesheet" href="static/css/bootstrap.css"/>
    <link rel="stylesheet" href="static/css/bootstrap-switch.css"/>
    <script src="static/js/bootstrap.js"></script>
    <script src="static/js/jquery-3.3.1.min.js"></script>
    <script src="static/js/bootstrap-switch.js"></script>
    <title>欢迎注册四则运算</title>
    <style>
        html,
        body {
            height: 100%;
        }

        body {
            display: -ms-flexbox;
            display: -webkit-box;
            display: flex;
            -ms-flex-align: center;
            -ms-flex-pack: center;
            -webkit-box-align: center;
            align-items: center;
            -webkit-box-pack: center;
            justify-content: center;
            padding-top: 40px;
            padding-bottom: 40px;
            background: #f5f5f5 url(static/img/3.jpg);
            background-size: 100% 100%
        }

        .form-sign {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
        }

        .form-sign .radio {
            font-weight: 400;
        }

        .form-sign .form-control {
            position: relative;
            box-sizing: border-box;
            height: auto;
            padding: 10px;
            font-size: 16px;
        }

        .form-sign .form-control:focus {
            z-index: 2;
        }
    </style>
</head>
<body class="text-center">
    <%
        if (null != session.getAttribute("user")) {
            response.sendRedirect(request.getContextPath() + "index.jsp");
        }
    %>
    <div class="container ">
        <form role="form" class="form-sign" action="register" method="post">
            <h1 class="mb-3 font-weight-normal">四则运算系统</h1>
            <h3 class="h4 mb-3 font-weight-normal">用户注册</h3>
            <div>
                <input type="text" class="form-control " placeholder="Enter Your Username" name="username" required autofocus/>
            </div>
            <div>
                <input type="password" class="form-control" name="passwd" placeholder="Enter Your Password"/>
            </div>
            <label class="col-md-3">
                <input type="radio" checked="checked" name="usertype" value="1"/>教师
            </label>
            <label class=" col-md-offset-4 col-md-3">
                <input type="radio" name="usertype" value="0"/>学生
            </label>
            <!-- 					<button type="button" class="btn btn-block btn-primary " >登录</button> -->
            <button type="submit" class="btn btn-block btn-info ">注册</button>
        </form>
    </div>
<%--<form action="register" method="post">--%>
    <%--用户名: <input type="text" name="username"/><br/>--%>
    <%--密码: <input type="password" name="passwd"/><br/>--%>
    <%--<input type="checkbox" name="teacher"/> 教师<br/>--%>
    <%--<input type="submit" value="提交"/>--%>
<%--</form>--%>
</body>
</html>
