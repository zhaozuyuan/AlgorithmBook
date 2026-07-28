package com.zy.algorithm.book;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Day26_0728 {

    /**
     * leetcode.1306
     * 题目描述
     * 给定一个非负整数数组 arr，你最开始位于该数组的 start 索引处。当你位于索引 i 处时，你可以跳到 i + arr[i] 或者 i - arr[i]。
     * 请判断自己是否能够到达任一存在 arr[i] = 0 的索引处。
     * 注意，任何时候你都不能跳到数组之外。
     *
     * 示例 1：
     * 输入：arr = [4,2,3,0,3,1,2], start = 5
     * 输出：true
     * 解释：
     * 到达值为 0 的索引 3 的所有可能路径为：
     * index 5 -> index 4 -> index 1 -> index 3
     * index 5 -> index 6 -> index 4 -> index 1 -> index 3
     */
    public static void main(String[] args) {
        // 测试用例：{arr, start, 预期结果}
        Object[][] cases = {
                {new int[]{4, 2, 3, 0, 3, 1, 2}, 5, true},   // 示例 1
                {new int[]{4, 2, 3, 0, 3, 1, 2}, 0, true},   // 示例 2
                {new int[]{3, 0, 2, 1, 2}, 2, false},        // 示例 3
                {new int[]{0}, 0, true},                     // 起点即为 0
                {new int[]{1}, 0, false},                    // 跳出数组，无法到达 0
                {new int[]{4, 2, 3, 0, 3, 1, 2}, 3, true},   // 起点本身值为 0
        };

        for (int i = 0; i < cases.length; i++) {
            int[] arr = (int[]) cases[i][0];
            int start = (int) cases[i][1];
            boolean expected = (boolean) cases[i][2];
            boolean actual = isReachable(arr, start);
            String result = expected == actual ? "PASS" : "FAIL";
            System.out.printf("用例 %d: 预期=%s, 实际=%s [%s]%n",
                    i + 1, expected, actual, result);
        }
    }

    // 核心思路，广度或者深度遍历，但不能遍历重复的 index
    private static boolean isReachable(int[] nums, int start) {
        // 广度遍历试试
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Integer index = queue.poll();
            if (nums[index] == 0) {
                return true;
            }

            int forward = index + nums[index];
            int backward = index - nums[index];
            if (forward < nums.length && nums[forward] != -1) {
                queue.add(forward);
            }
            if (backward >= 0 && nums[backward] != -1) {
                queue.add(backward);
            }

            nums[index] = -1; // 标记已扫描
        }
        return false;
    }
}
