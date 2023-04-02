package com.miaoshaproject.stream;

public class Person2 {
    private String name;

    Person2() {
    }

    Person2(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person2{" +
                "name='" + name + '\'' +
                '}';
    }
}
