package com.www.mapping;

import com.mysql.cj.Session;
import com.mysql.cj.xdevapi.JsonArray;
import com.www.dao.connect;
import com.www.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "getGrade")
public class getGrade extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (null == session.getAttribute("user")) {
            response.sendRedirect(request.getContextPath() + "login.jsp");
        }
        List<String> an = Arrays.asList(request.getParameter("answer").replace("[", "").replace("]", "").replace(" ", "").split(","));
        List<Integer> wrong = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < an.size(); i++) {
            if (an.get(i).equals(request.getParameter("" + i))) {
                sum++;
            } else {
                wrong.add(i);
            }
        }
        System.out.println("\n" + 100 * sum / an.size());
        JSONObject jo = new JSONObject();
        jo.put("grade", 100 * sum / an.size());
        jo.put("wrong", wrong);
        jo.put("length", an.size());
        PrintWriter out = response.getWriter();
        out.println(jo);
        out.flush();
        out.close();
        connect db = new connect();
        db.updateGrade(100 * sum / an.size(), ((User)session.getAttribute("user")).getName());
//        response.getWriter().write(sum/an.size());
    }
}
