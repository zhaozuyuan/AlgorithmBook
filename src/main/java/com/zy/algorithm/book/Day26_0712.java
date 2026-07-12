package com.zy.algorithm.book;

import java.util.HashMap;

public class Day26_0712 {

    /**
     * leetcode 309
     * 题目描述
     *
     * 给定一个数组 prices，其中 prices[i] 是第 i 天的股票价格。
     *
     * 找出你能获得的最大利润。你可以完成任意多笔交易（即多次买入和卖出一股股票），但有以下限制：
     *
     * - 卖出股票后，第二天不能买入股票（即冷冻期 1 天）。
     *
     * 注意：你不能同时参与多笔交易（即必须在再次买入前卖出股票）。
     *
     * 示例 1：
     * 输入: prices = [1,2,3,0,2]
     * 输出: 3
     * 解释: 交易 = [买入, 卖出, 冷冻期, 买入, 卖出]
     *
     * 示例 2：
     * 输入: prices = [1]
     * 输出: 0
     */
    public static void main(String[] args) {
        // 测试用例1：示例 [1,2,3,0,2]，预期输出 3
        int[] prices1 = {1, 2, 3, 0, 2};
        int result1 = getMaxProfit(prices1, 0, 0, Integer.MIN_VALUE, Integer.MIN_VALUE, new HashMap<>());
        System.out.println("测试用例1: prices = [1,2,3,0,2]");
        System.out.println("结果: " + result1 + " (预期: 3)");
        System.out.println();

        // 测试用例2：单日 [1]，预期输出 0
        int[] prices2 = {1};
        int result2 = getMaxProfit(prices2, 0, 0, Integer.MIN_VALUE, Integer.MIN_VALUE, new HashMap<>());
        System.out.println("测试用例2: prices = [1]");
        System.out.println("结果: " + result2 + " (预期: 0)");
    }

    // 思路: 动态规划，输入第n天前的买入/卖出状态，计算第n天可能产生的最大利润
    // 注意: 最大利润需要缓存，不然就变成了纯暴力检索
    private static int getMaxProfit(
            int[] prices,
            int n,
            // 之前的最大利益
            int preMaxProfit,
            // 前一个买入位置，>= 0 视为买入
            int boughtIndex,
            // 前一个卖出位置
            int soldIndex,
            HashMap<String, Integer> incrementalProfits  // map 还有优化空间，目前命中率较低
    ) {
        if (n == prices.length) {
            return preMaxProfit;
        }

        String key = n + "_" + boughtIndex + "_" + soldIndex;
        if (incrementalProfits.containsKey(key)) {
            return incrementalProfits.get(key) + preMaxProfit;
        }

        int maxProfit = preMaxProfit;
        if (boughtIndex >= 0) { // 已买入的状态
            // 考虑卖出
            int soldProfit = maxProfit + prices[n];
            maxProfit = getMaxProfit(prices, n+1, soldProfit, Integer.MIN_VALUE, n, incrementalProfits);
        } else {  // 已卖出的状态，
            if (soldIndex != n - 1) {  // 前一天必须是未卖出状态，才能买入
                // 考虑买入
                int boughtProfit = maxProfit - prices[n];
                maxProfit = getMaxProfit(prices, n+1, boughtProfit, n, Integer.MIN_VALUE, incrementalProfits);
            }
        }
        // 考虑冷冻
        maxProfit = Math.max(maxProfit, getMaxProfit(prices, n+1, preMaxProfit, boughtIndex, soldIndex, incrementalProfits));
        incrementalProfits.put(key, maxProfit - preMaxProfit);
        return maxProfit;
    }
}
