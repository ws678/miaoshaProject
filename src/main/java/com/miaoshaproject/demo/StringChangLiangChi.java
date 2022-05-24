package com.miaoshaproject.demo;

/**
 * @Author wangshuo
 * @Date 2022/5/9, 17:42
 * 字符串常量池
 */
public class StringChangLiangChi {

    /*
        jvm为了提高性能和减少开销，在实例化字符串常量时进行了一些优化
        字符串类维护了一个字符串池，当代码创建字符串常量时，jvm会先检查常量池，若已存在，直接返回池中实例的引用
     */
    //使用常量池
    static class Program_A {
        public static void main(String[] args) {
            String a = "hello";
            String b = "hello";
            //比较值
            System.out.println(a.equals(b));
            //比较内存地址
            System.out.println((a == b));
        }
    }

    /*
        new 的 String 不会指向常量池的对象
     */
    //不使用常量池
    static class Program_B {

        public static void main(String[] args) {
            String a = "hello";
            String b = new String("hello");
            //比较值
            System.out.println(a.equals(b));
            //比较内存地址
            System.out.println((a == b));
        }
    }

    //intern()方法 将字符串指向常量池中的某一个，返回一个保留池字符串
    static class Program_C{

        public static void main(String[] args) {
            String a = "hello";
            String b = new StringBuilder("he").append("llo").toString();
            String c = b.intern();

            //比较值
            System.out.println(a.equals(c) && a.equals(b));
            //比较内存地址
            System.out.println((a == b));
            System.out.println((a == c));
        }
    }
}
