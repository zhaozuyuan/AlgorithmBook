package com.zy.algorithm.book;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Day26_0730 {

    /**
     * leetcode.560 题目描述
     * 给你一个整数数组 nums 和一个整数 k，请你统计并返回该数组中和为 k 的连续子数组的个数。
     * 子数组是数组中连续非空的元素序列。
     *
     * 示例 1：
     * 输入：nums = [1,1,1], k = 2
     * 输出：2
     *
     * 示例 2：
     * 输入：nums = [1,2,3], k = 3
     * 输出：2
     */
    public static void main(String[] args) {
        runTest(new int[]{1, 1, 1}, 2, 2);
        runTest(new int[]{1, 2, 3}, 3, 2);
        runTest(new int[]{1}, 1, 1);
        runTest(new int[]{1, 2, 3}, 7, 0);
        runTest(new int[]{1, -1, 0}, 0, 3);
        runTest(new int[]{0, 0, 0}, 0, 6);
        runTest(new int[]{1, 2, 1, 2, 1}, 3, 4);
        // 更多负数 / 0 用例
        runTest(new int[]{-1, -1, 1}, 0, 1);
        runTest(new int[]{-1, 1, -1, 1}, 0, 4);
        runTest(new int[]{0, -1, 1, 0}, 0, 6);
        runTest(new int[]{-2, -1, 0, 1, 2}, 0, 3);
        runTest(new int[]{0}, 0, 1);
        runTest(new int[]{-3, -3, -3}, -6, 2);
        runTest(new int[]{1, -1, 1, -1, 1}, 1, 6);
    }

    private static void runTest(int[] nums, int k, int expected) {
        System.out.println("输入: nums=" + Arrays.toString(nums) + ", k=" + k);
        System.out.println("预期: " + expected);
        int actual = kSubArrayCount(nums, k);
        System.out.println("实际: " + actual);
        System.out.println("结果: " + (actual == expected ? "✅ 通过" : "❌ 未通过"));
        System.out.println();
    }

    // 可能有负数或者0，不能用滑动窗口
    // 关键点：1、前缀和，避免重复求和；2、基于前缀和，求 j-i == k 的两个位置；3、基于 hash 快速找到目标面积
    private static int kSubArrayCount(int[] nums, int k) {
        // 把每个坐标的面积求出来
        int[] sums = new int[nums.length];
        sums[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sums[i] = sums[i-1] + nums[i];
        }

        // 记录每个 sum 出现的次数
        Map<Integer, Integer> sumCountMap = new HashMap<>();
        sumCountMap.put(0,1); // 处理 k == sum 的情况，也需要计数
        int result = 0;
        for (int sum : sums) {
            // 后续的index，只算和前面index的差值，这样可以防止重复的组合
            int targetSum = sum - k;
            result += sumCountMap.getOrDefault(targetSum, 0);
            sumCountMap.put(sum, sumCountMap.getOrDefault(sum, 0) + 1);
        }

        // todo 其实 sums 可以省略，但保留当前的写法便于理解

        return result;
    }
}
