package com.java.qqclient.service;


import com.java.qqcommon.Message;
import com.java.qqcommon.MessageType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
                //从信息通道收到ObjectInputStream
                //转为Message
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

                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){ //普通的聊天消息类型

                    //把服务器转发的消息，直接显示控制器
                    System.out.println("\n" + message.getSender()
                            + "对" + message.getGetter() + "说：" + message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){ //群聊消息类型

                    //把服务器转发的消息，直接显示控制器
                    System.out.println("\n" + message.getSender()
                            + "对所有人说：" + message.getContent());

                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {

                    System.out.println("\n" + message.getSender() + "给" + message.getGetter() + "发文件：" + message.getSrc() + "到电脑目录" + message.getDest());

                    //取出message的文件字节数组
                    //通过文件输出流写出到磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n 保存文件成功");

                }
                else {
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
