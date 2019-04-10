package com.www.dao;

import com.www.Util.Util;

import java.sql.*;

public class connect {
    private Connection coon = null;

    // 连接数据库
    public connect() {
        try {
            // 新建驱动
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            // url要加上时区，不然会出现错误
            String url = "jdbc:mysql://127.0.0.1/onlineCalculate?serverTimezone=GMT%2B8";
            String user = "root";
            String passwd = "666";
            // 新建连接
            coon = DriverManager.getConnection(url, user, passwd);
            if (!coon.isClosed()) {
                System.out.println("Connection success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.coon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 插入数据
    public boolean add(String name, int isTeacher, String passwd) {
        // 新建sql语句
        Util.MD5 md5 = new Util.MD5();
        passwd = md5.encryptMD5(passwd);
        String sql = "insert into user(name, teacher, passwd) values('"+name+"','"+isTeacher+"','"+passwd+"')";
        try{
            // 尝试插入数据
            PreparedStatement preStmt = this.coon.prepareStatement(sql);
            // 更新数据库
            preStmt.executeUpdate();
            System.out.println("Insert Success");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int isTeacher(String name) {
        String sql = "select teacher from user where name='"+name+"'";
        try{
            int isTeacher = 0;
            Statement stmt = this.coon.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                isTeacher = rs.getInt("teacher");
            }
            return isTeacher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean comparePasswd(String name, String passwd) {
        // 新建查询语句
        String sql = "select passwd from user where name='"+name+"'";

        try{
            String dbPasswd = null;
            Statement stmt = this.coon.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                dbPasswd = rs.getString("passwd");
            }
            Util.MD5 md5 = new Util.MD5();
            return md5.encryptMD5(passwd).equals(dbPasswd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateGrade(int grade, String name) {
        String sql = "update user set grade='"+grade+"' where name='"+name+"'";

        try{
            PreparedStatement preStmt = this.coon.prepareStatement(sql);
            preStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
