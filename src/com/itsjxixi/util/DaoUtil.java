package com.itsjxixi.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DaoUtil {
    private static final Properties prp = new Properties();
    // 初始化
    static {
        // 使用配置文件方式获取连接
        try {
            FileInputStream fis = new FileInputStream("src/database.properties");
            prp.load(fis);
            Class.forName(prp.getProperty("driverClassName"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // 获取 连接
    public static Connection getConnection() {
        Connection connection = null;
        String url = prp.getProperty("url");
        String username = prp.getProperty("username");
        String password = prp.getProperty("password");
        try {
            connection =  DriverManager.getConnection(url, username, password);
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return connection;
    }

    public static void CloseAll(Connection c, PreparedStatement p, ResultSet r) {
        try {
            if (r != null)
                r.close();
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            try {
                if (p != null)
                    p.close();
            } catch (SQLException s) {
                s.printStackTrace();
            } finally {
                try {
                    if (c != null)
                        c.close();
                } catch (SQLException s) {
                    s.printStackTrace();
                }
            }
        }
    }
}
