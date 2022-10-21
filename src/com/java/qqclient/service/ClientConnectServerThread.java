package com.java.qqclient.service;


import com.java.qqcommon.Message;
import com.java.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

//继承Thread
//最关键：该线程目的是为了持有Socket
//真正读写数据要靠Socket
public class ClientConnectServerThread extends Thread{

    private Socket socket;

    //构造器
    //没用快捷键
    //接收一个Socket对象
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    //因为线程需要在后台和服务器通信
    //使用while无限循环
    @Override
    public void run() {

        while (true) {

            try {
                System.out.println("客户端线程，等待读取从服务器发来的消息");
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject(); //如果服务器没有发来Message对象，线程会阻塞
                //后续需要用到这个message
                //判断message类型，然后做相应的业务处理
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {

                    //如果读取到的是，服务器返回的在线用户列表
                    //取出在线列表，并显示
                    String[] onlineUsers = message.getContent().split("--");

                    //提示
                    System.out.println("\n==========User List==========");

                    //居然又要用到for循环
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户：" + onlineUsers[i]);
                    }

                } else {
                    System.out.println("其他类型的Message，暂不处理……");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    //为了更方便得到Socket - get()方法
    public Socket getSocket() {
        return socket;
    }



}
