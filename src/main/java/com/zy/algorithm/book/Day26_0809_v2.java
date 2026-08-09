package com.zy.algorithm.book;

import java.util.Arrays;

public class Day26_0809_v2 {

    /**
     * leetcode.945 使数组唯一的最小增量（Minimum Increment to Make Array Unique）
     * 题目描述：
     * 给你一个整数数组 nums。每次操作中，你可以选择一个下标 i（0 <= i < nums.length），并将 nums[i] 加 1。
     * 返回使 nums 中所有值互不相同所需的最少操作次数。
     * 示例 1：
     * 输入：nums = [1,2,2]
     * 输出：1
     * 解释：经过 1 次操作，数组可以是 [1, 2, 3]。
     */
    public static void main(String[] args) {
        // 示例 1
        runTest(new int[] {1, 2, 2}, 1);
        // 示例 2
        runTest(new int[] {3, 2, 1, 2, 1, 7}, 6);
        // 三个相同元素
        runTest(new int[] {1, 1, 1}, 3);
        // 全部相同
        runTest(new int[] {2, 2, 2, 2}, 6);
        // 本身无重复
        runTest(new int[] {5, 3, 1}, 0);
        // 含 0 且冲突
        runTest(new int[] {0, 0, 1}, 2);
        // 单元素边界
        runTest(new int[] {1}, 0);
    }

    private static void runTest(int[] nums, int expected) {
        System.out.print("输入: " + Arrays.toString(nums));
        int actual = minActionCount(nums);
        System.out.println(" | 预期: " + expected + " | 实际: " + actual
                + (actual == expected ? "  ✅" : "  ❌"));
    }

    // 先排序，找规律
    //   1,1,2 => 1,2,3
    //   1,1,1 => 1,2,1 => 1,2,3
    private static int minActionCount(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        for (int i = 1; i < nums.length; ++i) {
            int diff = nums[i] - nums[i-1];
            if (diff <= 0) {
                count = count - diff + 1;
                nums[i] = nums[i-1] + 1;
            }
        }
        return count;
    }
}
