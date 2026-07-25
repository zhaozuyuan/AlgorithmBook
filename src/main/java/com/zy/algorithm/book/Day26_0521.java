package com.zy.algorithm.book;

public class Day26_0521 {

    /**
     * leetcode.3584
     * 题目描述
     * 给你一个整数数组 nums 和一个整数 m。
     * 返回任意大小为 m 的 子序列 中首尾元素乘积的 最大值。
     * 子序列 是可以通过删除原数组中的一些元素（或不删除任何元素），且不改变剩余元素顺序而得到的数组。
     *
     * ---
     * 示例 1：
     * 输入：nums = [-1,-9,2,3,-2,-3,1], m = 1
     * 输出：81
     * 解释：子序列 [-9] 的首尾元素乘积最大：-9 * -9 = 81。
     * ▎ m=1 时，子序列只有一个元素，首尾都是它自己，即求 max(nums[i]²)。
     *
     * 示例 2：
     * 输入：nums = [1,3,-5,5,6,-4], m = 3
     * 输出：20
     * 解释：子序列 [-5, 6, -4] 的首尾元素乘积最大：-5 * -4 = 20。
     */
    public static void main(String[] args) {
        // 测试用例 1: 递增数组, m=2
        int[] nums1 = {1, 2, 3, 4, 5};
        System.out.println("nums1 = {1, 2, 3, 4, 5}, m=2, result = " + findMaxProduct(nums1, 2) + " (expected = 20, 首元素选4尾元素选5: 4*5=20)");

        // 测试用例 2: 递减数组, m=2
        int[] nums2 = {5, 4, 3, 2, 1};
        System.out.println("nums2 = {5, 4, 3, 2, 1}, m=2, result = " + findMaxProduct(nums2, 2) + " (expected = 20, 首元素选5尾元素选4: 5*4=20)");

        // 测试用例 3: 混合数组, m=2
        int[] nums3 = {1, 5, 2, 4, 3};
        System.out.println("nums3 = {1, 5, 2, 4, 3}, m=2, result = " + findMaxProduct(nums3, 2) + " (expected = 20, 首元素选5尾元素选4: 5*4=20)");

        // 测试用例 4: m=1, 首尾元素指向同一下标
        int[] nums4 = {3, 5, 8, 4};
        System.out.println("nums4 = {3, 5, 8, 4}, m=1, result = " + findMaxProduct(nums4, 1) + " (expected = 64, 首元素选8尾元素选8: 8*8=64)");

        // 测试用例 5: m=2, 示例数组
        int[] nums5 = {3, 5, 8, 4};
        System.out.println("nums5 = {3, 5, 8, 4}, m=2, result = " + findMaxProduct(nums5, 2) + " (expected = 40, 首元素选5尾元素选8: 5*8=40)");

        // 测试用例 8: 只有两个元素, m=1
        int[] nums8 = {7, 3};
        System.out.println("nums8 = {7, 3}, m=1, result = " + findMaxProduct(nums8, 1) + " (expected = 49, 首元素选7: 7*7=49)");

        // 测试用例 9: 所有元素相同
        int[] nums9 = {4, 4, 4, 4};
        System.out.println("nums9 = {4, 4, 4, 4}, m=2, result = " + findMaxProduct(nums9, 2) + " (expected = 16)");

        // 测试用例 10: 包含负数
        int[] nums10 = {-3, -5, 2, 4};
        System.out.println("nums10 = {-3, -5, 2, 4}, m=2, result = " + findMaxProduct(nums10, 2) + " (expected = 15, 首元素选-3尾元素选-5: (-3)*(-5)=15)");
    }


    // 很像暴力回溯，但时间复杂度是指数级
    // 应该先找规律，尾元素不断向后移动，计算它和最大、最小首元素的乘积
    private static int findMaxProduct(int[] nums, int m) {
        int maxHead = nums[0];
        int minHead = nums[0];
        int maxProduct = Integer.MIN_VALUE;
        for (int i = m - 1; i < nums.length; i++) {
            int newHead = nums[i + 1 - m];
            if (newHead > maxHead) {
                maxHead = newHead;
            } else if (newHead < minHead) {
                minHead = newHead;
            }

            int currentMaxProduct = nums[i] * (nums[i] > 0 ? maxHead : minHead);
            maxProduct = Math.max(maxProduct, currentMaxProduct);
        }
        return maxProduct;
    }
}
