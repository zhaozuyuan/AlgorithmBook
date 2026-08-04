package com.zy.algorithm.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day26_0804 {

    /**
     * leetcode.56 合并区间（Medium）
     *
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [startᵢ, endᵢ]。
     * 请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
     *
     * 示例 1：
     * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
     * 输出：[[1,6],[8,10],[15,18]]
     * 解释：区间 [1,3] 和 [2,6] 重叠，将它们合并为 [1,6]。
     *
     * 示例 2：
     * 输入：intervals = [[1,4],[4,5]]
     * 输出：[[1,5]]
     * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
     */
    public static void main(String[] args) {
        Object[][] cases = {
            // {输入, 预期输出}
            {new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}}, new int[][]{{1, 6}, {8, 10}, {15, 18}}}, // 示例1
            {new int[][]{{1, 4}, {4, 5}}, new int[][]{{1, 5}}},                                       // 示例2
            {new int[][]{{1, 4}, {5, 6}}, new int[][]{{1, 4}, {5, 6}}},                               // 不相邻不重叠
            {new int[][]{{1, 1}, {1, 1}}, new int[][]{{1, 1}}},                                       // 全相同点
            {new int[][]{{1, 3}}, new int[][]{{1, 3}}},                                               // 单个区间
            {new int[][]{{1, 10}, {2, 3}, {4, 5}, {6, 7}}, new int[][]{{1, 10}}},                     // 链式重叠
            {new int[][]{{2, 3}, {4, 5}, {6, 7}, {8, 9}, {1, 10}}, new int[][]{{1, 10}}},             // 乱序 + 首尾包裹
        };
        for (int i = 0; i < cases.length; ++i) {
            int[][] input = (int[][]) cases[i][0];
            int[][] expected = (int[][]) cases[i][1];
            int[][] actual = mergeSections(input);
            boolean pass = Arrays.deepEquals(expected, actual);
            System.out.println("用例 " + (i + 1) + ": " + (pass ? "通过" : "失败"));
            System.out.println("  输入:   " + toString(input));
            System.out.println("  预期:   " + toString(expected));
            System.out.println("  实际:   " + toString(actual));
        }
    }

    private static String toString(int[][] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append('[').append(arr[i][0]).append(',').append(arr[i][1]).append(']');
        }
        return sb.append(']').toString();
    }

    // 暴力，递归找到和当前区间重叠的所有区间，剩下的都是不可重叠的，时间复杂度 O(n^2)
    // 找规律，区间数字可以排序，什么是重叠区间？
    //   按照 start 升序，已经满足 (n-1).start <= n.start < n.end，如果 (n-1).end >= n.start，则一定是重叠区间
    // 怎么处理级连重叠区间
    //   如果 max((n-1).end) >= n.start，则继续往后判断 max(n.end) >= (n+1).start ...
    private static int[][] mergeSections(int[][] sections) {
        // 按照 start 升序
        Arrays.sort(sections, (int[] a, int[] b) -> { return a[0] - b[0]; });

        // 预处理数组，记录最大 end
        int[] maxEndArray = new int[sections.length];
        maxEndArray[0] = sections[0][1];
        for (int i = 1; i < sections.length; ++i) {
            maxEndArray[i] = Math.max(maxEndArray[i-1], sections[i][1]);
        }

        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < sections.length; ++i) {
            // 根据级联关系找到最远的重叠区间
            int lastTargetSecionIndex = i;
            for (int next = i + 1; next < sections.length; ++next) {
                if (maxEndArray[lastTargetSecionIndex] >= sections[next][0]) {
                    lastTargetSecionIndex = next;
                }
            }
            if (lastTargetSecionIndex > i) {  // 存在重叠区间
                result.add(new int[]{ sections[i][0], maxEndArray[lastTargetSecionIndex] });
                i = lastTargetSecionIndex;
            } else {  // 不存在重叠区间
                result.add(new int[]{ sections[i][0], sections[i][1] });
            }
        }
        return result.toArray(new int[result.size()][2]);
    }
}
