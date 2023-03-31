package com.itsjxixi.main;

import com.itsjxixi.entity.Dept;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        // 添加
        // System.out.println(insert(new Dept(0, "tom", "123456", "19967297087")));
        // 删除
        // System.out.println(delete(6));
        // 修改
        // System.out.println(update(new Dept(0, "tom", "100101", "11312341236"), 1));
        // 查询
        for (Dept d : select(1)) {
            System.out.println(d);
        }
    }

    // 增加
    public static int insert(Dept d1) {
        Connection root = null;
        Statement s1 = null;
        int a = -1;
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 连接数据库
            String url = "jdbc:mysql://localhost:3306/cs2301?useSSL=false";
            String username = "root";
            String password = "123456";
            root = DriverManager.getConnection(url, username, password);
            // 获取发送 SQL 对象
            String sql = "insert into user values(null , '" + d1.getUsername() + "', '" + d1.getPassword() + "', '" + d1.getPhone() + "');";
            s1 = root.createStatement();
            // 发送 SQL，返回结果集
            a = s1.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (s1 != null)
                    s1.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (root != null)
                        root.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return a;
    }

    // 删除
    public static int delete(int id) {
        Connection connection = null;
        Statement statement = null;
        int i = -1;
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
            // 执行
            String sql = "delete from user where id = " + id + ";";
            i = statement.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
        return i;
    }

    // 修改
    public static int update(Dept d1, int id) {
        Connection connection = null;
        Statement statement = null;
        int i = -1;
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
            // 执行
            String sql = "update user set username = '" +
                    d1.getUsername() + "', password = '" +
                    d1.getPassword() + "',phone = '" +
                    d1.getPhone() + "' where id = " +
                    id + ";";
            i = statement.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
        return i;
    }

    // 查询
    public static List<Dept> select(int ids) {
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
            String sql;
            if (ids <= 0) {
                sql = "select * from user";

            } else {
                sql = "SELECT * from user where id = " + ids;
            }
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
