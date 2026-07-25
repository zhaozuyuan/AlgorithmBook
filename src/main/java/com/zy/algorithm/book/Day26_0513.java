package com.zy.algorithm.book;

public class Day26_0513 {

    /**
     * leetcode.3724
     * 给你两个整数数组 nums1（长度 n）和 nums2（长度 n+1），要用最少操作把 nums1 变成 nums2。
     * 每次可以选一个下标 i，执行以下操作之一：
     * - nums1[i] 增加 1
     * - nums1[i] 减少 1
     * - 将 nums1[i] 追加 到数组末尾
     */
    public static void main(String[] args) {
        System.out.println("result:" + (fillNums2(new int[]{1,3,6}, new int[]{2,4,5,3}) == 4));
    }

    // 核心思路，贪心算法，找到本次的最优解
    private static int fillNums2(int[] nums1, int[] nums2) {
       int targetNum = nums2[nums2.length - 1];
       int targetMinDiffValue = Integer.MAX_VALUE;
       int count = 0;
       for (int i = 0; i < nums1.length; i++) {
           count += Math.abs(nums1[i] - nums2[i]);
           if (targetMinDiffValue > 0) {
               if (nums1[i] <= nums2[i] && nums1[i] <= targetNum && targetNum <= nums2[i]) {
                   targetMinDiffValue = 0;
               }
               if (nums1[i] >= nums2[i] && nums1[i] >= targetNum && targetNum >= nums2[i]) {
                   targetMinDiffValue = 0;
               }
               if (targetMinDiffValue != 0) {
                   int diffValue = Math.min(Math.abs(targetNum-nums1[i]), Math.abs(targetNum-nums2[i]));
                   if (diffValue < targetMinDiffValue) {
                       targetMinDiffValue = diffValue;
                   }
               }
           }
       }

       // 1 代表拷贝到 nums2 最后一位的操作
       return count + 1 + targetMinDiffValue;
    }
}
