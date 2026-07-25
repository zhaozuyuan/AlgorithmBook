package com.zy.algorithm.book;

public class Day26_0725 {

    /**
     * leetcode.72 题目描述
     * 给你两个单词 word1 和 word2，请返回将 word1 转换成 word2 所使用的最少操作数。
     * 你可以对一个单词进行如下三种操作：
     *
     * - 插入一个字符
     * - 删除一个字符
     * - 替换一个字符
     *
     * 示例 1：
     * 输入：word1 = "horse", word2 = "ros"
     * 输出：3
     * 解释：
     * horse -> rorse (将 'h' 替换为 'r')
     * rorse -> rose (删除 'r')
     * rose  -> ros  (删除 'e')
     *
     * 示例 2：
     * 输入：word1 = "intention", word2 = "execution"
     * 输出：5
     * 解释：
     * intention -> inention (删除 't')
     * inention  -> enention (将 'i' 替换为 'e')
     * enention  -> exention (将 'n' 替换为 'x')
     * exention  -> exection (将 'n' 替换为 'c')
     * exection  -> execution (插入 'u')
     */
    public static void main(String[] args) {
        // 测试用例：{word1, word2, 预期结果}
        String[][] cases = {
                {"horse",     "ros",        "3"},
                {"intention", "execution",  "5"},
                {"abc",       "abc",        "0"},
                {"",          "abc",        "3"},
                {"abc",       "",           "3"},
                {"a",         "b",          "1"},
                {"ab",        "a",          "1"},
                {"a",         "ab",         "1"},
                {"abc",       "def",        "3"},
                {"abcde",     "ace",        "2"},
                {"sea",       "eat",        "2"},
                {"algorithm", "altruistic", "6"},
        };

        System.out.println("word1       word2        预期  实际  结果");
        System.out.println("----------  -----------  ---  ---  ----");
        for (String[] c : cases) {
            String word1 = c[0];
            String word2 = c[1];
            int expected = Integer.parseInt(c[2]);
            int actual = minCount(word1, word2);
            String pass = actual == expected ? "PASS" : "FAIL";
            System.out.printf("%-10s  %-11s  %3d  %3d  %s%n",
                    word1.isEmpty() ? "\"\"" : word1,
                    word2.isEmpty() ? "\"\"" : word2,
                    expected, actual, pass);
        }
    }

    // 关键点在于，先删后加，是两步操作，直接转换是一步操作，所以不能找出最大公共子序列之后再去计算，而是要实时计算。
    // 注意，动态规划最重要的是找出状态转移方程，而不是靠自己脑力推导流程
    private static int minCount(String word1, String word2) {
        // 代表最小操作次数
        // 状态转移方程
        //   如果 word1[i-1] == word2[j-1] , dp[i][j] = dp[i-1][j-1]
        //   如果 word1[i-1] != word2[j-1] , dp[i][j] = dp[i-1][j]  直接等于上一次当 word2[j-1] 的转换次数 + 多删除一次的次数
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        // 关键是，需要手动补齐 0-i, 0-j 单边的最小操作次数
        // 这和最大公共子序列不一样，最大公共子序列单边本来就是0
        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 考虑新增、删除、转换3种情况
                    dp[i][j] = Math.min(Math.min(dp[i-1][j-1], dp[i-1][j]), dp[i][j-1]) + 1;
                }
            }
        }

        return dp[word1.length()][word2.length()];
    }
}
