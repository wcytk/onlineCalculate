<%@ page import="com.www.model.User" %><%--
  Created by IntelliJ IDEA.
  User: Wcytk
  Date: 2019/4/8
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>请选择整数题型</title>
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

        .form-info {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
            font-size: 15px;
            opacity: 1.0;
        }

        .form-info .radio {
            font-weight: 400;
        }

        /* 			.form-info .form-control {position: relative;box-sizing: border-box;height: 0.9375rem;
                    padding: 10px;font-size: 16px;} */
        .form-info .form-control:focus {
            z-index: 2;
        }

        .valuesize1 {
            width: 6rem;
            height: 3rem;
        }

        .valuesize2 {
            width: 13rem;
            height: 3rem;
        }

        ul {
            margin: 0;
            padding: 0;
            list-style: none;
        }

        ul li {
            line-height: 30px;
        }

        .div1 {
            float: left;
        }

        .div2 {
            float: right;
        }

        .div3 {
            display: inline;
        }

        .div4 {
            margin-left: 30px;
        }

        .opaque {
            background: rgba(255, 255, 255, 0.3);
        }

        .touming {
            padding: 25px;
            background-color: rgba(255, 255, 255, 0.73);
            /* -moz-opacity:0.5; opacity:0.5; */
        }

        .jianju {
            margin: 5px;
        }

        .moveposition {
            position: relative;
            left: 40px;
        }

        .buttonposition {
            position: absolute;
            top: 10px;
            right: 10px;
        }
    </style>
</head>
<body class="text-center">
<%
    User user = (User) session.getAttribute("user");
    String name = "";
    int isTeacher = 0;
    if (null == session.getAttribute("user")) {
        response.sendRedirect(request.getContextPath() + "login.jsp");
    }
    if (session.getAttribute("user") != null) {
        name = user.getName();
        isTeacher = user.getIsTeacher();
    }
%>

<nav class="navbar navbar-default buttonposition " role="navigation">
    <button class="btn btn-primary buttonposition "
            onclick="javascript:window.location.href='${pageContext.request.contextPath}/logout'">注销
    </button>
</nav>

<div class="container ">
    <h1 class="h3 mb-3 font-weight-normal ">题型选择</h1>
    <div class="panel touming col-md-offset-3 col-md-6 panelPostion ">
        <form role="form" class="form-info" action="generate" method="post">
            欢迎，
            <%= name %>
            <c:choose>
                <c:when test="<%= isTeacher  == 1 %>">
                    教师
                </c:when>
                <c:otherwise>
                    学生
                </c:otherwise>
            </c:choose>
            <div class="jianju">
                <div class="div1 element1">
                    <label>数值范围</label>
                </div>
                <div class="element2">
                    <input class="valuesize1 form-control div3 opaque " disabled="disabled" value="0"/>
                    <label>~</label>
                    <input class="valuesize1 form-control div3 opaque " name="bound"
                           onchange="if(/\D/.test(this.value)){alert('只能输入数字');this.value='';}"/>
                </div>
            </div>
            <div class="jianju">
                <div class="div1 element3 ">
                    <label>题目数量</label>
                </div>
                <div class="element4">
                    <input class="valuesize2 form-control div3 opaque " name="number"
                           onchange="if(/\D/.test(this.value)){alert('只能输入数字');this.value='';}"/>
                </div>
            </div>
            <div class="jianju">
                <div class="div1 element5 ">
                    <label>运算符数量</label>
                </div>
                <div class="moveposition">
                    <select class=" form-control valuesize2 opaque element6 " name="symbol">
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                    </select>
                </div>
            </div>
            <div class="jianju">
                <label class="div1 element5 ">是否包含括号&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <div class="element6">
                    <input name="bracket" type="checkbox" data-size="mini" value="0">
                    <script type="text/javascript">
                        $('[name="bracket"]').bootstrapSwitch({
                            onText: "是",
                            offText: "否",
                            onColor: "success",
                            offColor: "info",
                            size: "mini",
                            onSwitchChange: function (event, state) {
                                if (state == true) {
                                    $(this).val(1);
                                } else {
                                    $(this).val(0);
                                }
                            }
                        })
                    </script>
                </div>
            </div>
            <div class="jianju">
                <label class="div1 element5 ">只生成分数题&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <div class="element6">
                    <input name="fraction" type="checkbox" data-size="mini" value="0">
                    <script type="text/javascript">
                        $('[name="fraction"]').bootstrapSwitch({
                            onText: "是",
                            offText: "否",
                            onColor: "success",
                            offColor: "info",
                            size: "mini",
                            onSwitchChange: function (event, state) {
                                if (state == true) {
                                    $(this).val("1");
                                } else {
                                    $(this).val("0");
                                }
                            }
                        })
                    </script>
                </div>
            </div>
            <div class="form-group element6 ">
                <c:choose>
                    <c:when test="<%= isTeacher  == 1 %>">
                        <button type="submit" class=" btn btn-block btn-primary">生成题目</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class=" btn btn-block btn-primary">开始出题</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
    </div>
</div>

<%--<form action="generate" method="post">--%>
<%--Hello,--%>
<%--<c:choose>--%>
<%--<c:when test="<%= isTeacher  == 1 %>">--%>
<%--Teacher--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--Student--%>
<%--</c:otherwise>--%>
<%--</c:choose> <%= name %> <br/>--%>
<%--出题类型: <select name="questions">--%>
<%--<option value="noBracket">无括号整数运算</option>--%>
<%--<option value="bracket">有括号整数运算</option>--%>
<%--<option value="fraction">真分数运算</option>--%>
<%--</select> <br/>--%>
<%--运算符个数: <select name="symbol">--%>
<%--<option value="2">2</option>--%>
<%--<option value="3">3</option>--%>
<%--<option value="4">4</option>--%>
<%--<option value="5">5</option>--%>
<%--</select> <br/>--%>
<%--出题范围: <input type="text" name="bound"> <br/>--%>
<%--出题数量: <input type="text" name="number"> <br/>--%>
<%--<a href="logout" style="text-decoration: none;">注销</a> <br/>--%>
<%--<c:choose>--%>
<%--<c:when test="<%= isTeacher  == 1 %>">--%>
<%--<input type="submit" value="生成题目">--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--<input type="submit" value="做题">--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>
<%--</form>--%>
</body>
</html>
