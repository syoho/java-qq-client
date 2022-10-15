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
        System.out.println("==========Exit System==========");
    }

    //显示主菜单
    public void mainMenu() {

        while(loop) {

            //第一层页面

            System.out.println("==========QQSystem==========");
            System.out.println("\t\t1 登录系统");
            System.out.println("\t\t9 退出系统");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1); //输入一个字符

            //根据用户输入，处理不同逻辑
            switch (key) {

                case "1":
                    //第一层页面输入项
                    System.out.print("请输入用户ID：");
                    String userId = Utility.readString(30);
                    System.out.print("请输入用户密码：");
                    String passwd = Utility.readString(30);
                    //需要到服务器去验证用户是否合法
                    //此处有大量代码
                    //先梳理逻辑->假设已验证成功
                    //编写一个类【UserClientService】【用户登录/注册】【服务】


                    if (true) {
                        //二级菜单
                        System.out.println("==========Welcome User " + userId +" To Submenu==========");
                        //再次loop
                        while (loop) {
                            System.out.println("\n==========Submenu User " + userId +"==========");
                            System.out.println("\t\t 1 显示在线列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择（1-9）：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    System.out.println("显示在线列表");
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    loop = false;
                                    break;
                            }
                        }
                    }else {
                        //服务器验证失败，登录失败
                        System.out.println("==========Login Failed==========");
                    }
                    break;

                case "9": //第一级菜单选项
                    loop = false;
                    break;
            }






        }

    }

}
