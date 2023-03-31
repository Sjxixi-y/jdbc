package com.itsjxixi.main;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Text1 {
    public static void main(String[] args) throws SQLException {
        for (int i = 1; i <= 20; i++) {
            Connection connection = DbUtils.getConnection();
            System.out.println(connection);
            //关闭--->放回池中,不是真的关闭
            connection.close();//调用的是DruidPooledConnection实现类里的close()
        }
    }
}

class DbUtils {
    //声明连接池对象
    private static DruidDataSource ds;

    static {
        //实例化配置对象
        Properties properties = new Properties();
        try {
            //加载配置文件内容
            properties.load(new FileInputStream("src/database.properties"));
            ds = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接对象
    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //释放资源。。
    public static void close(Connection conn, Statement state, ResultSet rs) {
        //关闭连接
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();//
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
