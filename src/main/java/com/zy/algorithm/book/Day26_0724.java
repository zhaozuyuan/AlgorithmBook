package com.zy.algorithm.book;

import java.util.Map;

public class Day26_0724 {

    /**
     * leetcode.96 不同的二叉搜索树

     * 题目描述
     * 给你一个整数 n，求恰由 n 个节点组成、节点值从 1 到 n 互不相同的二叉搜索树（BST）有多少种结构上不同的形态。返回这个数目。
     * 示例
     * 示例 1：
     * 输入：n = 3
     * 输出：5
     *
     * 示例 2：
     * 输入：n = 1
     * 输出：1
     */
    public static void main(String[] args) {
        // 测试用例：n -> 预期结果（卡特兰数列：1,1,2,5,14,42,...）
        int[][] cases = {
                {0, 1},   // 空树算一种结构
                {1, 1},
                {2, 2},
                {3, 5},
                {4, 14},
                {5, 42},
                {6, 132},
                {19, 1767263190},
        };

        for (int[] c : cases) {
            int n = c[0];
            int expected = c[1];
            int actual = numTrees(n);
            String pass = actual == expected ? "PASS" : "FAIL";
            System.out.printf("n=%-2d  预期=%-12d  实际=%-12d  %s%n",
                    n, expected, actual, pass);
        }
    }

    // 核心思路，节点数为 n 时，假设左子节点为i，右子节点就为 n-i-1，i为[0,n-1]
    // 动态规划
    //   dp[n] = (dp[0] * dp[n-1]) + (dp[1] * dp[n-2]) + ... + (dp[n-1] * dp[0])
    private static int numTrees(int n) {
        if (n < 2) {
            return 1;
        }

        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; ++i) {

            // (dp[0] * dp[n-1]) + (dp[1] * dp[n-2]) + ... + (dp[n-1] * dp[0])
            int result = 0;
            for (int j = 0; j < i; ++j) {
                result += (dp[j] * dp[i - j - 1]);
            }
            dp[i] = result;
        }
        return dp[n];
    }
}
