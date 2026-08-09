package com.zy.algorithm.book;

import java.util.Arrays;

public class Day26_0809_v3 {

    /**
     * leetcode.912 排序数组
     * 给你一个整数数组 nums，请你将该数组升序排列。
     * 你必须在不使用任何内置函数的情况下解决问题，时间复杂度为 O(n log n)，并且空间复杂度尽可能小。
     *
     * 示例 1：
     * 输入：nums = [5,2,3,1]
     * 输出：[1,2,3,5]
     */
    public static void main(String[] args) {
        test("示例1", new int[]{5, 2, 3, 1}, new int[]{1, 2, 3, 5});
        test("示例2", new int[]{5, 1, 1, 2, 0, 0}, new int[]{0, 0, 1, 1, 2, 5});
        test("单元素", new int[]{1}, new int[]{1});
        test("双元素正序", new int[]{1, 2}, new int[]{1, 2});
        test("双元素逆序", new int[]{2, 1}, new int[]{1, 2});
        test("三元素正序", new int[]{1, 2, 3}, new int[]{1, 2, 3});
        test("三元素逆序", new int[]{3, 2, 1}, new int[]{1, 2, 3});
        test("含重复元素", new int[]{2, 1, 3, 1, 2}, new int[]{1, 1, 2, 2, 3});
        test("全重复元素", new int[]{7, 7, 7}, new int[]{7, 7, 7});
        // 卡死反例：base 同时出现在两个指针停止位，修复前会死循环
        test("卡死反例1", new int[]{2, 2, 3, 2}, new int[]{2, 2, 2, 3});
        test("卡死反例2", new int[]{2, 3, 2, 2}, new int[]{2, 2, 2, 3});
        test("卡死反例3", new int[]{2, 1, 2, 2}, new int[]{1, 2, 2, 2});
        test("含负数", new int[]{-3, 0, 5, -1}, new int[]{-3, -1, 0, 5});
        test("五元素正序", new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5});
        test("五元素逆序", new int[]{5, 4, 3, 2, 1}, new int[]{1, 2, 3, 4, 5});
    }

    // 排序并打印预期、实际结果
    private static void test(String name, int[] nums, int[] expected) {
        int[] actual = nums.clone();
        quickSort(actual);
        boolean pass = Arrays.equals(expected, actual);
        System.out.println("【" + name + "】" + (pass ? "通过" : "未通过"));
        System.out.println("  输入: " + Arrays.toString(nums));
        System.out.println("  预期: " + Arrays.toString(expected));
        System.out.println("  实际: " + Arrays.toString(actual));
    }

    // 快速排序
    // 选择一个基准值，将数组分为两部分，左边小于基准值，右边大于基准值
    private static void quickSort(int[] nums) {
        swapSection(nums, 0, nums.length - 1);
    }

    // [left,right] 区间内进行交换
    private static void swapSection(int[] nums, int left, int right) {
        if (right <= left) {
            return;
        }
        if (right == left + 1) {
            if (nums[left] > nums[right]) {
                swap(nums, left, right);
            }
            return;
        }

        // 基准值直接取第0位，至少需要3个元素才能启动
        int base = nums[left];
        int i = left + 1, j = right;
        while (true) {
            int iValue = nums[i];
            int jValue = nums[j];
            // 注意，iValue 和 jValue 必须有一个判断 == ，不然 [2,2,2] 这种的数组就会死循环
            while (iValue <= base && i < j) {
                ++i;
                iValue = nums[i];
            }
            while (jValue > base && j > i) {
                --j;
                jValue = nums[j];
            }
            if (i == j) {
                break;
            } else {
                swap(nums, i, j);
            }
        }

        // 例子，[3,2,1]、[1,-1,2,3]
        // 只有 > 的时候才交换
        if (base > nums[i]) {
            swap(nums, left, i);
        }
        // i 一定 > left，所以是 i - 1，不然可能死循环
        swapSection(nums, left, i-1);
        swapSection(nums, i, right);
    }

    private static void swap(int[] nums, int i, int j) {
        int n = nums[i];
        nums[i] = nums[j];
        nums[j] = n;
    }
}
