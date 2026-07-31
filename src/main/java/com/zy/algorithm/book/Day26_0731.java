package com.zy.algorithm.book;

import java.util.Arrays;

public class Day26_0731 {

    /**
     * leetcode.3551 题目描述
     *
     * 给定一个由互不相同的正整数组成的数组 nums。需要根据每个数字的数位和对数组进行升序排序；若两个数字的数位和相同，则较小的数字排在前面。
     * 返回将 nums 重排为该排序顺序所需的最小交换次数。
     * 一次交换定义为：交换数组中两个不同位置上的值。
     *
     * 示例 1
     * - 输入：nums = [37, 100]
     * - 输出：1
     * - 解释：数位和为 [10, 1]，按数位和排序得 [100, 37]，交换一次即可。
     *
     * 示例 2
     * - 输入：nums = [22, 14, 33, 7]
     * - 输出：0
     * - 解释：数位和为 [4, 5, 6, 7]，数组已是有序，无需交换。
     * @param args
     */
    public static void main(String[] args) {
        int[][] testCases = {
                {37, 100},              // 示例 1
                {22, 14, 33, 7},        // 示例 2
                {18, 43, 34, 16},       // 示例 3
                {5},                    // 边界：单元素
                {3, 2, 1},
                {2, 3, 1},
                {5, 4, 3, 2, 1},
                {30, 20, 10}
        };
        int[] expected = {
                1,
                0,
                2,
                0,
                1,
                2,
                2,
                1
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            int actual = minChangeCount(nums);
            boolean pass = actual == expected[i];
            System.out.println("用例 " + (i + 1) + ": nums=" + Arrays.toString(nums)
                    + " | 预期=" + expected[i] + " | 实际=" + actual
                    + (pass ? " ✅" : " ❌"));
        }
    }

    // 不能用常规排序思路处理
    // 贪心算法，每次把最大的交换到最后去，这样交换后的位置是固定的，不需要再次交换
    private static int minChangeCount(int[] args) {
        int[] nums = new int[args.length];
        System.arraycopy(args, 0, nums, 0, args.length);

        int[] sums = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            sums[i] = getSum(nums[i]);
        }

        int result = 0;
        int maxSumIndex = nums.length - 1;
        for (int i = 0; i < nums.length; ++i) {
            int currentMaxSumIndex = 0;
            for (int j = 1; j < maxSumIndex; ++j) {
                if (sums[j] > sums[currentMaxSumIndex] || (sums[j] == sums[currentMaxSumIndex] && nums[j] > nums[currentMaxSumIndex])) {
                    currentMaxSumIndex = j;
                }
            }
            if (sums[currentMaxSumIndex] > sums[maxSumIndex]
                    || (sums[currentMaxSumIndex] == sums[maxSumIndex] && nums[currentMaxSumIndex] > nums[maxSumIndex])) {
                 // currentMaxSumIndex 交换 maxSumIndex
                ++result;

                sums[currentMaxSumIndex] = sums[maxSumIndex];
                nums[currentMaxSumIndex] = nums[maxSumIndex];
            }

            --maxSumIndex;
        }
        return result;
    }

    private static int getSum(int num) {
        int sum = 0;
        while(num > 0) {
            sum = sum + (num % 10);
            num = num / 10;
        }
        return sum;
    }
}
