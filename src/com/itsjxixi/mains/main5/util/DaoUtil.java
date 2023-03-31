package com.itsjxixi.mains.main5.util;

import java.sql.*;

public class DaoUtil {
    // 初始化
    static {
        try {
            Class.forName("");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // 获取 连接
    public static Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/cs2301?useSSL=false";
        String username = "root";
        String password = "123456";
        try {
            connection =  DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static void CloseAll(Connection c, PreparedStatement p, ResultSet r) {
        try {
            if (r != null)
                r.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (p != null)
                    p.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (c != null)
                        c.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
