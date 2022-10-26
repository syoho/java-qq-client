package com.java.qqclient.service;


//该类/对象完成文件的传输

import com.java.qqcommon.Message;
import com.java.qqcommon.MessageType;

import java.io.*;

public class FileClientService {

    /*
    src:源文件目录
    dest:目标目录
    senderId:发送用户id
    getterId:接收用户id
     */
    public void sendToOne(String src, String dest, String senderId, String getterId) {

        //读取src文件 --> 封装至message
        Message message = new Message();

        //1 设置文件类型 --> 文件消息
        message.setMesType(MessageType.MESSAGE_FILE_MES);

        //2 设置发送用户id <-- 从方法中传入
        message.setSender(senderId);

        //3 设置接收用户id <-- 从方法中传入
        message.setGetter(getterId);

        //4 设置源文件目录 <-- 从方法中传入
        message.setSrc(src);

        //5 设置目标目录 <-- 从方法中传入
        message.setDest(dest);

        //最关键
        //将文件读取 - 创建一个文件输入流 - 读入字节数组
        //开一个字节数组
        FileInputStream fileInputStream = null;

        byte[] fileBytes = new byte[(int) new File(src).length()];

        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes); //将src文件读入到程序的字节数组

            //将文件对应的字节数组 设置到 message中
            message.setFileBytes(fileBytes);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //关闭
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        //发送
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //提示信息
        System.out.println("\n" + senderId + "给" + getterId + "发送文件：" + src + "到对方的电脑目录：" + dest);


    }

}
