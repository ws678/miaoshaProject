package com.miaoshaproject.controller;

import java.util.*;

/**
 * @Author wangshuo
 * @Date 2022/4/27, 20:11
 * 测试freemarker和controller返回
 * <p>
 * 算法
 */
public class TestController {

    static class Solution {

        public static void main(String[] args) {

            //测试Scanner
            /*Scanner sc = new Scanner(System.in);
            int[] a = new int[sc.nextInt()];
            for (int i = 0; i < a.length; i++) {
                a[i] = sc.nextInt();
            }
            for (int i : a) {
                System.out.println(i);
            }*/
            //两数之和
            /*int[] ss = {4, 5, 9, 10, 7, 12};
            int[] x = twoSum(ss, 15);
            for (int i : x) {
                System.out.println(i);
            }*/
            //两数相加
            /*ListNode listNode = addTwoNumbers(
                    new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))))),
                    new ListNode(9, new ListNode(9, new ListNode(9)))
            );
            System.out.println(listNode.toString());*/
            //无重复字符的最长子串
            /*int fheiwwaasjdwe = lengthOfLongestSubstring("fheiwwaasjdwe");
            System.out.println(fheiwwaasjdwe);*/
            //寻找两个有序数组的中位数
            /*double medianSortedArrays = findMedianSortedArrays(new int[]{4,5,9,14}, new int[]{2,3,6,7,8});
            System.out.println(medianSortedArrays);*/
        }

        //两数之和 O(N)
        public static int[] twoSum(int[] nums, int target) {

            int[] a = new int[2];
            HashMap<Integer, Integer> hash = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (hash.containsKey(nums[i])) {

                    a[0] = i;
                    a[1] = hash.get(nums[i]);
                    return a;
                }
                hash.put(target - nums[i], i);
            }
            return a;
        }

        //链表两数相加 O(max(m,n))
        public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode head = null, tail = null;
            int carry = 0;
            while (l1 != null || l2 != null) {
                int n1 = l1 != null ? l1.val : 0;
                int n2 = l2 != null ? l2.val : 0;
                int sum = n1 + n2 + carry;
                if (head == null)
                    head = tail = new ListNode(sum % 10);
                else {
                    tail.next = new ListNode(sum % 10);
                    tail = tail.next;
                }
                carry = sum / 10;
                if (l1 != null)
                    l1 = l1.next;
                if (l2 != null)
                    l2 = l2.next;
            }
            if (carry > 0)
                tail.next = new ListNode(carry);
            return head;
        }

        //无重复字符的最长子串HashMap O(N)
        public int lengthOfLongestSubstring_88(String s) {

            HashMap<Character, Integer> hashMap = new HashMap<>();
            int max = 0, start = 0;
            for (int end = 0; end < s.length(); end++) {

                char charAt = s.charAt(end);
                if (hashMap.containsKey(charAt))
                    start = Math.max(hashMap.get(charAt) + 1, start);
                max = Math.max(max, end - start + 1);
                hashMap.put(charAt, end);
            }
            return max;
        }

        //无重复最长子串 O(N)
        public static int lengthOfLongestSubstring(String s) {

            int[] last = new int[128];
            for (int i = 0; i < 128; i++) {
                last[i] = -1;
            }
            int n = s.length();
            int res = 0, start = 0;
            for (int i = 0; i < n; i++) {

                int index = s.charAt(i);
                start = Math.max(start, last[index] + 1);
                res = Math.max(res, i - start + 1);
                last[index] = i;
            }
            return res;
        }

        //寻找两个正序数组的中位数---(官方解法1)二分法 O（log（m+n））
        public static double findMedianSortedArrays_A(int[] nums1, int[] nums2) {
            int length1 = nums1.length, length2 = nums2.length;
            int totalLength = length1 + length2;
            if (totalLength % 2 == 1) {
                int midIndex = totalLength / 2;
                return getKthElement(nums1, nums2, midIndex + 1);
            } else {
                int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
                return (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
            }
        }

        private static int getKthElement(int[] nums1, int[] nums2, int k) {

            /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
             * 这里的 "/" 表示整除
             * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
             * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
             * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
             * 这样 pivot 本身最大也只能是第 k-1 小的元素
             * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
             * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
             * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
             */
            int length1 = nums1.length, length2 = nums2.length;
            int index1 = 0, index2 = 0;
            int kthElement = 0;
            while (true) {
                //边界情况
                if (index1 == length1)
                    return nums2[index2 + k - 1];
                if (index2 == length2)
                    return nums1[index1 + k - 1];
                if (k == 1)
                    return Math.min(nums1[index1], nums2[index2]);
                //正常情况
                int half = k / 2;
                int newIndex1 = Math.min(index1 + half, length1) - 1;
                int newIndex2 = Math.min(index2 + half, length2) - 1;
                int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
                if (pivot1 <= pivot2) {
                    k -= (newIndex1 - index1 + 1);
                    index1 = newIndex1 + 1;
                } else {
                    k -= (newIndex2 - index2 + 1);
                    index2 = newIndex2 + 1;
                }
            }
        }

        //寻找两个有序数组的中位数----二分法 O（log（min（m,n）））
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int m = nums1.length;
            int n = nums2.length;
            //保证m<=n
            if (m > n)
                return findMedianSortedArrays(nums2, nums1);
            int iMin = 0, iMax = m;
            while (iMin <= iMax) {
                int i = (iMin + iMax) / 2;
                int j = (m + n + 1) / 2 - i;
                if (j != 0 && i != m && nums1[i] < nums2[j - 1]) {// i需要增大
                    iMin = i + 1;
                } else if (i != 0 && j != n && nums1[i - 1] > nums2[j]) {// i需要减小
                    iMax = i - 1;
                } else {
                    //达到要求，将边界条件拿出来单独考虑
                    int maxLeft = 0;
                    if (i == 0)
                        maxLeft = nums2[j - 1];
                    else if (j == 0)
                        maxLeft = nums1[i - 1];
                    else
                        maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);

                    if ((m + n) % 2 != 0)
                        return maxLeft;//奇数的话不需要考虑右半部分

                    int minRight = 0;
                    if (i == m)
                        minRight = nums2[j];
                    else if (j == n)
                        minRight = nums1[i];
                    else
                        minRight = Math.min(nums1[i], nums2[j]);

                    return (maxLeft + minRight) / 2.0;//偶数的话返回结果
                }
            }
            return 0.0;
        }
    }

    /*
        ListNode链表
        int类型val
        next指向ListNode类
        重写toString
     */
    public static class ListNode {

        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }

    //二分查找O(log n)
    public int BinarySearch(int[] nums, int val) {

        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            if (nums[mid] == val)
                return mid;
            else if (nums[mid] < val)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return -1;
    }

    //合并两个排序的链表(迭代) O(m+m)
    public ListNode margeTwoListsDD(ListNode ln1, ListNode ln2) {
        ListNode listNode = new ListNode(-1);
        ListNode preList = listNode;
        while (ln1 != null && ln2 != null) {
            if (ln1.val <= ln2.val) {
                preList.next = ln1;
                ln1 = ln1.next;
            } else {
                preList.next = ln2;
                ln2 = ln2.next;
            }
            preList = preList.next;
        }
        //while过后ln1或者ln2至多还有一个节点没有被合并
        preList.next = ln1 == null ? ln2 : ln1;
        return listNode.next;
    }
    //合并两个有序的链表（递归）
    /*public ListNode margeTwoListsDG(ListNode ln1, ListNode ln2){

    }*/
}
