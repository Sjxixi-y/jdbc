package com.itsjxixi.mains.main5;

import com.itsjxixi.entity.Dept;
import com.itsjxixi.mains.main5.util.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装为 工具类
 */
public class Main5 {
    // 增加
    public static int insert(Dept d1) {
        Connection connection = null;
        PreparedStatement ps = null;
        int a = -1;
        try {
            connection = DaoUtil.getConnection();
            // 获取发送 SQL 对象
            String sql = "insert into user values(null, ?, ?, ?)";
            ps = connection.prepareStatement(sql);
            //
            ps.setString(1, d1.getUsername());
            ps.setString(2, d1.getPassword());
            ps.setString(3, d1.getPhone());
            // 发送 SQL，返回结果集
            a = ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseAll(connection, ps, null);
        }
        return a;
    }

    // 删除
    public static int delete(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        int i = -1;
        try {
            connection = DaoUtil.getConnection();
            // 预编译
            String sql = "delete from user where id = ?";
            ps = connection.prepareStatement(sql);
            // 修改占位符
            ps.setInt(1, id);
            // 执行
            i = ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseAll(connection, ps, null);
        }
        return i;
    }

    // 修改
    public static int update(Dept d1, int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        int i = -1;
        try {
            connection = DaoUtil.getConnection();
            // 预编译
            String sql = "update user set username = ?, password = ?, phone = ? where id = ?";
            ps = connection.prepareStatement(sql);
            // 编译后文件 修改占位符
            ps.setString(1, d1.getUsername());
            ps.setString(2, d1.getPassword());
            ps.setString(3, d1.getPhone());
            ps.setInt(4, id);
            // 执行
            i = ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseAll(connection, ps, null);
        }
        return i;
    }

    // 查询
    public static List<Dept> select(int ids) {
        List<Dept> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = DaoUtil.getConnection();
            // 预编译
            String sql;
            if (ids <= 0) {
                sql = "select * from user";

            } else {
                sql = "SELECT * from user where id = ?";
            }
            ps = connection.prepareStatement(sql);
            // 修改占位符
            ps.setInt(1, ids);
            // 发送
            resultSet = ps.executeQuery(sql);
            while (resultSet.next()) {
                list.add(new Dept(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("phone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseAll(connection, ps, resultSet);
        }
        return list;
    }
}
