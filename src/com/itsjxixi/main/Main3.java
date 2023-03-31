package com.itsjxixi.main;

import com.itsjxixi.entity.Dept;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main3 {
    public static void main(String[] args) {
        String username = "tom";
        String password = "100101";

        List<Dept> list= select(username, password);

        if (list.size() != 0) {
            for (Dept d : list) {
                System.out.println(d);
            }
        } else {
            System.out.println("密码错误");
        }
    }

    // 查询
    public static List<Dept> select(String u, String p) {
        List<Dept> list = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 连接数据库
            String url = "jdbc:mysql://localhost:3306/cs2301?useSSL=false";
            String username = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, username, password);
            // 获取发送对象
            statement = connection.createStatement();
            // 发送
            String sql = "select * from user where username = '" + u + "' and password = '" + p + "'";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(new Dept(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("phone")));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    try {
                        if (connection != null)
                            connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
        return list;
    }
}
