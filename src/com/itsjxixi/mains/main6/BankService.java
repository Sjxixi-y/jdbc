package com.itsjxixi.mains.main6;

import com.itsjxixi.entity.Users;
import com.itsjxixi.util.DaoUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BankService {
    private static final BankDao bd = new BankDao();
    // 创建一个数据库连接
    Connection c = null;

    // 转账
    public void transfer(String card, String password, String cardY, int balance) {
        // 查询账户是否存在
        List<Users> users = bd.select(card);
        if (users.size() < 1) {
            System.out.println("输入的账户不存在");
            return;
        }
        Users us1 = users.get(0);
        // 判断密码是否正确
        if (!us1.getPassword().equals(password)) {
            System.out.println("密码错误");
            return;
        }
        // 判断余额
        if (us1.getBalance() < balance) {
            System.out.println("余额不足");
            return;
        }

        // 判断目标用户是否存在
        List<Users> users2 = bd.select(cardY);
        if (users2.size() < 1) {
            System.out.println("目标用户不存在");
            return;
        }
        Users us2 = users.get(0);
        c = DaoUtil.getConnection();
        try {
            // 开启事务，关闭自动提交
            c.setAutoCommit(false);
            // 开启转账操作
            int flag = 0;
            // 减少钱
            bd.update(new Users(0, null, us1.getUsername(), us1.getPassword(), us1.getBalance() - balance), card);
            flag++;
            // 添加钱
            int a = 5 / 0;
            bd.update(new Users(0, null, us2.getUsername(), us2.getPassword(), us2.getBalance() + balance), cardY);
            flag++;

            if (flag != 2) {
                System.out.println("转账失败");
                // 回滚
                c.rollback();
            }
            // 手动提交
            c.commit();
        } catch (SQLException s) {
            System.out.println(s);
        } finally {
            DaoUtil.CloseAll(c, null, null);
        }
    }
}
