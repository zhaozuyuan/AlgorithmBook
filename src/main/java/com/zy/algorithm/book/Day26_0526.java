package com.zy.algorithm.book;

import java.util.Arrays;
import java.util.List;

public class Day26_0526 {

    public static void main(String[] args) {
        System.out.println("result: " + Arrays.toString(findMaxSortedNumber(332)));
        System.out.println("result: " + Arrays.toString(findMaxSortedNumber(1234)));
        System.out.println("result: " + Arrays.toString(findMaxSortedNumber(10)));
    }

    /**
     * 当且仅当每个相邻位数上的数字 x 和 y 满足 x <= y 时，我们称这个整数是单调递增的。
     * 给定一个整数 n ，返回 小于或等于 n 的最大数字，且数字呈 单调递增 。
     * 案例，输入 332，返回 299
     * leetcode.738
     *
     */
    private static int[] findMaxSortedNumber(int num) {
        int[] nums = convertNumToArray(num);
        if (num < 10) {
            return nums;
        }
        if (nums.length < 3 && num % 10 < num / 10) {
            return new int[]{9};
        }

        // 因此求单调递增的数组
        for (int i = nums.length - 1; i > 0; --i) {
            if (nums[i] < nums[i-1]) {
                reduce1(nums, i - 1);
                reset9(nums, i);
            }
        }
        return nums;
    }

    private static void reset9(int[] nums, int startIndex) {
        for (int i = startIndex; i < nums.length; ++i) {
            nums[i] = 9;
        }
    }

    private static void reduce1(int[] nums, int startIndex) {
        for (int i = startIndex; i >= 0; --i) {
            int v = nums[i];
            if (v == 0) {
                nums[i] = 9;
            } else {
                nums[i] = v - 1;
                return;
            }
        }
    }

    private static int[] convertNumToArray(int num) {
        String numStr = String.valueOf(num);
        int[] nums = new int[numStr.length()];
        char[] cs = numStr.toCharArray();
        for (int i = 0; i < cs.length; ++i) {
            nums[i] = cs[i] - '0';
        }
        return nums;
    }
}
