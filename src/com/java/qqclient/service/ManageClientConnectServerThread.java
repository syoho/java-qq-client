package com.java.qqclient.service;


import java.util.HashMap;

//管理客户端连接到服务器端的线程的类
public class ManageClientConnectServerThread {

    //将多个线程放入HashMap集合
    //key -> 用户id
    //value -> 线程
    private static HashMap<String, ClientConnectServerThread> hashMap = new HashMap<>();

    //写方法
    //将某个线程加入集合
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hashMap.put(userId, clientConnectServerThread);
    }

    //通过userId，得到对应线程
    //返回类型：ClientConnectServerThread
    //传入类型：String -> userId
    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hashMap.get(userId);
    }
}
