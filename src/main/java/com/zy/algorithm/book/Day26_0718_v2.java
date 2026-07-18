package com.zy.algorithm.book;

import java.util.LinkedList;
import java.util.Queue;

public class Day26_0718_v2 {

    /**
     * leetcode.542. 01 Matrix
     *   题目描述
     *   给定一个 m x n 的二进制矩阵 mat，请返回每个格子到最近的 0 的距离。
     *   两个共享一条边的相邻格子之间距离为 1。
     *   示例 1
     *   输入：
     *   mat = [[0,0,0],[0,1,0],[0,0,0]]
     *   输出：
     *   [[0,0,0],[0,1,0],[0,0,0]]
     *
     *   示例 2
     *   输入：
     *   mat = [[0,0,0],[0,1,0],[1,1,1]]
     *   输出：
     *   [[0,0,0],[0,1,0],[1,2,1]]
     *
     *   约束
     *
     *   - m == mat.length
     *   - n == mat[i].length
     *   - 1 <= m, n <= 10^4
     *   - 1 <= m * n <= 10^4
     *   - mat[i][j] 只能是 0 或 1
     *   - mat 中至少有一个 0
     */
    public static void main(String[] args) {
        int[][] input1 = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        int[][] expected1 = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        runTestCase("测试用例1", input1, expected1);

        int[][] input2 = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
        int[][] expected2 = {{0, 0, 0}, {0, 1, 0}, {1, 2, 1}};
        runTestCase("测试用例2", input2, expected2);

        int[][] input3 = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        int[][] expected3 = {{2, 1, 2}, {1, 0, 1}, {2, 1, 2}};
        runTestCase("测试用例3", input3, expected3);
    }

    private static void runTestCase(String name, int[][] input, int[][] expected) {
        System.out.println(name);
        System.out.print("输入: ");
        printMatrix(input);
        System.out.print("预期结果: ");
        printMatrix(expected);
        System.out.print("最终结果: ");
        printMatrix(getMinDistance(input));
        System.out.println();
    }

    /**
     * 核心思路：多源 bfs，把多个起点放进队列，遍历下一层，再把下一层放进队列，再遍历。
     */
    private static void printMatrix(int[][] matrix) {
        System.out.print("[");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("[");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    System.out.print(",");
                }
            }
            System.out.print("]");
            if (i < matrix.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

    private static int[][] getMinDistance(int[][] input) {
        // 暴力思路：bfs 广度遍历，从每个1往外bfs，注意 bfs 是基于每个点再往外找
        // 反向思路：通过0去确认1，关键点在于先确认所有的0，然后同时往外扩散找1，距离一步一步增加
        // bfs 的关键就是队列，一开始记录所有0的位置，再层级遍历，直到队列清空

        int rowCount = input.length;
        int colCount = input[0].length;

        int[][] result = new int[rowCount][colCount];
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < colCount; ++j) {
                result[i][j] = -1;
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        // 填充第一批0
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < colCount; ++j) {
                if (input[i][j] == 0) {
                    // 第三个值是当前到0最近的距离
                    result[i][j] = 0;
                    queue.add(new int[]{i,j});
                }
            }
        }

        // 一层一层发散去填空，bfs
        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            int i = cur[0];
            int j = cur[1];
            int minDistance = result[i][j];
            // 找到需要填空的地方
            if (i > 0 && result[i-1][j] == -1) {
                result[i-1][j] = minDistance + 1;
                queue.offer(new int[]{i-1, j});
            }
            if (i < rowCount-1 && result[i+1][j] == -1) {
                result[i+1][j] = minDistance + 1;
                queue.offer(new int[]{i+1, j});
            }
            if (j > 0 && result[i][j-1] == -1) {
                result[i][j-1] = minDistance + 1;
                queue.offer(new int[]{i, j-1});
            }
            if (j < colCount-1 && result[i][j+1] == -1) {
                result[i][j+1] = minDistance + 1;
                queue.offer(new int[]{i, j+1});
            }
        }

        return result;
    }
}
