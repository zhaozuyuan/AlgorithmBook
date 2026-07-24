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
            int actual = numTrees(n, new java.util.HashMap<>());
            String pass = actual == expected ? "PASS" : "FAIL";
            System.out.printf("n=%-2d  预期=%-12d  实际=%-12d  %s%n",
                    n, expected, actual, pass);
        }
    }

    // 不需要回溯树的结构，而是方程推导
    // 整个树可以拆分为最小子结构，n == 0、n == 1、n == 2、n == 3
    // 加上缓存后，时间复杂度为 O(n^2)
    private static int numTrees(int n, Map<Integer, Integer> cache) {
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (n == 3) {
            return 5;
        }

        // f(1)=1, f(2)=2, f(3)=5
        // 树的最小组成单位就这三种
        int count = 0;
        for (int i = 0; i < n; ++i) {
            int leftCount = i;
            int rightCount = n - i - 1; // 减1是为了去掉根节点
            count += (numTrees(leftCount, cache) * numTrees(rightCount, cache));
        }

        cache.put(n, count);
        return count;
    }
}
