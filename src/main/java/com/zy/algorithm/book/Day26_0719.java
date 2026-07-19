package com.zy.algorithm.book;

import java.util.*;

public class Day26_0719 {

    /**
     * leetcode.139 单词拆分
     *   给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
     *
     *   注意： 不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
     *   示例 1：
     *   输入: s = "leetcode", wordDict = ["leet", "code"]
     *   输出: true
     *   解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
     *
     *   示例 2：
     *   输入: s = "applepenapple", wordDict = ["apple", "pen"]
     *   输出: true
     *   解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
     *        注意，你可以重复使用字典中的单词。
     */
    public static void main(String[] args) {
        // 测试用例 1：题目示例1 - 基本拆分
        runTest("示例1：基本拆分", "leetcode", Arrays.asList("leet", "code"), true);

        // 测试用例 2：题目示例2 - 重复使用字典单词
        runTest("示例2：重复使用单词", "applepenapple", Arrays.asList("apple", "pen"), true);

        // 测试用例 3：题目示例3 - 无法拆分
        runTest("示例3：无法拆分", "catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat"), false);

        // 测试用例 4：单个字符匹配
        runTest("单个字符匹配", "a", Arrays.asList("a"), true);

        // 测试用例 5：单个字符不匹配
        runTest("单个字符不匹配", "a", Arrays.asList("b"), false);

        // 测试用例 6：字典包含多余单词
        runTest("字典有多余单词", "cars", Arrays.asList("car", "ca", "rs", "hello", "world"), true);

        // 测试用例 7：前缀重叠（需正确选择分割点）
        runTest("前缀重叠-正确分割", "aaaaaaa", Arrays.asList("aaaa", "aaa"), true);

        // 测试用例 8：全字符串本身在字典中
        runTest("整个串在字典中", "hello", Arrays.asList("hello", "world"), true);

        // 测试用例 9：空字典（无法拆分非空串）
        runTest("空字典", "abc", Collections.emptyList(), false);

        // 测试用例 10：较长串 + 短单词拼接
        runTest("长串多词拼接", "aaaaaaaaab", Arrays.asList("a", "aa", "aaa", "b"), true);
    }

    private static void runTest(String name, String s, List<String> wordDict, boolean expected) {
        boolean actual = wordBreak(s, wordDict);
        String status = (actual == expected) ? "✅ PASS" : "❌ FAIL";
        System.out.printf("[%s] %s%n", status, name);
        System.out.printf("  s         = \"%s\"%n", s);
        System.out.printf("  wordDict  = %s%n", wordDict);
        System.out.printf("  expected  = %s%n", expected);
        System.out.printf("  actual    = %s%n", actual);
        System.out.println();
    }

    /**
     * 核心思路：动态规划，推导公式 n = x 子串是否可达 + (n - x) 且 (n - x) in wordDict
     */
    private static boolean wordBreak(String s, List<String> wordDict) {
        Boolean[] dp = new Boolean[s.length() + 1];
        dp[0] = true;


        return isDp(dp, s, s.length(), new HashSet<>(wordDict));
    }

    // dp[i] 表示 s 的前 i 个字符组成的字符串是否可达
    // 计算 s[0,length) 是否可达
    private static boolean isDp(
            Boolean[] dp,
            String s,
            int length,
            Set<String> wordDict
    ) {
        if (length < 1) {
            return true;
        }
        // i 代表字符串长度
        for (int i = (length - 1); i >= 0; --i) {
            String sub = s.substring(i, length);
            if (wordDict.contains(sub)) {
                // 复用缓存
                if (dp[i] == null) {
                    dp[i] = isDp(dp, s, i, wordDict);
                }
                if (dp[i]) {
                    dp[length] = true;
                    return true;
                }
            }
        }
        return false;
    }
}
