package com.zy.algorithm.book;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day26_0729_v2 {

    /**
     * 题目描述
     * 给你一个下标从 0 开始的整数数组 nums。如果 i < j 且 j - i != nums[j] - nums[i]，那么我们称 (i, j) 是一个 坏数对。
     * 请你返回 nums 中 坏数对 的总数目。
     *
     * 示例 1：
     * 输入：nums = [4,1,3,3]
     * 输出：5
     * 解释：
     * 数对 (0, 1) 是坏数对，因为 1 - 0 != 1 - 4
     * 数对 (0, 2) 是坏数对，因为 2 - 0 != 3 - 4, 2 != -1
     * 数对 (0, 3) 是坏数对，因为 3 - 0 != 3 - 4, 3 != -1
     * 数对 (1, 2) 是坏数对，因为 2 - 1 != 3 - 1, 1 != 2
     * 数对 (2, 3) 是坏数对，因为 3 - 2 != 3 - 3, 1 != 0
     * 总共有 5 个坏数对，所以返回 5。
     */
    public static void main(String[] args) {
        runTest(new int[]{4, 1, 3, 3}, 5);
        runTest(new int[]{1, 2, 3, 4, 5}, 0);
        runTest(new int[]{5}, 0);
        runTest(new int[]{1, 1, 1, 1}, 6);
        runTest(new int[]{1, 2, 1, 2}, 4);
        runTest(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 0);
    }

    private static void runTest(int[] nums, int expected) {
        System.out.println("输入: " + Arrays.toString(nums));
        System.out.println("预期: " + expected);
        int actual = findBadPairs(nums);
        System.out.println("实际: " + actual);
        System.out.println("结果: " + (actual == expected ? "✅ 通过" : "❌ 未通过"));
        System.out.println();
    }

    // 正常做，两个 for 循环就搞定了，所以应该有优化思路
    // 推导公式：=> j - i != nums[j] - nums[i] => j - nums[j] != i - nums[i]
    // 再结合 hash，找到相等的，剩下都是不相等的组合
    private static int findBadPairs(int[] nums) {
        // key diff, value count
        Map<Integer, Integer> map = new HashMap<>();
        int result = 0;
        int totalCount = 0;
        for (int i = 0; i < nums.length; i++) {
            int diff = i - nums[i];
            int equalCount = map.get(diff) == null ? 0 : map.get(diff);
            int badCount = totalCount - equalCount;
            map.put(diff, equalCount + 1);
            result += badCount;
            ++totalCount;
        }
        return result;
    }
}
