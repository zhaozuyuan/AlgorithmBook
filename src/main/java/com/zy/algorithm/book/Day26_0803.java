package com.zy.algorithm.book;

import java.util.Arrays;

public class Day26_0803 {

    /**
     * 2054. 两个最好的不重叠活动
     *
     * 题目描述：
     * 给你一个二维整数数组 events，其中 events[i] = [startTimeᵢ, endTimeᵢ, valueᵢ]。第 i 个活动从 startTimeᵢ 开始，到 endTimeᵢ 结束，参加该活动会获得价值 valueᵢ。
     * 你最多可以选择两个不重叠的活动参加，使得它们的价值之和最大化。
     * 返回这个最大和。
     * 注意：开始时间和结束时间都是闭区间——你不能参加两个在边界上重叠的活动（一个的结束时间恰好等于另一个的开始时间时也不行）。
     *
     * 输入：events = [[1,5,3],[1,5,1],[6,6,5]]
     * 输出：8
     * 解释：选择活动 0 和 2，和为 3 + 5 = 8。
     */
    public static void main(String[] args) {
        // 用例1：官方示例1
        int[][] events1 = {{1, 3, 2}, {4, 5, 2}, {2, 4, 3}};
        test("用例1（官方示例1）", events1, 4);

        // 用例2：官方示例2，只选一个活动就最大
        int[][] events2 = {{1, 3, 2}, {4, 5, 2}, {1, 5, 5}};
        test("用例2（官方示例2）", events2, 5);

        // 用例3：官方示例3
        int[][] events3 = {{1, 5, 3}, {1, 5, 1}, {6, 6, 5}};
        test("用例3（官方示例3）", events3, 8);

        // 用例4：闭区间边界重叠，只能选一个
        int[][] events4 = {{1, 5, 10}, {5, 6, 8}};
        test("用例4（边界重叠）", events4, 10);

        // 用例5：最大两个活动重叠，退而求其次
        int[][] events5 = {{1, 2, 5}, {3, 4, 6}, {2, 5, 10}};
        test("用例5（最大活动重叠）", events5, 11);
    }

    private static void test(String name, int[][] events, int expected) {
        int actual = maxTwoEvents2(events);
        System.out.println(name + " 预期结果: " + expected + "，实际结果: " + actual + (expected == actual ? "，通过" : "，未通过"));
    }

    // 用值排序，贪心找最大的，最坏时间复杂度 O(n^2)
    private static int maxTwoEvents(int[][] events) {
        Arrays.sort(events, (int[] a, int[] b) -> a[2] - b[2]);

        int result = events[events.length - 1][2];
        for (int i = events.length - 1; i > 0; --i) {
            int iStart = events[i][0];
            int iEnd = events[i][1];
            int iValue = events[i][2];
            // 考虑一个值的情况
            result = Math.max(result, iValue);

            for (int j = i - 1; j >= 0; --j) {
                int jStart = events[j][0];
                int jEnd = events[j][1];
                int jValue = events[j][2];

                boolean isValid = (iStart < jStart && iEnd < jStart) || (iStart > jEnd && iEnd > jEnd);
                if (isValid) {
                    if ((iValue + jValue) > result) {
                        result = iValue + jValue;
                    }
                    // 剪枝，剩下的肯定更小，没必要探索
                    break;
                }

                // 剪枝，剩下的肯定更小，没必要探索
                if ((iValue + jValue) <= result) {
                    break;
                }
            }
        }
        return result;
    }

    // 用时间排序，关键点：1、找开始时间 > 结束时间，可以使用二分查找；2、找匹配的最大值，可以用前缀最大值思路。时间复杂度为 O(nlogn)
    private static int maxTwoEvents2(int[][] events) {
        // 使用start时间从小到大排序
        // 如果 i + 1 的 start 时间 > i 的 end 时间，则后面所有坐标的 start 时间都大于 i 的 end 时间，都是可以组合的坐标
        Arrays.sort(events, (int[] a, int[] b) -> (a[0] - b[0]));

        // 记录后缀最大值
        int[] maxSuffix = new int[events.length];
        maxSuffix[events.length - 1] = events[events.length - 1][2];
        for (int i = events.length - 2; i >= 0; --i) {
            maxSuffix[i] = Math.max(maxSuffix[i + 1], events[i][2]);
        }

        int result = Integer.MIN_VALUE;
        for (int i = 0; i < events.length; ++i) {
            int iStart = events[i][0];
            int iEnd = events[i][1];
            int iValue = events[i][2];
            result = Math.max(result, iValue);

            // 二分检索，start > end 的第一个坐标
            int x = i + 1, y = events.length - 1;
            while (x <= y) {
                int mid = (x + y) / 2;
                // 找到恰好满足条件的坐标
                if (events[mid][0] > iEnd && events[mid - 1][0] <= iEnd) {
                    int maxValue = maxSuffix[mid];
                    if ((iValue + maxValue) > result) {
                        result = iValue + maxValue;
                        break;
                    }
                }

                // 值太小了，往右找
                if (x == y) {
                    break;
                } else if (events[mid][0] <= iEnd) { // 值太小了，往右找
                    x = mid + 1;
                } else {
                    y = mid;
                }
            }
        }

        return result;
    }
}
