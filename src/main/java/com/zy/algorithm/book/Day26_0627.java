package com.zy.algorithm.book;

import java.util.Map;
import java.util.HashMap;


public class Day26_0627 {

    /**
     * leetcode 454
     *   给你四个整数数组 nums1、nums2、nums3 和 nums4，数组长度都是 n，请你计算有多少个元组 (i, j, k, l) 能满足：
     *
     *   - 0 <= i, j, k, l < n
     *   - nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
     *
     *   示例 1：
     *   输入：nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
     *   输出：2
     *   解释：
     *   1. (0, 0, 0, 1) -> 1 + (-2) + (-1) + 2 = 0
     *   2. (1, 1, 0, 0) -> 2 + (-1) + (-1) + 0 = 0
     *
     *   示例 2：
     *   输入：nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
     *   输出：1
     *
     *   约束：
     *   - n == nums1.length == nums2.length == nums3.length == nums4.length
     *   - 1 <= n <= 200
     *   - -2^28 <= nums1[i], nums2[i], nums3[i], nums4[i] <= 2^28
     * @param args
     */
    public static void main(String[] args) {
        // 示例1
        int[] nums1 = {1, 2};
        int[] nums2 = {-2, -1};
        int[] nums3 = {-1, 2};
        int[] nums4 = {0, 2};
        System.out.println("示例1 result: " + fourSumCount(nums1, nums2, nums3, nums4) + " (expected: 2)");

        // 示例2
        System.out.println("示例2 result: " + fourSumCount(
                new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0}
        ) + " (expected: 1)");

        // 测试3：无匹配
        System.out.println("测试3 result: " + fourSumCount(
                new int[]{1}, new int[]{2}, new int[]{3}, new int[]{4}
        ) + " (expected: 0)");

        // 测试4：全0数组，结果应为 n^4（每种组合都满足）
        // n=2 => 2^4=16
        System.out.println("测试4 result: " + fourSumCount(
                new int[]{0, 0}, new int[]{0, 0}, new int[]{0, 0}, new int[]{0, 0}
        ) + " (expected: 16)");

        // 测试5：正负配对
        System.out.println("测试5 result: " + fourSumCount(
                new int[]{-1, -1}, new int[]{1, 1}, new int[]{-1, -1}, new int[]{1, 1}
        ) + " (expected: 16)");
    }

    // hash 用 key 决定 value ，所以把4个数降到2个数就行
    // nums1 和 nums2 计算和1，nums3 和 nums4 计算和2，再根据和1计算和2是否存在
    private static int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> sums1 = getTotalSums(nums1, nums2);
        Map<Integer, Integer> sums2 = getTotalSums(nums3, nums4);

        int result = 0;
        for (Map.Entry<Integer, Integer> entry1: sums1.entrySet()) {
            Integer sum2Count = sums2.get(-entry1.getKey());
            if (sum2Count != null) {
                // 注意是次数乘积
                result += entry1.getValue() * sum2Count;
            }
        }
        return result;
    }

    /**
     * 获取所有的和，key 是和的值，value 是和出现的次数
     * @return
     */
    private static Map<Integer, Integer> getTotalSums(int[] nums1, int[] nums2) {
        Map<Integer, Integer> sums = new HashMap<>();
        for (int k : nums1) {
            for (int v : nums2) {
                int sum = k + v;
                sums.put(sum, 1 + sums.getOrDefault(sum, 0));
            }
        }
        return sums;
    }
}
