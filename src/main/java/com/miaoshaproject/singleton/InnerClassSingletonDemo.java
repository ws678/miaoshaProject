package com.miaoshaproject.singleton;

//懒加载
public class InnerClassSingletonDemo {

    //静态内部类
    static class InnerClassDemo {

        private static InnerClassSingletonDemo innerClassSingletonDemo = new InnerClassSingletonDemo();
    }

    public static InnerClassSingletonDemo getInstance() {

        return InnerClassDemo.innerClassSingletonDemo;
    }

    private InnerClassSingletonDemo() {

        if (InnerClassDemo.innerClassSingletonDemo != null)
            throw new RuntimeException("实例已经存在! 单例类不允许多个实例");
    }
}
