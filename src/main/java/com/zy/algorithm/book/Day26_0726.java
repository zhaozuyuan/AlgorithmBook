package com.zy.algorithm.book;

public class Day26_0726 {

    /**
     * leetcode.5 题目描述
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     *
     * 示例
     * 示例 1：
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是有效答案。
     *
     * 示例 2：
     * 输入：s = "cbbd"
     * 输出："bb"
     */
    public static void main(String[] args) {
        // 测试用例：{输入, 预期结果}
        // 预期结果仅为"其中一个"合法答案；存在多个等长回文时以长度为准
        String[][] cases = {
                {"babad", "bab"},      // 奇数长度，"aba" 亦合法
                {"cbbd", "bb"},        // 偶数长度，长度 2
                {"a", "a"},            // 单字符
                {"ac", "a"},           // 无回文，返回任一单字符
                {"abbac", "abba"},     // 偶数长度回文，长度 4（Bug1 反例）
                {"axbya", "a"},        // 内层断开、外层相等，长度 1（Bug2 反例）
                {"bananas", "anana"},  // 奇数长度回文，长度 5
                {"abacaba", "abacaba"},// 整串即为回文，长度 7
                {"xabay", "aba"},      // 奇数长度回文，长度 3
                {"aaaa", "aaaa"},      // 全相同字符，长度 4
        };

        int passed = 0;
        for (String[] c : cases) {
            String input = c[0];
            String expected = c[1];
            String actual = longestSymmetricStringByDP(input);
            // 校验：actual 必须是回文，且长度等于"真实最长回文长度"（暴力参考解）
            boolean pass = isPalindrome(actual)
                    && actual.length() == bruteLongestLength(input);
            if (pass) passed++;
            System.out.printf("输入: %-10s | 预期: %-9s | 实际: %-9s | %s%n",
                    "\"" + input + "\"",
                    "\"" + expected + "\"",
                    "\"" + actual + "\"",
                    pass ? "✅" : "❌");
        }
        System.out.printf("%n通过: %d/%d%n", passed, cases.length);
    }

    // 判断 s 是否为回文
    private static boolean isPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--)) {
                return false;
            }
        }
        return true;
    }

    // 暴力参考解：枚举所有子串，返回最长回文子串的长度（O(n^3)，仅用于测试校验）
    private static int bruteLongestLength(String s) {
        int best = 1;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                if (isPalindrome(s.substring(i, j))) {
                    best = Math.max(best, j - i);
                }
            }
        }
        return best;
    }

    // 回文子串的要点，中心扩展
    private static String longestSymmetricString(String s) {
        String result = s.substring(0, 1);
        for (int i = 0; i < s.length(); ++i) {
            // 情况1，奇数长度
            for (int j = i + 1; j < s.length(); ++j) {
                int left = 2*i - j;
                int right = j;
                if (left < 0 || s.charAt(left) != s.charAt(right)) {
                    break;
                }
                int subLength = right - left + 1;
                if (subLength > result.length()) {
                    result = s.substring(left, right + 1);
                }

            }

            // 情况2，偶数长度
            for (int j = i; j < s.length(); ++j) {
                int left = 2*i - j - 1;
                int right = j;
                if (left < 0 || s.charAt(left) != s.charAt(right)) {
                    break;
                }
                int subLength = right - left + 1;
                if (subLength > result.length()) {
                    result = s.substring(left, right + 1);
                }
            }
        }
        return result;
    }

    // 动态规划
    // dp[i][j] 代表字符串subString(i,j+1)是否回文子串
    //   dp[i][j] = s[j] == s[j] && dp[i+1][j-1] == true
    // 关键是怎么遍历i、j，因为历史值 dp[i+1][j-1]，所以 i 只能从大往小找，j只能从小(i)往大找
    private static String longestSymmetricStringByDP(String s) {
        int maxSubLength = 1;
        int maxSubStartIndex = 0;
        int maxSubEndIndex = 0;

        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int i = s.length() - 1; i >= 0 ; --i) {
            for (int j = i; j < s.length(); ++j) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j - i <= 1) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i+1][j-1];
                    }
                } else {
                    dp[i][j] = false;
                }

                if (dp[i][j] && j - i + 1 > maxSubLength) {
                    maxSubLength = j - i + 1;
                    maxSubStartIndex = i;
                    maxSubEndIndex = j;
                }
            }
        }
        return s.substring(maxSubStartIndex, maxSubEndIndex + 1);
    }
}
