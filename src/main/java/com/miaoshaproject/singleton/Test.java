package com.miaoshaproject.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Test {

    public Test() {
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        //获得DeclaredConstructor
        /*Constructor<InnerClassSingletonDemo> declaredConstructor = InnerClassSingletonDemo.class.getDeclaredConstructor();
        //获得访问权限
        declaredConstructor.setAccessible(true);
        //实例化
        InnerClassSingletonDemo instance = declaredConstructor.newInstance();
        //通过静态内部类拿到一个实例
        InnerClassSingletonDemo innerClassSingletonDemo = InnerClassSingletonDemo.getInstance();

        //验证是否是同一个实例 (运行结果是false)
        System.out.println(instance == innerClassSingletonDemo);*/

        EnumSingletonDemo instance1 = EnumSingletonDemo.INSTANCE;
        EnumSingletonDemo instance2 = EnumSingletonDemo.INSTANCE;
        //天然不允许new 可以防止反射攻击
        //EnumSingletonDemo enumSingletonDemo = new EnumSingletonDemo();
        //结果是true
        System.out.println(instance1 == instance2);
        instance1.print();
        instance2.print();
    }
}
