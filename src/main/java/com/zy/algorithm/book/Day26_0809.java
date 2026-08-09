package com.zy.algorithm.book;

import java.util.Arrays;

public class Day26_0809 {

    /**
     * leetcode.462 最小操作次数使数组元素相等 II（中等 · 数组 / 数学 / 排序）
     * 给你一个长度为 n 的整数数组 nums，返回使所有数组元素相等需要的最小操作次数。
     * 在一次操作中，你可以将数组中任意一个元素加一或减一。测试用例设计保证答案在 32 位整数范围内。
     *
     * 示例 1：
     * 输入：nums = [1,2,3]
     * 输出：2
     * 解释：
     * 只需要两次操作（记住每次操作只能对其中一个元素加一或减一）：
     * [1,2,3] => [2,2,3] => [2,2,2]
     */
    public static void main(String[] args) {
        int passed = 0;
        int total = 0;
        System.out.println("=== Day26_0809 462.最小操作次数使数组元素相等 II 测试 ===");
        passed += test("示例1（[1,2,3]）", new int[]{1, 2, 3}, 2, total = total + 1, total);
        passed += test("示例2（[1,10,2,9]）", new int[]{1, 10, 2, 9}, 16, total = total + 1, total);
        passed += test("反例（均值147，中位数100）", new int[]{1, 2, 3, 100}, 100, total = total + 1, total);
        passed += test("单元素", new int[]{5}, 0, total = total + 1, total);
        passed += test("全部相等", new int[]{3, 3, 3}, 0, total = total + 1, total);
        passed += test("非对称（[1,1,1,10,100]）", new int[]{1, 1, 1, 10, 100}, 108, total = total + 1, total);
        passed += test("偶数长度（[1,2,100,100]）", new int[]{1, 2, 100, 100}, 197, total = total + 1, total);
        passed += test("两个元素", new int[]{1, 100}, 99, total = total + 1, total);
        passed += test("含负数（[-3,-1,0,2]）", new int[]{-3, -1, 0, 2}, 6, total = total + 1, total);
        passed += test("大数值边界", new int[]{1000000000, -1000000000}, 2000000000, total = total + 1, total);
        System.out.println("=== 汇总：" + passed + "/" + total + " 通过 ===");
    }

    private static int test(String name, int[] nums, int expected, int index, int total) {
        int actual = minActionCount(nums);
        boolean ok = expected == actual;
        System.out.printf("[%d/%d] %s：预期 %d，实际 %d %s%n", index, total, name, expected, actual, ok ? "✅ 通过" : "❌ 失败");
        return ok ? 1 : 0;
    }

    // 找规律，最小次数，应当是所有元素尽可能少操作，小的元素往大移动，大的元素往小的移动
    // 可能1，平均值，1,2,3 => 平均值3，次数为3，错误
    // 可能2，中位数，1,2,3 => 中位值2，次数为2，正确
    //    1,1,1,10,100 => 中位数1，正确
    //    1,2,100,100 => 中位数2，正确
    private static int minActionCount(int[] nums) {
        // 排序后找中位数
        Arrays.sort(nums);
       int mid = nums[nums.length / 2];
       int distance = 0;
       for (int i = 0; i < nums.length; i++) {
           distance += Math.abs(nums[i] - mid);
       }
       return distance;
    }
}
