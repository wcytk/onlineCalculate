package com.www.mapping;

import com.www.dao.connect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "register")
public class register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置网页响应类型
        response.setContentType("text/html;charset=GBK");
        response.setContentType("text/html");
        //实现具体操作

        connect db = new connect();
        PrintWriter out = response.getWriter();

        String name = request.getParameter("username");
        String passwd = request.getParameter("passwd");
        int isTeacher = Integer.parseInt(request.getParameter("usertype"));
        if (db.add(name, isTeacher, passwd)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            response.sendRedirect(request.getContextPath() + "/register.jsp");
        }
    }
}
