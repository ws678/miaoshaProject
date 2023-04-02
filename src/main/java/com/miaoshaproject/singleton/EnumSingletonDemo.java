package com.miaoshaproject.singleton;

public enum EnumSingletonDemo {

    INSTANCE;

    public void print() {
        System.out.println(this.hashCode());
    }
}
