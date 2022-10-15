package com.java.qqclient.service;

import com.java.qqcommon.User;

import java.net.InetAddress;
import java.net.Socket;

//该类完成【用户登录验证】【用户注册】等功能
public class UserClientService {

    //需要无参构造器；User.java中已定义
    //User作为属性
    //可能在其他地方使用User信息
    private User user = new User();

    //可能在其他地方使用Socket,因此作为属性
    private Socket socket;



    //需要一个方法
    //返回是否登录成功
    //根据【userId】【passwd】到服务器去验证用户是否合法
    public boolean checkUser (String userId, String passwd) {

        //创建User对象
        user.setUserId(userId);
        user.setPasswd(passwd);

        //连接服务器，发送user对象
        //使用Socket
        //有异常抛出

        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);


    }
