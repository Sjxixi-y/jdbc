package com.itsjxixi.mains.main6;

import java.util.Scanner;

public class BankController {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankService bs = new BankService();

        System.out.print("请输入卡号：");
        String card = sc.nextLine();

        System.out.print("请输入密码：");
        String password = sc.nextLine();

        System.out.print("请输入对方卡号：");
        String cardY = sc.nextLine();

        System.out.print("请输入转账金额：");
        int balance = sc.nextInt();

        bs.transfer(card, password, cardY, balance);
    }
}
