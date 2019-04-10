<%@ page import="java.util.List" %>
<%@ page import="com.www.model.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Wcytk
  Date: 2019/4/8
  Time: 19:33
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
    <title>请回答下列问题</title>
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

        #pagination a {
            width: 50px;
            height: 20px;
            float: left;
            margin-left: 10px;
            cursor: pointer;
        }

        .buttonposition {
            position: absolute;
            top: 10px;
            right: 10px;
        }
        .touming {
            padding: 25px;
            background-color: rgba(255, 255, 255, 0.73);
            /* -moz-opacity:0.5; opacity:0.5; */
        }
    </style>
    <script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        function grade() {
            // xhr.setRequestHeader("Content-type", "application/json");
            for (var i = 0; i < $("#length").val(); i++) {
                $("." + i).css({"background-color": "transparent"})
            }
            $.ajax({
                // async:false,
                url: '${pageContext.request.contextPath}/getGrade',
                type: "post",
                data: $('#gg').serialize(),
                dataType: "json",
                success: function (data) {
                    alert("你的分数为: " + data["grade"]);
                    for (var i in data["wrong"]) {
                        $("." + data["wrong"][i]).css({"background-color": "wheat"})
                    }
                }, error: function (e) {
                    alert("请重新登录!");
                }
            });
        }
    </script>
</head>
<body>
<%
    String name = "";
    if (null == session.getAttribute("user")) {
        response.sendRedirect(request.getContextPath() + "login.jsp");
    }
    if (session.getAttribute("user") != null) {
        User user = (User) session.getAttribute("user");
        name = user.getName();
    }
%>
<nav class="navbar navbar-default buttonposition " role="navigation">
    <button class="btn btn-primary buttonposition "
            onclick="javascript:window.location.href='${pageContext.request.contextPath}/logout'">注销
    </button>
</nav>

<form id="gg">
    <div class="container">
        <div class="panel panel-primary  touming">
            <div class="panel panel-heading">
                题目列表
            </div>
            <div class="panel-body ">
                <form>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th colspan="2">题干</th>
                            <th>答案</th>
                        </tr>
                        </thead>
                        <tbody id="pageInfo">
                        <%
                            List<String> q = (List<String>) request.getAttribute("qu");
                            for (int i = 0; i < q.size(); i++) {
                                out.println("<tr class=\"" + i + "\">" +
                                        "<td>" + (i + 1) + "</td>" +
                                        "<td colspan=\"2\">" + q.get(i) +
                                        "<td> <input type=\"text\" class=\"form-control col-md-2\" name=\"" + i + "\"/> </td>" +
                                        "</tr>");
                            }
                        %>
                        </tbody>
                    </table>
                    <input type="hidden" id="length" value="<%= q.size() %>"/>
                    <input type="hidden" name="answer" value="<%= request.getAttribute("an") %>"/>
                    <button type="button" class="btn btn-primary" style="float: right;" onclick="grade()">提交</button>
                </form>
                <div id="pagination" style="margin-top: 10px;height: 35px;">
                    <a id="firPage" onclick="firstPage()" style="margin-left: 0px;width: 35px; cursor: pointer;">首页</a>
                    <a id="prePage" onclick="prevPage()" style="cursor: pointer;">上一页</a>
                    <a id="nexPage" onclick="pnextPage()" style="cursor: pointer;">下一页</a>
                    <a id="lasPage" onclick="lastPage()" style="width: 35px; cursor: pointer;">尾页</a>
                    <label for="numPage"></label><input id="numPage" style="width: 48px;margin-left: 10px;height: 16px;"
                                                        disabled="disabled"/>
                    <span> 共<%= q.size() % 6 == 0 ? q.size() / 6 : q.size() / 6 + 1 %>页</span>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        var departmentInfo = document.getElementById("pageInfo");  /*获取table中的内容*/
        var totalRow = $("#length").val();   /*计算出总条数(这种方法用来获取table行数，获取列数用var cells = departmentsInfo.rows[0].cells.length;*/
        var pagesize = 6;   /*每页条数*/
        var totalPage = Math.ceil(totalRow / pagesize);  /*计算出总页数*/
        var currentPage;
        var startRow;
        var lastRow;

        function pagination(i) {
            currentPage = i;/*当前页*/
            document.getElementById("numPage").value = "第" + currentPage + "页";   /*显示页码*/
            startRow = (currentPage - 1) * pagesize;/*每页显示第一条数据的行数*/
            lastRow = currentPage * pagesize;/*每页显示的最后一条数据的行数,因为表头也算一行，所以这里不需要减1，这种情况以自己的实际情况为准*/
            if (lastRow > totalRow) {
                lastRow = totalRow;/*如果最后一页的最后一条数据显示的行数大于总条数，那么就让最后一条的行数等于总条数*/
            }
            for (var i = 0; i < totalRow; i++) {   /*将数据遍历出来*/
                var hrow = departmentInfo.rows[i];
                if (i >= startRow && i < lastRow) {
                    hrow.style.display = "table-row";
                } else {
                    hrow.style.display = "none";
                }
            }
        }

        $(function () {
            firstPage();
        });

        function firstPage() {
            var i = 1;
            pagination(i);
        }

        function prevPage() {
            var i = currentPage - 1;
            if (i < 1) {
                i = currentPage;
            }
            pagination(i);
        }

        function pnextPage() {
            var i = currentPage + 1;
            if (i > totalPage) {
                i = currentPage;
            }
            pagination(i);
        }

        function lastPage() {
            var i = totalPage;
            pagination(i);
        }
    </script>
    <%--<%--%>
    <%--List<String> q = (List<String>) request.getAttribute("qu");--%>
    <%--for (int i = 0; i < q.size(); i++) {--%>
    <%--out.println("<div style=\"display: inline-block; width: 200px;\" class=\"" + i + "\">" + q.get(i) + " " + "</div>");--%>
    <%--out.println("<input type=\"text\" name=\"" + i + "\"><br/>");--%>
    <%--}--%>
    <%--%>--%>
    <%--<input type="hidden" name="answer" value="<%= request.getAttribute("an") %>"/>--%>
    <%--<input type="button" value="提交" onclick="grade()">--%>
    <%--<input type="hidden" id="length" value="<%= q.size() %>"/>--%>
</form>
</body>
</html>
