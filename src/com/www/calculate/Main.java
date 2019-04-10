package com.www.calculate;

import com.www.model.User;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.SimpleFormatter;

@WebServlet(name = "generate")
public class Main extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=GBK");
        response.setContentType("text/html");


        HttpSession session = request.getSession();

        int n, i, j, result, sign, num, bound;
        PrintWriter out = response.getWriter();
        int choose;//用来选择题目类型
        if(request.getParameter("number") == null){
            n = 0;
        } else {
            n = Integer.parseInt(request.getParameter("number"));// 读入题目数
        }
        if(request.getParameter("symbol") == null) {
            num = 0;
        } else {
            num = Integer.parseInt(request.getParameter("symbol"));// 读入符号数量
        }
        if(request.getParameter("bound") == null) {
            bound = 0;
        } else {
            bound = Integer.parseInt(request.getParameter("bound"));// 读入范围
        }
        String bracket = "", fraction = "";
        if(request.getParameter("bracket") == null) bracket = "0";
        else bracket = request.getParameter("bracket");
        if(request.getParameter("fraction") == null) fraction = "0";
        else fraction = request.getParameter("fraction");
        if(bracket.equals("0")){
            choose = 0;
        } else {
            choose = 2;
        }

        if(fraction.equals("1")) {
            choose = 1;
        }
        CreateQuestion question = new CreateQuestion();
        Calculate cal = new Calculate();
        FileOutput fo = new FileOutput();//文件输出的类
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
        fo.newOutput(dateFormat.format(now));
        List<String> questions = new ArrayList<String>();
        List<String> answer = new ArrayList<String>();
        List<String> list1 = new ArrayList<String>();//存储题目的集合类
        List<Integer> list2 = new ArrayList<Integer>();//存储题目运算结果和信息的集合类,第一个元素默认为0,即合法
        for (i = 0; i < n; i++) {
            String str = "";
            list1 = question.Create(choose, num, bound);//最后的第一个值存储整数式子还是分数式子的信息
            list2 = cal.calculate(list1);//list2的第一个值存储运算过程中是否有非法运算结果的信息
            while (list2.get(0) == 1) {//“1”表示非法
                list1 = question.Create(choose, num, bound);
                list2 = cal.calculate(list1);
            }
            sign = Integer.parseInt(list1.get(list1.size() - 1));
            if (sign == 0) {//判断为整数式子
                //控制文件末尾不输出换行符
                for (j = 0; j < list1.size() - 1; j++) {
                    str = str + list1.get(j);
                }
                questions.add(str);
                answer.add("" + list2.get(1));
                if (i == n - 1) {
                    str = str + "=" + list2.get(1);
                } else {
                    str = str + "=" + list2.get(1) + "\n";
                }
            } else if (sign == 1) {
                for (j = 0; j < list1.size() - 1; j++) {
                    str = str + list1.get(j);
                }
                questions.add(str);
                answer.add(list2.get(1) + "/" + list2.get(2));
                if (i == n - 1) {
                    str = str + "=" + list2.get(1) + "/" + list2.get(2);
                } else {
                    str = str + "=" + list2.get(1) + "/" + list2.get(2) + "\n";
                }
            } else if (sign == 2) {
                for (j = 0; j < list1.size() - 1; j++) {
                    str = str + list1.get(j);
                }
                questions.add(str);
                answer.add("" + list2.get(1));
                if (i == n - 1) {
                    str = str + list2.get(1);
                } else {
                    str = str + list2.get(1) + "\n";
                }
            }

            fo.output(str);
            System.out.print(str);
        }

        JSONArray qu = JSONArray.fromObject(questions);
        JSONArray an = JSONArray.fromObject(answer);
        request.setAttribute("qu", questions);
        request.setAttribute("an", answer);
        if(session.getAttribute("user") != null) {
            if(((User)session.getAttribute("user")).getIsTeacher() == 1){
                request.getRequestDispatcher(request.getContextPath() + "/answer.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher(request.getContextPath() + "/work.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

    }
}
