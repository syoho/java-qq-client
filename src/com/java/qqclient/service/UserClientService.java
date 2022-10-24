package com.java.qqclient.service;

import com.java.qqcommon.Message;
import com.java.qqcommon.MessageType;
import com.java.qqcommon.User;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
    public boolean checkUser(String userId, String passwd) {

        //控制判断结构
        boolean b = false;

        //创建User对象
        user.setUserId(userId);
        user.setPasswd(passwd);

        //连接服务器，发送user对象
        //使用Socket
        //有异常抛出

        //surround with快捷键
        //Mac：option+command+T
        //socket在其他地方也要使用，因此要声明，作为属性
        try {
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            //socket -> 得到ObjectOutputStream对象
            //ObjectOutputStream对象 <- user
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(user); //发送User对象

            //读取从服务器回复的Message对象
            //需要得到一个ObjectInputStream对象
            //问：这里的socket是client的还是server的？
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            //objectInputStream -> 读取方法 -> 强制转化为Message对象【向下转型】
            Message message = (Message) objectInputStream.readObject(); //有异常 -> 需要扩大异常信息

            //Message对象 -> mesType属性 -> Messagetype接口 -> 定义消息的内容
            if (message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) { //登录成功


                //登录成功 - 启动线程 - 持用socket - 后台持续运行 - 保持和服务器端持续通讯 - 感谢韩老，居然在编程中找到了乐趣 - 理想的状态应该是郭宇
                //创建一个和服务器端保持通讯的线程
                //又要创建一个线程类：ClientConnectServerThread - 线程之后放入集合中管理
                //放入socket
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                //启动线程
                clientConnectServerThread.start();
                //为了客户端扩展，将线程放入集合中
                //静态方法直接用
                ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);

                b = true;

            } else {

                //如果登录失败
                //无法启动线程
                //关闭socket
                socket.close();

            }


        } catch (Exception e) { //IOException -> Exception
            e.printStackTrace();
        }

        return b;
    }

    //向服务器端请求在线用户列表
    public void onlineFriendList() {

        //发送一个Message
        //类型是MESSAGE_GET_ONLINE_FRIEND

        //建对象
        Message message = new Message();
        //设置种类
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getUserId());

        //准备发送给服务器
        //应该得到当前线程的Socket - 对应的ObjectOutputSteam对象
        //ManageClientConnectServerThread.getClientConnectServerThread(user.getUserId()) - 取得【线程】- 取得Socekt - 取得getOutputStream()
        try {

            //从管理线程的集合中 - 通过userId - 得到线程
            ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(user.getUserId());

            //线程 - 关联的Socket
            Socket socket = clientConnectServerThread.getSocket();

            //Socket - OutputStream
            OutputStream outputStream = socket.getOutputStream();

            //OutputStream - ObjectOutputStream
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            //发送一个Message对象
            //向服务器要求在线用户列表
            //ObjectOutputStream - writeObject() - message
            objectOutputStream.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //编写方法 - 退出客户端 - 给服务器发送一个退出系统的Message对象
    public void logout() {

        //构建Message对象
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getUserId()); //指定是哪个客户端

        //发送Message
        try {
            //ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
            System.out.println(user.getUserId() + "退出系统");
            System.exit(0); //结束进程
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
