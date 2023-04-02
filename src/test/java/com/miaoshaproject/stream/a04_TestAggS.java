package com.miaoshaproject.stream;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class a04_TestAggS {

    public static void main(String[] args) {


        //获取成绩最大值
        Stream<Student> studentStream = Stream.of(
                new Student("wangEr", 15, 99.00),
                new Student("liSi", 55, 9.50),
                new Student("liuWu", 5, 39.00),
                new Student("zhaoLiu", 85, 9.00)
        );
        Optional<Student> collect = studentStream.max((v1, v2) -> {
            double v = v1.getScore() - v2.getScore();
            return ((int) v);
        });
        collect.ifPresent(student -> System.out.println("最大值" + student.toString()));

        //获取年龄最小值
        Stream<Student> studentStream1 = Stream.of(
                new Student("wangEr", 15, 99.00),
                new Student("liSi", 55, 9.50),
                new Student("liuWu", 5, 39.00),
                new Student("zhaoLiu", 85, 9.00)
        );
        studentStream1.min((v1, v2) -> {
            return v1.getAge() - v2.getAge();
        }).ifPresent(System.out::println);

        //求详细聚合信息 summarizingDouble summarizingInt summarizingLong
        Stream<Student> studentStream2 = Stream.of(
                new Student("wangEr", 15, 99.00),
                new Student("liSi", 55, 9.50),
                new Student("liuWu", 5, 39.00),
                new Student("zhaoLiu", 85, 9.00)
        );
        System.out.println(studentStream2.collect(Collectors.summarizingDouble(Student::getScore)));
        //输出结果为数组 内置元素可以直接.出来： DoubleSummaryStatistics{count=4, sum=156.500000, min=9.000000, average=39.125000, max=99.000000}
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
