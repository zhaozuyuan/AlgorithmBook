package com.zy.algorithm.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day26_0810 {

    /**
     * leetcode.131 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文串。返回 s 所有可能的分割方案。
     * 示例：
     *
     * 输入: s = "aab"
     * 输出: [["a","a","b"],["aa","b"]]
     */
    public static void main(String[] args) {
        // 测试用例：输入字符串 + 预期结果
        runCase("aab", Arrays.asList(
                Arrays.asList("a", "a", "b"),
                Arrays.asList("aa", "b")));
        runCase("a", Arrays.asList(
                Arrays.asList("a")));
        runCase("aa", Arrays.asList(
                Arrays.asList("a", "a"),
                Arrays.asList("aa")));
        runCase("aba", Arrays.asList(
                Arrays.asList("a", "b", "a"),
                Arrays.asList("aba")));
        runCase("aabb", Arrays.asList(
                Arrays.asList("a", "a", "b", "b"),
                Arrays.asList("a", "a", "bb"),
                Arrays.asList("aa", "b", "b"),
                Arrays.asList("aa", "bb")));
        runCase("ababa", Arrays.asList(
                Arrays.asList("a", "b", "a", "b", "a"),
                Arrays.asList("a", "b", "aba"),
                Arrays.asList("a", "bab", "a"),
                Arrays.asList("aba", "b", "a"),
                Arrays.asList("ababa")));
    }

    /**
     * 运行单个测试用例，打印预期结果与实际结果
     */
    private static void runCase(String s, List<List<String>> expected) {
        List<List<String>> actual = findAllSymmetricString(s);
        boolean pass = expected.equals(actual);
        System.out.println("输入 s = \"" + s + "\"");
        System.out.println("  预期: " + expected);
        System.out.println("  实际: " + actual);
        System.out.println("  判定: " + (pass ? "通过 ✓" : "失败 ✗"));
        System.out.println();
    }

    // 动态规划
    // dp[i][j] = str[i] == str[j] && dp[i+1][j-1]
    private static List<List<String>> findAllSymmetricString(String str) {
        // 动态规划，计算所有的回文子串
        boolean[][] dp = new boolean[str.length()][str.length()];
        for (int i = str.length(); i >= 0; --i) {
            for (int j = i; j < str.length(); ++j) {
                if (i == j) {
                    dp[i][j] = true;
                } else if (str.charAt(i) == str.charAt(j)) {
                    if (j == i + 1) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                } else {
                    dp[i][j] = false;
                }
            }
        }

        // 回溯，计算所有的组合
        List<List<String>> result = new ArrayList<>();
        backtrace(result, new ArrayList<>(), 0, str, dp);
        return result;
    }

    private static void backtrace(
            List<List<String>> allSubStrings,
            List<String> subStrings,
            int startIndex,
            String str,
            boolean[][] dp
    ) {
        if (startIndex == str.length()) {
            // 填充完一次集合
            allSubStrings.add(new ArrayList<>(subStrings));
            return;
        }

        // 找到所有 [startIndex, i] 为回文字符串的组合
        for (int i = startIndex; i < dp[startIndex].length; ++i) {
            if (dp[startIndex][i]) {
                subStrings.add(str.substring(startIndex, i + 1));
                // 填充剩下的位置
                backtrace(allSubStrings, subStrings, i + 1, str, dp);
                // 还原状态
                subStrings.remove(subStrings.size() - 1);
            }
        }
    }
}
