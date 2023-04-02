package com.miaoshaproject.function;

public class TestPersonClass {

    private String name;
    private Integer age;

    public TestPersonClass() {
        System.out.println("无参构造");
    }

    public TestPersonClass(String name, Integer age) {
        this.name = name;
        this.age = age;
        System.out.println("有参构造");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestPersonClass{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
