package com.zy.algorithm.book;

import java.util.*;

public class Day26_0730_v2 {

    /**
     * leetcode.76 最小覆盖子串
     *
     * 给定两个长度分别为 m 和 n 的字符串 s 和 t，返回 s 中最小覆盖子串，使得该子串包含 t 中的每个字符（包括重复字符）。如果不存在这样的子串，返回空字符串 ""。
     * 保证测试用例的答案是唯一的。
     *
     * 示例 1：
     * 输入：s = "ADOBECODEBANC", t = "ABC"
     * 输出："BANC"
     * 解释：最小覆盖子串 "BANC" 包含 t 中的 'A'、'B'、'C'。
     *
     * 示例 2：
     * 输入：s = "a", t = "a"
     * 输出："a"
     * 解释：整个字符串 s 就是最小覆盖子串。
     */
    public static void main(String[] args) {
        // 示例与边界用例：打印预期结果与实际结果
//        test("ADOBECODEBANC", "ABC", "BANC");      // 官方示例 1
        test("a", "a", "a");                        // 官方示例 2
        test("a", "aa", "");                        // 官方示例 3
        test("aa", "aa", "aa");                     // 恰好两个相同字符
        test("ab", "b", "b");                       // 单字符目标
        test("abc", "ac", "abc");                   // 需跳过中间字符
        test("abc", "d", "");                       // 目标字符不存在
        test("cabwefgewcwaefgcf", "cae", "cwae");   // 经典用例
        test("aaflslflsldkalskdkjff", "aa", "aa");  // 多个重复字符
    }

    private static void test(String s, String t, String expected) {
        String actual = minSubstring(s, t);
        boolean pass = expected.equals(actual);
        System.out.println("s = \"" + s + "\",  t = \"" + t + "\"");
        System.out.println("  预期结果: \"" + expected + "\"");
        System.out.println("  实际结果: \"" + actual + "\"");
        System.out.println("  " + (pass ? "✓ 通过" : "✗ 未通过"));
        System.out.println();
    }

    // 关键是，求 s 中的连续子串，可以考虑滑动窗口
    // 滑动规则，
    //  1. 右指针移动：当前求得的目标字符长度 < t 的长度，右指针右移
    //  2. 左指针移动：当前求得的目标字符长度 == t 的长度，左指针右移
    private static String minSubstring(String s, String t) {
        // t 字符是无序的，所以直接 hash 处理
        int targetCount = t.length();
        // key char, value 出现次数
        Map<Character, Integer> targetMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetMap.put(c, targetMap.getOrDefault(c, 0) + 1);
        }

        int i = 0, j = 0;
        int validCount = 0;  // 有效字符数
        int minSubstringStartIndex = 0;
        int minSubstringLength = Integer.MAX_VALUE;
        for (; j < s.length() ; ++j) {
            if (targetMap.containsKey(s.charAt(j))) {
                int needCount = targetMap.get(s.charAt(j)) - 1;
                validCount = validCount + (needCount < 0 ? 0 : 1);
                targetMap.put(s.charAt(j), needCount);
            }

            while (targetCount == validCount) {
                if ((j - i + 1) < minSubstringLength) {
                    minSubstringStartIndex = i;
                    minSubstringLength = j - i + 1;
                }
                if (targetMap.containsKey(s.charAt(i))) {
                    int needCount = targetMap.get(s.charAt(i)) + 1;
                    validCount = validCount - (needCount > 0 ? 1 : 0);
                    targetMap.put(s.charAt(i), needCount);
                }
                ++i;
            }
        }

        return minSubstringLength < Integer.MAX_VALUE ? s.substring(minSubstringStartIndex, minSubstringStartIndex + minSubstringLength) : "";
    }
}
