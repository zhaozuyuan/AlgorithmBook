package com.zy.algorithm.book;

public class Day26_0808 {

    /**
     * leetcode.221 最大正方形
     * 在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
     * 示例 1：
     * 输入：matrix = [["1","0","1","0","0"],
     * ["1","0","1","1","1"],
     * ["1","1","1","1","1"],
     * ["1","0","0","1","0"]]
     * 输出：4
     *
     * 示例 2：
     * 输入：matrix = [["0","1"],["1","0"]]
     * 输出：1
     *
     * 提示： 注意元素是字符 '0'/'1' 不是整数（和 1139 不同，容易踩坑）；矩阵规模较大，暴力 O(n⁴) 过不了。
     */
    public static void main(String[] args) {
        int passed = 0;
        int total = 0;
        System.out.println("=== Day26_0808 221.最大正方形 测试 ===");
        passed += test("示例1（4x5 混合）", new char[][]{{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}}, 4, total = total + 1, total);
        passed += test("示例2（对角1）", new char[][]{{'0','1'},{'1','0'}}, 1, total = total + 1, total);
        passed += test("示例3（单格0）", new char[][]{{'0'}}, 0, total = total + 1, total);
        passed += test("单格为1", new char[][]{{'1'}}, 1, total = total + 1, total);
        passed += test("全1 2x2", new char[][]{{'1','1'},{'1','1'}}, 4, total = total + 1, total);
        passed += test("全1 3x3", new char[][]{{'1','1','1'},{'1','1','1'},{'1','1','1'}}, 9, total = total + 1, total);
        passed += test("全0 3x3", new char[][]{{'0','0','0'},{'0','0','0'},{'0','0','0'}}, 0, total = total + 1, total);
        passed += test("2x3全1", new char[][]{{'1','1','1'},{'1','1','1'}}, 4, total = total + 1, total);
        passed += test("单行全1", new char[][]{{'1','1','1','1'}}, 1, total = total + 1, total);
        passed += test("单行1001", new char[][]{{'1','0','0','1'}}, 1, total = total + 1, total);
        passed += test("对角为1", new char[][]{{'1','0'},{'0','1'}}, 1, total = total + 1, total);
        passed += test("边框1内部0（区别221与1139）", new char[][]{{'1','1','1'},{'1','0','1'},{'1','1','1'}}, 1, total = total + 1, total);
        passed += test("左上阶梯", new char[][]{{'1','1','1'},{'1','1','0'},{'1','0','0'}}, 4, total = total + 1, total);
        passed += test("大阶梯", new char[][]{{'1','1','1','1'},{'1','1','1','0'},{'1','1','1','0'},{'1','1','1','0'}}, 9, total = total + 1, total);
        passed += test("十字", new char[][]{{'0','0','1','0','0'},{'0','1','1','1','0'},{'1','1','1','1','1'},{'0','1','1','1','0'},{'0','0','1','0','0'}}, 9, total = total + 1, total);
        passed += test("混合", new char[][]{{'1','0','1','1'},{'1','1','1','1'},{'0','1','1','1'}}, 4, total = total + 1, total);
        System.out.println("=== 汇总：" + passed + "/" + total + " 通过 ===");
    }

    private static int test(String name, char[][] matrix, int expected, int index, int total) {
        int actual = findMaxMatrixAreaDP(matrix);
        boolean ok = expected == actual;
        System.out.printf("[%d/%d] %s：预期 %d，实际 %d %s%n", index, total, name, expected, actual, ok ? "✅ 通过" : "❌ 失败");
        return ok ? 1 : 0;
    }

    // 暴力找正方形，时间复杂度为 O(n^4)，太大了
    // 二维前缀和，全为1的正方形面积是固定的，可以算出来，时间复杂度可以做到 O(n^3)
    private static int findMaxMatrixArea(char[][] matrix) {
        int[][] sums = new int[matrix.length + 1][matrix[0].length + 1];
        for (int i = 1; i < sums.length; i++) {
            for (int j = 1; j < sums[0].length; j++) {
                sums[i][j] = sums[i - 1][j] + sums[i][j - 1] - sums[i - 1][j - 1] + (matrix[i - 1][j - 1] == '1' ? 1 : 0);
            }
        }

        // 从左上角开始找
        int maxArea = 0;
        for (int i = 1; i < sums.length; i++) {
            for (int j = 1; j < sums[0].length; j++) {
                // 边长，从大到小找
                int maxLength = Math.min(sums.length - i, sums[0].length - j);
                for (int length = maxLength; length >= 0; length--) {
                    // 注意，必须是 sums[i + length - 1][j - 1] ，是 j - 1 而不是 j，前缀和（面积）需要减到 i 或 j 的前一个位置才是对的
                    int area = sums[i + length - 1][j + length - 1] - sums[i + length - 1][j - 1] - sums[i - 1][j + length - 1] + sums[i - 1][j - 1];
                    if (length * length <= maxArea) {
                        break;
                    }
                    if (area == length * length) {
                        if (area > maxArea) {
                            maxArea = area;
                        }
                        break;
                    }
                }
            }
        }
        return maxArea;
    }

    // 动态规划，必须手画图才能理解
    // dp[i][j]，这里的 i,j 代表正方形的右下角坐标，规律：
    //   1、(i,j) 的左、上、左上的最大正方形重叠和组合之后，规律是它们的最短边 + (i,j) 可以组合成一个新的正方形
    //   2、左、上、左上重叠部分补齐的是老的正方形的左边和上边，左、上补齐的是正方形的下边、右边
    // dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
    private static int findMaxMatrixAreaDP(char[][] matrix) {
        // dp 代表右下角的正方形的边长
        int[][] dp = new int[matrix.length + 1][matrix[0].length + 1];
        int maxLength = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (matrix[i-1][j-1] == '1') {
                    dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1])) + 1;
                    if (maxLength < dp[i][j]) {
                        maxLength = dp[i][j];
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return maxLength * maxLength;
    }
}
