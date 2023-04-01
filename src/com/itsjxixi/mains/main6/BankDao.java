package com.itsjxixi.mains.main6;

import com.itsjxixi.entity.Users;
import com.itsjxixi.util.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankDao {
    // 数据修改
    public int update(Users d1, String card) {
        Connection connection = null;
        PreparedStatement ps = null;
        int i = -1;
        try {
            connection = DaoUtil.getConnection();
            // 预编译
            String sql = "update t_users set username = ?, password = ?, balance = ? where card = ?";
            ps = connection.prepareStatement(sql);
            // 编译后文件 修改占位符
            ps.setString(1, d1.getUsername());
            ps.setString(2, d1.getPassword());
            ps.setInt(3, d1.getBalance());
            ps.setString(4, card);
            // 执行
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseAll(connection, ps, null);
        }
        return i;
    }

    // 查询数据
    public List<Users> select(String card) {
        List<Users> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = DaoUtil.getConnection();
            // 预编译
            String sql;
            sql = "select * from t_users where card = ?";
            ps = connection.prepareStatement(sql);
            // 修改占位符
            ps.setString(1, card);
            // 发送
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new Users(
                        resultSet.getInt("id"),
                        resultSet.getString("card"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("balance"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseAll(connection, ps, resultSet);
        }
        return list;
    }
}
