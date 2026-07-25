package com.zy.algorithm.book;

import java.util.HashMap;

public class Day26_0712 {

    /**
     * leetcode.309
     * 题目描述
     *
     * 给定一个数组 prices，其中 prices[i] 是第 i 天的股票价格。
     * 找出你能获得的最大利润。你可以完成任意多笔交易（即多次买入和卖出一股股票），但有以下限制：
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
        // 测试用例：prices数组，预期结果
        int[][][] cases = {
            {{1, 2, 3, 0, 2}, {3}},          // 示例1：买入1→卖出2→冷冻→买入0→卖出2
            {{1},               {0}},          // 示例2：单日无法交易
            {{1, 2},            {1}},          // 简单：买入1→卖出2
            {{1, 2, 3},         {2}},          // 连续涨：买入1→卖出3
            {{2, 1, 4},         {3}},          // 先跌后涨：买入1→卖出4
            {{1, 2, 4},         {3}},          // 单调涨：买入1→卖出4
            {{3, 2, 6, 5, 0, 3},{7}},          // 多段交易：2→6 + 0→3 = 4+3=7
            {{2, 1},            {0}},          // 单边下跌：不交易
            {{1, 2, 3, 4, 5},   {4}},          // 连续涨5天：1→5
        };

        for (int c = 0; c < cases.length; c++) {
            int[] prices = cases[c][0];
            int expected = cases[c][1][0];
            int actual = getMaxProfit(prices);
            String pass = actual == expected ? "PASS" : "FAIL";
            System.out.printf("用例%d. %-25s  预期=%-2d  实际=%-2d  %s%n",
                    c + 1, java.util.Arrays.toString(prices), expected, actual, pass);
        }
    }

    /**
     * 思路：动态规划，当的状态是什么（已买入、已卖出？），再规划下一个状态，通过递归的方式求出所有状态下的利益，再取最大值
     *
     * dp[i][j] 表示第 i 天，状态为 j 时的最大利益
     * j = 0 不买
     * j = 1 买入
     * j = 2 卖出
     *
     * dp[i][j]
     *   j==0, dp[i][j] = 之前的最大利益
     *   j==1, dp[i][j] = max(dp[0..i-2][2]) 利益 && 满足冷冻期 - prices[i-1]
     *   j==2, dp[i][j] = 之前最大的买入利益 + prices[i-1]
     */
    private static int getMaxProfit(int[] prices) {
        // 划重点：为了方便理解动态规划，这里保留了 dp 数组，但最终可以去掉 dp 数组节省空间
        int[][] dp = new int[prices.length][3];

        // 补充第0天的数据
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[0][2] = 0;

        // 补充最大值
        int maxProfit = 0;
        int maxBuyingProfit = dp[0][1];
        int maxSellingProfit = 0;

        for (int i = 1; i < prices.length; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (j == 0) { // 不买入
                    dp[i][j] = maxProfit;
                } else if (j == 1) { // 买入
                    dp[i][j] = maxSellingProfit - prices[i];
                    if (maxBuyingProfit < dp[i][j]) {
                        maxBuyingProfit = dp[i][j];
                    }
                } else {
                    dp[i][j] = maxBuyingProfit + prices[i];
                }

                maxProfit = Math.max(maxProfit, dp[i][j]);
            }

            // 有冷冻期，所以不能记录今天的，而是必须记录前一天的
            maxSellingProfit = Math.max(maxSellingProfit, dp[i-1][2]);

        }
        return maxProfit;
    }
}
