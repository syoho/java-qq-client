package com.java.qqclient.view;

import com.java.qqclient.utils.Utility;

//客户端的登录界面/菜单
public class QQView {

    //需要写在方法外面
    private boolean loop = true; //控制是否显示菜单
    private String key = ""; //接受用户的输入

    //主方法-测试
    public static void main(String[] args) {
        new QQView().mainMenu();
    }

    //显示主菜单
    public void mainMenu() {

        while(loop) {

            System.out.println("==========QQSystem==========");
            System.out.println("\t\t1 登录系统");
            System.out.println("\t\t9 退出系统");
            System.out.println("请输入你的选择：");


            key = Utility.readString(1); //输入一个字符

            //根据用户输入，处理不同逻辑
            switch (key) {

                case "1":
                    System.out.println("登录系统");
                    break;
                case "9":
                    loop = false;
                    break;

            }

        }

    }

}
