package com.zy.algorithm.book;

import java.util.HashSet;
import java.util.List;

public class Day25_0212 {

    /**
     * <a href="https://leetcode.cn/problems/word-break/description/">139. 单词拆分</a>
     * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
     * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
     *
     * 思路：动态规划，对于n字符串，它一定等于某两个子串相加，那就是计算这两个子串的可达性
     */
    public static boolean groupWord(String s, List<String> word) {
        // 可达标记，从0开始，位置0一定可达
        boolean[] reachableMark = new boolean[s.length() + 1];
        reachableMark[0] = true;

        HashSet<String> wordSet = new HashSet<>(word);
        // 计算位置i是否可达
        for (int i = 0; i < s.length(); ++i) {
            // 遍历子串，位置i的字符串 = 两个子串相加
            for (int j = 0; j < i; ++j) {
                // 子串1可达 && 子串2可达
                if (reachableMark[j] && wordSet.contains(s.substring(j, i + 1))) {
                    reachableMark[i + 1] = true;
                    break;
                }
            }
        }
        return reachableMark[s.length()];
    }
}
