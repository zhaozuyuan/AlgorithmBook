package com.zy.algorithm.book;

import java.util.*;

public class Day26_0727 {

    /**
     * 题目描述
     * 给定一个整数数组 hours，表示以小时为单位的时间。返回满足 i < j 且 hours[i] + hours[j] 构成整天的下标对 (i, j) 的数目。
     * 整天定义：时间长度恰好是 24 的整数倍（如 24 小时为 1 天，48 小时为 2 天，72 小时为 3 天，依此类推）。
     * 示例 1：
     * 输入：hours = [12,12,30,24,24]
     * 输出：2
     * 解释：构成整天的下标对是 (0, 1) 和 (3, 4)。
     *
     * 示例 2：
     * 输入：hours = [72,48,24,3]
     * 输出：3
     * 解释：构成整天的下标对是 (0, 1)、(0, 2) 和 (1, 2)。
     */
    public static void main(String[] args) {
        // 测试用例：{输入 hours, 预期对数}
        // 覆盖：题目示例、单元素、自互补余数(0/12)、非自互补余数(如 6↔18)、大数取余、全相同
        Object[][] cases = {
                {new int[]{12, 12, 30, 24, 24}, 2},       // 示例1
                {new int[]{72, 48, 24, 3}, 3},            // 示例2，全 24 倍数 + 一个 3
                {new int[]{24}, 0},                       // 单元素
                {new int[]{12, 12}, 1},                   // 12+12=24
                {new int[]{1, 23}, 1},                    // 1+23=24
                {new int[]{48, 48, 48}, 3},               // C(3,2)
                {new int[]{13, 11, 13, 11}, 4},           // 非自互补余数 13↔11
                {new int[]{6, 18}, 1},                    // 非自互补余数 6↔18
                {new int[]{6, 18, 6, 18}, 4},             // 6↔18 交叉配对
                {new int[]{24, 24, 24, 24}, 6},           // C(4,2)
                {new int[]{1, 1, 1}, 0},                  // 无法配成整天
                {new int[]{1_000_000_007, 1}, 1},         // 大数取余：1e9+7 % 24 = 23，23+1=24
        };

        int pass = 0;
        for (int t = 0; t < cases.length; t++) {
            int[] input = ((int[]) cases[t][0]).clone(); // 副本，保持原样用于打印
            int expected = (int) cases[t][1];
            int actual = findPairs(input);
            boolean ok = actual == expected;
            if (ok) pass++;

            System.out.println("用例 " + (t + 1) + "：" + Arrays.toString((int[]) cases[t][0]));
            System.out.println("  预期对数 = " + expected);
            System.out.println("  实际对数 = " + actual);
            System.out.println("  结果：" + (ok ? "PASS ✅" : "FAIL ❌"));
            System.out.println();
        }
        System.out.println("通过 " + pass + "/" + cases.length + " 个用例");
    }

    // 暴力回溯，O(n^2)
    // 找规律，这种题目核心思路肯定是 hash，所以需要转一层得到 hash 表。
    // 两个数相加得到 24 的整数，大于 24 的数字直接对 24 取余 + 匹配的数字 == 24 （注意0也算整数）
    // 而且，如果都是 24 以内，就可以用一个长度为 24 的数组存储结果
    private static int findPairs(int[] hours) {
        int[] targetHourCount = new int[24];
        int result = 0;
        for (int hour : hours) {
            // 都转换成余数计算
            int h = hour % 24;
            int targetH = (24 - h) % 24;
            // 关键，直接记录目标次数，从0开始
            int targetCount = targetHourCount[targetH];
            result += targetCount;
            // 注意，这里记录的是自身出现的次数
            targetHourCount[h] = targetHourCount[h] + 1;
        }
        return result;
    }
}
