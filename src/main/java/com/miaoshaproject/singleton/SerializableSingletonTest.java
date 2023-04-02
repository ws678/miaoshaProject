package com.miaoshaproject.singleton;

import java.io.*;

public class SerializableSingletonTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        HungrySingletonDemo hungrySingletonDemo = HungrySingletonDemo.getHungrySingletonDemo();

        //创建序列化Stream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("hungrySingletonDemo"));
        //写入对象 writeObject默认写入项目根目录中
        objectOutputStream.writeObject(hungrySingletonDemo);
        objectOutputStream.close();

        //把写入的文件读取出来 查看是否一样
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("hungrySingletonDemo"));
        HungrySingletonDemo readObject = ((HungrySingletonDemo) objectInputStream.readObject());
        objectInputStream.close();

        //返回true
        System.out.println(hungrySingletonDemo == readObject);
    }
}
