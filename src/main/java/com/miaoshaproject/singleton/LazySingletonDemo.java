package com.miaoshaproject.singleton;

public class LazySingletonDemo {

    public static void main(String[] args) {
        System.out.println("单例模式 本质上还是一个类 里边放一些东西 保证jvm中只有一个实例 并对外提供一个全局的访问点");
        LazySingletonDemo lazySingletonDemo = new LazySingletonDemo();
    }
}

//懒汉模式 使用的时候才开始实例化
//在jvm中只有一个实例
class LazySingleton {

    private static LazySingleton lazySingleton;

    public static LazySingleton getLazySingleton() {
        if (lazySingleton == null) {

            //将锁放在深处
            synchronized (LazySingleton.class) {
                if (lazySingleton == null)
                    lazySingleton = new LazySingleton();
            }
        }
        return lazySingleton;
    }

    //私有化构造方法 不允许直接new
    private LazySingleton() {

    }
}
