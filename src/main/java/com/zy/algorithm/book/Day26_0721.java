package com.zy.algorithm.book;

import java.util.Map;

public class Day26_0721 {

    /**
     * leetcode.2284
     * 给定一个整数数组 jobs，其中 jobs[i] 是完成第 i 个任务所需的时间。有 k 个工人，每个任务必须分配给恰好一个工人。
     * 一个工人的工作时间是分配给他所有任务时间之和。设计最优分配方案，使任意工人的最大工作时间最小化，并返回这个最小值。
     *
     * 示例 1：
     * 输入: jobs = [3,2,3], k = 3
     * 输出: 3
     * 解释: 每人分配一个任务，最大时间为 3。
     *
     * 示例 2：
     * 输入: jobs = [1,2,4,7,8], k = 2
     * 输出: 11
     * 解释:
     *   工人1: 1, 2, 8 → 11
     *   工人2: 4, 7 → 11
     * 最大工作时间为 11。
     */
    public static void main(String[] args) {
        // 测试用例：{jobs数组, k, 预期结果}
        Object[][] testCases = {
            {new int[]{3, 2, 3}, 3, 3},
            {new int[]{1, 2, 4, 7, 8}, 2, 11},
            {new int[]{5, 4, 3}, 1, 12},
            {new int[]{1, 1, 1, 1}, 2, 2},
            {new int[]{10, 7, 5, 4, 3, 2}, 3, 11},
            {new int[]{12, 8, 7, 6, 5}, 3, 13},
            {new int[]{9, 9, 9, 9, 9}, 3, 18},
        };

        for (int t = 0; t < testCases.length; t++) {
            int[] jobs = (int[]) testCases[t][0];
            int k = (int) testCases[t][1];
            int expected = (int) testCases[t][2];

            // 重置全局状态
            maxTime = Integer.MAX_VALUE;
            minTime = Integer.MAX_VALUE;

            int actual = minTime(jobs, k);

            String jobsStr = java.util.Arrays.toString(jobs);
            boolean pass = actual == expected;
            System.out.printf("Case %d: jobs=%s, k=%d | expected=%d, actual=%d %s%n",
                t + 1, jobsStr, k, expected, actual, pass ? "✓" : "✗ FAIL");
        }
    }

    // 动态规划，当前的最小值 = 上次的分配情况 + 本次的情况
    private static int minTime(int[] jobs, int k) {
        // 人和当前工作量
        int[][] persons = new int[k][1];

        computeMinTime(persons, jobs, 0);
        return minTime;
    }

    // 需要最大时间最小化，也就是耗时最长的工人，他的耗时最小
    private static int maxTime = Integer.MAX_VALUE;
    private static int minTime = Integer.MAX_VALUE;

    private static void computeMinTime(int[][] persons, int[] jobs, int index) {
        if (index == jobs.length) {
            return;
        }

        int jobTime = jobs[index];
        // 遍历所有人，假设当前工作归它
        for (int i = 0; i < persons.length; ++i) {
            int time = persons[i][0];
            persons[i][0] = time + jobTime;
            computeMinTime(persons, jobs, index + 1);

            int[] minAndMaxTime = getMinAndMaxPersonTime(persons);
            // 需要最大时间最小化，也就是耗时最长的工人，他的耗时最小
            if (minAndMaxTime[1] < maxTime) {
                minTime = minAndMaxTime[0];
            }

            // 回退状态
            persons[i][0] = time;
        }
    }

    private static int[] getMinAndMaxPersonTime(int[][] persons) {
        int currentMinTime = Integer.MAX_VALUE;
        int currentMaxTime = Integer.MIN_VALUE;
        int[] result = new int[]{currentMinTime, currentMaxTime};
        for (int[] person : persons) {
            if (person[0] > currentMaxTime) {
                currentMaxTime = person[0];
            }
            if (person[0] < currentMinTime) {
                currentMinTime = person[0];
            }
        }
        return result;
    }
}
