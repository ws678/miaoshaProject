package com.miaoshaproject.shujujiegou;

import java.util.Scanner;

/**
 * @Author wangshuo
 * @Date 2022/4/30, 10:19
 * pta数据结构与算法练习题
 */
public class Test {

    //1.复杂度1 最大子列和问题
    public static void main1(String[] args) {

        Scanner sc = new Scanner(System.in);
        int pre = 0, maxSubArr = 0, count = sc.nextInt();
        for (int i = 0; i < count; i++) {
            pre += sc.nextInt();
            if (pre <= 0)
                pre = 0;
            else {
                if (pre > maxSubArr)
                    maxSubArr = pre;
            }
        }
        sc.close();
        System.out.println(maxSubArr);
    }

    //2.复杂度2 找到最大子列的第一个和最后一个数字
    public static void main2(String[] args) {

        boolean allminus = true;//全部小于0标识
        int n, nowsum = 0, maxsum = -1, l = 0, r = 0, first = 0, last = 0;
        int[] a = new int[10001];
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        sc.close();
        for (int i = 0; i < n; i++) {

            if (a[i] >= 0)
                allminus = false;
            nowsum += a[i];
            if (maxsum < nowsum && nowsum >= 0) {
                maxsum = nowsum;
                r = i;
                first = l;
                last = r;
            } else if (nowsum < 0) {
                nowsum = 0;
                l = r = i + 1;
            }
        }
        if (allminus) {
            System.out.println(0 + " " + a[0] + " " + a[n - 1]);
        } else
            System.out.println(maxsum + " " + a[first] + " " + a[last]);
    }

    /* C 二分查找算法
    Position BinarySearch( List L, ElementType X ) {
        if (L->Last == 0)
            return NotFound;
        int l, r, mid, ans;
        l = 1, r = L->Last;
        while(l <= r) {
            mid = (l + r) / 2;
            if(L->Data[mid] == X) {
                ans = mid;
                return ans;
            } else if(L->Data[mid] < X) {
                l = mid + 1;
            } else
                r = mid - 1;
        }
        return NotFound;
    }
     */

    /* C 线性结构 两个有序链表的合并
    List Merge( List L1, List L2 ) {
        List p1,p2,p3,T;
        T = (List) malloc(sizeof(struct Node));
        p3 = T;
        p1 = L1->Next;
        p2 = L2->Next;
        while (p1 && p2) {  //p1和p2都存在时
            if (p1->Data <= p2->Data) {
                p3->Next = p1;
                p3 = p3->Next;
                p1 = p1->Next;
            } else {
                p3->Next = p2;
                p3 = p3->Next;
                p2 = p2->Next;
            }
        }
        if (p1) {//哪个还存在哪个后边的就全接到p3上
            p3->Next = p1;
        } else
            p3->Next = p2;
        //裁判会检查原链表，这里置空
        L1->Next = NULL;
        L2->Next = NULL;
        return T;
    }
     */

    public static void main(String[] args) {

    }
}
