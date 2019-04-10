package com.www.mapping;

import com.www.dao.connect;
import com.www.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "login")
public class login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置网页响应类型
        response.setContentType("text/html;charset=GBK");
        response.setContentType("text/html");

        connect db = new connect();
        PrintWriter out = response.getWriter();

        String name = request.getParameter("username");
        String passwd = request.getParameter("passwd");
        if(db.comparePasswd(name, passwd)){
            User user = new User();
            user.setName(name);
            user.setIsTeacher(db.isTeacher(name));
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/loginFail.html");
        }
    }
}
