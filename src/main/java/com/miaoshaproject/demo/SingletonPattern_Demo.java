package com.miaoshaproject.demo;

/**
 * @Author wangshuo
 * @Date 2022/5/19, 16:19
 * 几个单例模式的demo
 */
public class SingletonPattern_Demo {

    //----------------------------懒汉式单例模式--------------------------------

    //简单懒汉式单例模式（线程不安全）
    public static class Singleton_1 {

        private static Singleton_1 singleton_1;

        //私有构造方法
        private Singleton_1() {
        }

        //对外提供单例对象获取方法
        public static Singleton_1 getSingleton_1() {
            return singleton_1;
        }
    }

    //线程安全的懒汉式单例（使用了synchronized关键字。资源消耗较大）
    public static class Singleton_2 {

        private static Singleton_2 singleton2;

        //私有构造方法
        private Singleton_2() {
        }

        //对外提供线程安全的单例对象获取方法
        public static synchronized Singleton_2 getSingleton_2() {
            if (singleton2 == null)
                singleton2 = new Singleton_2();
            return singleton2;
        }
    }

    //资源消耗较小且线程安全的懒汉式单例
    public static class Singleton_3 {

        //volatile保证可见性
        private volatile static Singleton_3 singleton_3;

        private Singleton_3() {
        }

        //双重检验，相当于把我们的锁 锁在更深处了
        public static Singleton_3 getSingleton_3() {
            if (singleton_3 == null) {
                synchronized (Singleton_3.class) {
                    if (singleton_3 == null)
                        singleton_3 = new Singleton_3();
                }
            }
            return singleton_3;
        }
    }

    //----------------------------饿汉式单例模式--------------------------------

    //简单的饿汉单例模式（线程安全）
    public static class Singleton_4 {

        //使用final关键字(无法进行二次赋值)
        //实例被静态和final了，在类第一次被加载到内存时就初始化（这也造成了一定的资源浪费）
        private static final Singleton_4 singleton_4 = new Singleton_4();

        private Singleton_4() {
        }

        public static Singleton_4 getSingleton_4() {
            return singleton_4;
        }
    }

    //复杂的饿汉单例模式（使用静态内部类）
    public static class Singleton_5 {

        //用于创建Singleton_5的工具人（其实它是懒汉式的）
        private static class Singleton_5_Creater {
            private static final Singleton_5 singleton_5 = new Singleton_5();
        }

        private Singleton_5() {
        }

        //把创建方法静态final化（调用该方法之后才会创建Singleton_5实例）
        private static final Singleton_5 getSingleton_5(){
            return Singleton_5_Creater.singleton_5;
        }
    }
}
