package com.itsjxixi.main;

import java.sql.*;

public class Main1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 建立连接，面向接口编程
        String url = "jdbc:mysql://localhost:3306/cs2301";
        String username = "root";
        String password = "123456";
        Connection root = DriverManager.getConnection(url, username, password);
        System.out.println(root);

        // 创建SQL语句
        String str = "select deptno, dname, loc from dept";

        Statement s1 = root.createStatement();

        // 执行
        ResultSet r1 = s1.executeQuery(str);

        // 处理结果
        while (r1.next()) { // 判断结果集中是否有数据
            System.out.println(r1.getObject(1) + ", " + r1.getObject(2) + ", " + r1.getObject(3));
        }

        // 释放资源
        r1.close();
        s1.close();
        root.close();
    }
}
