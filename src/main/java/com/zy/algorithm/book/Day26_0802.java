package com.zy.algorithm.book;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day26_0802 {

    /**
     * leetcode.1400 给你一个字符串 s 和一个整数 k，如果用 s 中的所有字符可以构造 k 个非空回文字符串，则返回 true，否则返回 false。
     * 示例：
     * - s = "annabelle", k = 2 → true（可构造 "anna" + "elble"）
     * - s = "leetcode", k = 3 → false
     * - s = "true", k = 4 → true（每个字符单独一个串）
     */
    public static void main(String[] args) {
        // {s, k, 预期结果}
        Object[][] cases = {
                {"annabelle", 2, true},   // 官方示例 1："anna" + "elble"
                {"leetcode", 3, false},   // 官方示例 2
                {"true", 4, true},        // 官方示例 3：每个字符单独一组
                {"a", 1, true},           // 单字符本身就是回文
                {"a", 2, false},          // k 超过字符串长度
                {"aa", 1, true},          // "aa" 整体一个回文
                {"aa", 2, true},          // "a" + "a"
                {"aa", 3, false},         // k 超过字符串长度
                {"abc", 1, false},        // 3 个奇数次字符，1 个回文放不下
                {"abc", 3, true},         // "a" + "b" + "c"
                {"abab", 1, true},        // "abba"
                {"aabb", 4, true},        // "a" + "a" + "b" + "b"
                {"leetcode", 1, false},   // 官方示例 2 的 k 改为 1
                {"annabelle", 9, true},   // k 等于字符串长度
                {"aabbbc", 1, false},     // a:2, b:3, c:1 → 2 个奇数字符，1 个回文放不下
                {"aabbbc", 2, true},      // k 恰好等于奇数字符数（下界）
                {"aabbbc", 3, true},      // 介于下界和上界之间
                {"zzzz", 1, true},        // 单字符重复 4 次，无奇数字符
                {"zzzz", 4, true},        // k = 字符串长度
                {"abcdef", 5, false},     // 6 个互不相同的字符，k 小于奇数字符数
                {"abcdef", 6, true},      // k = 字符串长度
        };
        for (Object[] c : cases) {
            String s = (String) c[0];
            int k = (int) c[1];
            boolean expected = (boolean) c[2];
            boolean actual = hasKSymmetricString(s, k);
            System.out.printf("s=%-10s k=%-2d 预期:%s 实际:%s %s%n",
                    s, k, expected, actual, expected == actual ? "✅ 通过" : "❌ 失败");
        }
    }

    // 回文子串不要求长度，不能暴力枚举，不然指定超限
    // 找规律，1、对称坐标，两个字符一定相同。2、中心坐标可以是独立的字符，也可以是相同字符拆开用。
    // [最少组装的回文子串,最多组装的回文子串]
    private static boolean hasKSymmetricString(String s, int k) {
        // 找出成对出现的字符，aa、aaaa 这种没有本质差别
        Set<Character> symmetricChars = new HashSet<>();
        Set<Character> independentChars = new HashSet<>();
        for (char c : s.toCharArray()) {
            Character cObj = (Character) c;
            if (independentChars.contains(cObj)) {
                independentChars.remove(cObj);
                symmetricChars.add(cObj);
            } else {
                independentChars.add(cObj);
            }
        }

        // 找最少组装次数的回文子串，[aacaa, d]、[aca、ada] 这种没有本质差别
        // 最少的回文子串个数 == 1(所有能成对的字符+1个独立字符) + 所有独立字符-1
        int minCount = 1 + independentChars.size() - 1;
        int maxCount = s.length();

        return k >= minCount && k <= maxCount;
    }
}
