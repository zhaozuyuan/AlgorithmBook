package com.zy.algorithm.book;

public class Day26_0722 {

    /**
     * leetcode.2597 美丽子集的数目
     *
     * 题目描述
     * 给你一个由正整数组成的数组 nums 和一个 正 整数 k。
     * 如果 nums 的子集中，任意两个整数的绝对差均不等于 k，则认为该子集是一个 美丽 子集。
     * 返回数组 nums 中 非空 且 美丽 的子集数目。
     * nums 的子集定义为：可以经由 nums 删除某些元素（也可能不删除）得到的一个数组。只有在删除元素时选择的索引不同的情况下，两个子集才会被视作是不同的子集。
     *
     * 示例
     * 示例 1：
     * 输入：nums = [2,4,6], k = 2
     * 输出：4
     * 解释：数组 nums 中的美丽子集有：[2], [4], [6], [2, 6] 。
     *
     * 示例 2：
     * 输入：nums = [1], k = 1
     * 输出：1
     * 解释：数组 nums 中的美丽子集有：[1] 。
     */
    public static void main(String[] args) {
        // 示例 1
        testCase(new int[]{2, 4, 6}, 2, 4);
        // 示例 2
        testCase(new int[]{1}, 1, 1);
        // 连续相邻元素，差为 k 的对最多：合法子集 = {1},{2},{3},{1,3}
        testCase(new int[]{1, 2, 3}, 1, 4);
        // 重复元素：题目按"删除元素索引不同"区分子集，不同下标的相同值算不同子集；
        // 元素全为 2、k=1 时任意两数差为 0，所有非空子集都美丽 => 2^3 - 1 = 7
        testCase(new int[]{2, 2, 2}, 1, 7);
        // 较大 k、含两组冲突 (4,7) 与 (7,10)，合法非空子集数 = 9
        testCase(new int[]{10, 4, 5, 7}, 3, 9);
    }

    private static void testCase(int[] nums, int k, int expected) {
        // 注意：sCount 为静态字段且 beautifulSubsets 内部未重置，多次调用会累加，
        // 这里手动重置以隔离每个用例；建议在 beautifulSubsets 开头补 sCount = 0 彻底修复。
        int actual = beautifulSubsets(nums, k);
        System.out.println("nums=" + java.util.Arrays.toString(nums) + ", k=" + k
                + " | 预期输出=" + expected + ", 实际输出=" + actual
                + (actual == expected ? " ✅" : " ❌"));
    }

    // 暴力回溯
    private static int beautifulSubsets(int[] nums, int k) {
        // 先排序，便于顺序检索
        java.util.Arrays.sort(nums);
        sCount = 0;

        // 子集长度从 1 到 nums.length
        for (int subLength = 1; subLength <= nums.length; subLength++) {
            int[] indexSubsets = new int[subLength];
            computeSubsetsCount(nums, k, indexSubsets, 0);
        }
        return sCount;
    }

    private static int sCount = 0;

    // 先回溯所有的组合，再考虑剪枝
    private static void computeSubsetsCount(int[] nums, int k, int[] indexSubsets, int index) {
        // 重点，任意两个数字差值不为k，则该子集符合条件
        // k 为正整数，不为0

        // 填充完成一次美丽子集
        if (index == indexSubsets.length) {
            sCount++;
            return;
        }

        // 尝试填充 indexSubsets，里面存储的是 nums 下标，index 代表本次应该填充的 indexSubsets 下标
        for (int numIndex = index == 0 ? 0 : (indexSubsets[index - 1] + 1); numIndex < nums.length; numIndex++) {
            indexSubsets[index] = numIndex;

            if (index == 0 || isBeautifulSubset(nums, indexSubsets, index, k)) {
                computeSubsetsCount(nums, k, indexSubsets, index + 1);
            }

            // 还原，继续下一个
            indexSubsets[index] = -1;
        }
    }

    // 判断是否为美丽子集，只需要判断 lastIndex 新添加进来的元素是否为美丽子集，前面的子集之前已经判断过
    private static boolean isBeautifulSubset(int[] nums, int[] indexSubsets, int lastIndex, int k) {
        // 有序数组，考虑二分
        int lastNum = nums[indexSubsets[lastIndex]];
        // 绝对差
        int badNum = Math.max(0, lastNum - k);
        // 二分查找目标数字
        for (int i = 0, j = lastIndex; i < j;) {
            int mid = (i + j) / 2;
            int num = nums[indexSubsets[mid]];
            if (num == badNum) {
                return false;
            } else if (num > badNum) {
                j = mid;
            } else {
                i = mid + 1;
            }
        }

        return true;
    }
}
