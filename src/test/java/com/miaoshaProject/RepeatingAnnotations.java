package com.miaoshaProject;

import java.lang.annotation.*;

/*
JDK8新特性
 */
public class RepeatingAnnotations {
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Filters {
        Filter[] value();
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Filters.class)
    public @interface Filter {
        String value();

        String value2();
    }

    @Filter(value = "filter1", value2 = "111")
    @Filter(value = "filter2", value2 = "222")
    //@Filters({@Filter(  value="filter1",value2="111" ),@Filter(  value="filter2", value2="222")})
    //注意：JDK8之前：1.没有@Repeatable2.采用本行“注解容器”写法
    public interface Filterable {
    }

    public static void main(String[] args) {
        //获取注解后遍历打印值
        for (Filter filter : Filterable.class.getAnnotationsByType(Filter.class)) {
            System.out.println(filter.value() + filter.value2());
        }
    }
}
