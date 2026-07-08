package com.zy.algorithm.book;

public class Day26_0629 {

    /**
     * leetcode 1483
     *   📖 题目简介
     *
     *   给定一个 m x n 矩阵 mat 和整数 k，返回矩阵 answer，其中 answer[i][j] 是以 (i, j) 为中心、范围 [i-k, i+k] × [j-k, j+k] 内所有元素之和。
     *
     *   示例：
     *   输入: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 1
     *   输出: [[12,21,16],[27,45,33],[24,39,28]]
     *
     *   暴力枚举：O(m*n*k^2)
     */
    public static void main(String[] args) {
        // 测试用例1: 示例1
        int[][] mat1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int k1 = 1;
        System.out.println("测试用例1: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 1");
        int[][] result1 = getMatrixSum(mat1, 0, 0, k1);
        System.out.println("期望输出: [[12,21,16],[27,45,33],[24,39,28]]");
        System.out.print("实际输出: ");
        printMatrix(result1);

        System.out.println();

        // 测试用例2: 示例2
        int[][] mat2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int k2 = 2;
        System.out.println("测试用例2: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 2");
        System.out.println("期望输出: [[45,45,45],[45,45,45],[45,45,45]]");
        System.out.print("实际输出: ");
        printMatrix(getMatrixSum(mat2, 0, 0, k2));
    }

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

    /**
     * 思路，把每个坐标到 (0,0) 的和计算出来，最后算出矩阵的差即可，O(m*n)
     */
    private static int[][] getMatrixSum(int[][] mat, int x, int y, int k) {
        int[][] sum_00 = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                // 当前行的和（当前行上一个坐标的和 + 当前值） + 上一行的和
                int preRowSum = j == 0 ? 0 : sum_00[i][j-1];
                // 注意，行列为0时，左上角的 sum 就为 0
                int preRowColumSum = j == 0 ? 0 : (i == 0 ? 0 : sum_00[i-1][j-1]);
                int preColumSum = i == 0 ? 0 : sum_00[i-1][j];
                sum_00[i][j] = preRowSum - preRowColumSum + mat[i][j] + preColumSum;
            }
        }

        // 注意边界，右下角 - 左下角(列数-1) - 右上角(行数-1) + 左上角(行列数-1)
        int[][] result = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                int r1 = Math.max(i - k, 0);
                int c1 = Math.max(j - k, 0);
                int r2 = Math.min(i + k, mat.length - 1);
                int c2 = Math.min(j + k, mat[0].length - 1);
                // 标准公式：S[r2][c2] - S[r1-1][c2] - S[r2][c1-1] + S[r1-1][c1-1]
                // r1==0 或 c1==0 时，对应项取 0（越界即空区域）
                int sumTop       = r1 == 0 ? 0 : sum_00[r1 - 1][c2];
                int sumLeft      = c1 == 0 ? 0 : sum_00[r2][c1 - 1];
                int sumTopLeft   = (r1 == 0 || c1 == 0) ? 0 : sum_00[r1 - 1][c1 - 1];
                result[i][j] = sum_00[r2][c2] - sumTop - sumLeft + sumTopLeft;
            }
        }

        return result;
    }
}
