package com.zy.algorithm.book;

import java.util.Arrays;
import java.util.Random;

public class Day26_0803_v2 {

    /**
     * leetcode.1109 航班预订统计（中等）
     *
     * 有 n 个航班，它们分别从 1 到 n 进行编号。
     * 有一份航班预订表 bookings，表中第 i 条预订记录
     * bookings[i] = [firstᵢ, lastᵢ, seatsᵢ] 意味着在从 firstᵢ 到 lastᵢ（包含 firstᵢ 和 lastᵢ）的每个航班上预订了 seatsᵢ 个座位。
     * 请你返回一个长度为 n 的数组 answer，里面的元素是每个航班预定的座位总数。
     *
     * 示例 1：
     * 输入：bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
     * 输出：[10,55,45,25,25]
     * 解释：
     * 航班编号        1   2   3   4   5
     * 预订记录 1 ：   10  10
     * 预订记录 2 ：       20  20
     * 预订记录 3 ：       25  25  25  25
     * 总座位数：      10  55  45  25  25
     *
     * 示例 2：
     * 输入：bookings = [[1,2,10],[2,2,15]], n = 2
     * 输出：[10,25]
     */
    public static void main(String[] args) {
        // 官方示例
        check(new int[][] {{1, 2, 10}, {2, 3, 20}, {2, 5, 25}}, 5, new int[] {10, 55, 45, 25, 25});
        check(new int[][] {{1, 2, 10}, {2, 2, 15}}, 2, new int[] {10, 25});

        // 边界用例
        check(new int[][] {{1, 1, 5}}, 1, new int[] {5}); // n = 1
        check(new int[][] {{1, 5, 7}}, 5, new int[] {7, 7, 7, 7, 7}); // 区间覆盖全部航班
        check(new int[][] {{3, 3, 9}, {3, 3, 2}, {3, 5, 1}}, 5, new int[] {0, 0, 12, 1, 1}); // 同一航班多条记录
        check(new int[][] {{2, 5, 8}, {1, 4, 3}}, 5, new int[] {3, 11, 11, 11, 8}); // 区间部分重叠

        // 随机用例与暴力解对拍（固定随机种子，可复现）
        Random random = new Random(42);
        int total = 100;
        int failed = 0;
        for (int t = 0; t < total; t++) {
            int n = 1 + random.nextInt(20);
            int m = 1 + random.nextInt(10);
            int[][] bookings = new int[m][3];
            for (int i = 0; i < m; i++) {
                int first = 1 + random.nextInt(n);
                int last = first + random.nextInt(n - first + 1);
                int seats = 1 + random.nextInt(100);
                bookings[i] = new int[] {first, last, seats};
            }
            int[] expected = getSeatCountBruteForce(bookings, n);
            int[] actual = getSeatCount(bookings, n);
            boolean pass = Arrays.equals(expected, actual);
            if (!pass) {
                failed++;
            }
            System.out.printf("用例%03d: n=%d 预期=%s 实际=%s %s%n", t, n,
                    Arrays.toString(expected), Arrays.toString(actual), pass ? "PASS" : "FAIL");
        }
        System.out.printf("%n随机对拍：%d/%d 通过%n", total - failed, total);
    }

    // 运行单个用例，打印预期与实际结果
    private static void check(int[][] bookings, int n, int[] expected) {
        int[] actual = getSeatCount(bookings, n);
        boolean pass = Arrays.equals(expected, actual);
        System.out.printf("bookings=%s n=%d%n预期=%s%n实际=%s %s%n%n",
                Arrays.deepToString(bookings), n,
                Arrays.toString(expected), Arrays.toString(actual), pass ? "PASS" : "FAIL");
    }

    // 暴力解法，用于对拍生成预期结果，O(mn)
    private static int[] getSeatCountBruteForce(int[][] bookings, int n) {
        int[] result = new int[n];
        for (int[] booking : bookings) {
            int first = booking[0];
            int end = booking[1];
            int seats = booking[2];
            for (int i = first; i <= end; i++) {
                result[i - 1] += seats;
            }
        }
        return result;
    }

    // 暴力枚举，O(mn), m代表记录条数
    // 关键，[first,last] 就代表一个区间，也就是批量更新值，优先考虑差分
    private static int[] getSeatCount(int[][] bookings, int n) {
        int[] diffCount = new int[n+1];
        for (int[] booking : bookings) {
            int first = booking[0];
            int end = booking[1];
            int seat = booking[2];

            diffCount[first] = diffCount[first] + seat;
            if (end+1 <= n) {
                diffCount[end + 1] = diffCount[end + 1] - seat;
            }
        }

        int[] result = new int[n];
        result[0] = diffCount[1];
        for (int i = 1; i < n; i++) {
            result[i] = result[i-1] + diffCount[i+1];
        }
        return result;
    }
}
