package com.miaoshaproject.function;

import java.util.Arrays;
import java.util.function.Supplier;

/*
    使用Supplier接口返回数组的最大值
 */
public class SupplierTestDemo {

    public static void main(String[] args) {
        //使用Lambda表达式返回数组元素的最大值
        int[] a = {9,8,1,4,3,7};
        printMax(() -> {

            Arrays.sort(a);
            return a[a.length - 1];
        });
    }

    public static void printMax(Supplier<Integer> supplier) {

        Integer max = supplier.get();
        System.out.println("max:" + max);
    }
}
