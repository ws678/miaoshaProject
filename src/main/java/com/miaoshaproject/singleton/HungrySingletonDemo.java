package com.miaoshaproject.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

//在类初始化时进行赋值
//java的类加载机制保证了线程安全性
public class HungrySingletonDemo implements Serializable  {

    //需要加上VersionUID 不然每次JVM都会随机生成 单例就失效了
    static final Long serialVersionUID = 141848668489L;

    private static final HungrySingletonDemo hungrySingletonDemo = new HungrySingletonDemo();

    public static HungrySingletonDemo getHungrySingletonDemo() {
        return hungrySingletonDemo;
    }

    private HungrySingletonDemo(){
        if (hungrySingletonDemo != null)
            throw new RuntimeException("防止反射攻击");
    }

    //读取数据流指定签名 反序列化时会从这个地方拿
    Object readResolve() throws ObjectStreamException {
        return hungrySingletonDemo;
    }
}
