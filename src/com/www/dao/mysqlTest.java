package com.www.dao;

import com.www.Util.Util;

import java.util.Scanner;

public class mysqlTest {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        connect db = new connect();
        Util.MD5 md5 = new Util.MD5();

//        System.out.println("输入姓名，身份和密码");
//        String name = in.next();
//        int isTeacher = in.nextInt();
//        String passwd = md5.encryptMD5(in.next());
//        db.add(name, isTeacher, passwd);
        System.out.println("输入姓名和密码");
        String name = in.next();
        String passwd = in.next();
        if(db.comparePasswd(name, passwd)) {
            System.out.println("Login Success");
        } else {
            System.out.println("Login failed");
        }
    }
}
