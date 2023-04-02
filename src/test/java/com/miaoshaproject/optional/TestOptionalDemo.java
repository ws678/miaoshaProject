package com.miaoshaproject.optional;


import com.miaoshaproject.stream.a04_TestAggS;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class TestOptionalDemo {

    public static void main(String[] args) {
        //使用of创建Optional对象
        Optional<String> a = Optional.of("a");
        //isPresent
        boolean present = a.isPresent(); //有值返回True 无值返回False
        //get
        if (a.isPresent()) {
            String s = a.get(); //一般先isPresent进行判断
        }
        //ifPresent 内置Consumer函数
        a.ifPresent(System.out::println);

        //orElse Optional不为空则取出值 为空则返回other属性值
        Optional<String> empty = Optional.empty();
        String str = empty.orElse("empty is null");
        String str1 = a.orElse("a is null");
        System.out.println(str + "\n" + str1);

        Optional<Student> cvbnadc = Optional.of(new Student(null, 18, 99.00));
        Optional<Student> convert = convert(cvbnadc);
        if (convert.isPresent()) {
            System.out.println(convert.get().getName());
        }
    }

    //案例 将Student类中的Name转化为大写
    public static Optional<Student> convert(Optional<Student> in) {
        Optional<Student> upper = in.map((i) -> { //map若有值则进行处理 没有值返回Optional.empty();
            if (i.getName() != null) {
                i.setName(i.getName().toUpperCase(Locale.ROOT));
            } else {
                i.setName("未实名用户");
            }
            return i;
        });
        return upper;
    }
    static class Student {
        private String name;
        private Integer age;
        private Double score;

        public Student(String name, Integer age, Double score) {
            this.name = name;
            this.age = age;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", score=" + score +
                    '}';
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

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }
    }
}
