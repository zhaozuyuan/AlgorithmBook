package com.zy.algorithm.book;

public class Day26_0724_v3 {

    /**
     * leetcode.583 题目描述
     * 给定两个单词 word1 和 word2，返回使得 word1 和 word2 相同所需的最小步数。
     * 每步 可以删除任意一个字符串中的一个字符。
     *
     * 示例 1：
     * 输入: word1 = "sea", word2 = "eat"
     * 输出: 2
     * 解释: 第一步将 "sea" 变为 "ea" ，第二步将 "eat" 变为 "ea"
     *
     * 示例 2：
     * 输入：word1 = "leetcode", word2 = "etco"
     * 输出：4
     */
    public static void main(String[] args) {
        // 测试用例：{word1, word2, 预期结果}
        String[][] cases = {
                {"sea",     "eat",      "2"},
                {"leetcode","etco",     "4"},
                {"abc",     "abc",      "0"},
                {"a",       "b",        "2"},
                {"abc",     "def",      "6"},
                {"a",       "a",        "0"},
                {"ab",      "a",        "1"},
                {"a",       "ab",       "1"},
                {"abcde",   "ace",      "2"},
                {"abcd",    "bcde",     "2"},
                {"park",    "spake",    "3"},
        };

        System.out.println("word1       word2       预期  实际  结果");
        System.out.println("----------  ----------  ---  ---  ----");
        for (String[] c : cases) {
            String word1 = c[0];
            String word2 = c[1];
            int expected = Integer.parseInt(c[2]);
            int actual = minCount(word1, word2);
            String pass = actual == expected ? "PASS" : "FAIL";
            System.out.printf("%-10s  %-10s  %3d  %3d  %s%n",
                    word1, word2, expected, actual, pass);
        }
    }

    // 核心思路：动态规划求**最大公共子序列**，计算每个 dp[i][j] 的最大子序列，i代表word1的长度，j代表word2的长度
    private static int minCount(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                // 状态转移方程
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return word1.length() + word2.length() - 2 * dp[word1.length()][word2.length()];
    }
}
