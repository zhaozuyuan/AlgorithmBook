package com.zy.algorithm.book;


public class Day26_0721 {

    /**
     * leetcode.1723
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
            sMinTime = Integer.MAX_VALUE;

            int actual = minTime(jobs, k);

            String jobsStr = java.util.Arrays.toString(jobs);
            boolean pass = actual == expected;
            System.out.printf("Case %d: jobs=%s, k=%d | expected=%d, actual=%d %s%n",
                t + 1, jobsStr, k, expected, actual, pass ? "✓" : "✗ FAIL");
        }
    }


    private static int minTime(int[] jobs, int k) {

        // 剪枝，倒序排列，更早的触发“当前工人的工作量已经大于最优解，剩下的探索都没有意义”条件
        java.util.Arrays.sort(jobs);
        int[] newJobs = new int[jobs.length];
        for (int i = 0; i < jobs.length; i++) {
            newJobs[i] = jobs[jobs.length - 1 - i];
        }

        // 剪枝：sMinTime 用随机算出来的一个组合的最大值。但实现比较麻烦

        int[] personTimeArray = new int[k];
        computeMinTime(personTimeArray, newJobs, k, 0);
        return sMinTime;
    }

    private static int sMinTime = Integer.MAX_VALUE;

    // 关键思路：暴力回溯+剪枝，回溯所有的组合，得出最优解，时间复杂度最差为 O(k^n)
    private static void computeMinTime(int[] personTimeArray, int[] jobs, int k, int index) {
        // 代表任务分配完成，然后计算本次是否为最优解
        if (index == jobs.length) {
            // 耗时最长的工作，是不是所有组合中最小的
            int maxTime = Integer.MIN_VALUE;
            for (int time : personTimeArray) {
                if (time > maxTime) {
                    maxTime = time;
                }
            }
            if (maxTime < sMinTime) {
                sMinTime = maxTime;
            }
//            System.out.println(java.util.Arrays.toString(personTimeArray));
            return;
        }

        // 每个人的累积工作时间
        // 假设当前工作分别分配到每个工人时，最佳值是多少
        for (int i = 0; i < k; i++) {
            int time = jobs[index];
            int personTime = personTimeArray[i];

            // 剪枝：当前工人的工作量已经大于最优解，剩下的探索都没有意义
            if (personTimeArray[i] + time > sMinTime) {
                continue;
            }

            // 剪枝：相邻两个人的工作量一致，则新增工作放在谁身上都是一样的，都是求后续的最佳组合
            if (i > 0 && personTimeArray[i] == personTimeArray[i-1]) {
                continue;
            }

            personTimeArray[i] = personTime + time;
            // 递归回溯，直到本次的工作分配完成
            computeMinTime(personTimeArray, jobs, k, index + 1);

            // 还原状态，分配下一名工人
            personTimeArray[i] = personTime;
        }
    }
}
