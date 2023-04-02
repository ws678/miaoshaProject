package com.miaoshaproject.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class a07_TestForkJoin {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumRecursive sumRecursive = new SumRecursive(0, 10000);
        Long invoke = forkJoinPool.invoke(sumRecursive);
        System.out.println(invoke);
    }
}

//创建一个求和任务类 继承RecursiveTask重写compute方法
class SumRecursive extends RecursiveTask<Long> {

    //拆分的临界值
    private static final long THRESHOLD = 3000L;
    //起始值
    private final long start;
    //结束值
    private final long end;

    public SumRecursive(long start, long end) {
        this.start = start;
        this.end = end;
    }
    @Override
    protected Long compute() {
        //计算或者拆分
        long sum = 0;
        long length = end - start;
        if (length < THRESHOLD) {
            //计算
            for (long i = start; i < end; i++) {
                sum += i;
            }
            return sum;
        } else {
            //长度超过临界值 进行拆分 若过长会进行递归处理
            long middle = (start + end) / 2;
            SumRecursive left = new SumRecursive(start, middle);
            left.fork();
            SumRecursive right = new SumRecursive(middle + 1, end);
            right.fork();
            return left.join() + right.join();//求和
        }
    }
}
