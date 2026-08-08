package com.zy.algorithm.book;

public class Day26_0807 {

    /**
     * leetcode.1139 最大的以 1 为边界的正方形（Medium）
     * 给你一个由若干 0 和 1 组成的二维网格 grid，请你找出边界全部由 1 组成的最大 正方形 子网格，并返回该子网格中的元素个数。如果不存在，则返回 0。
     *
     * 示例 1：
     * 输入：grid = [[1,1,1],[1,0,1],[1,1,1]]
     * 输出：9
     *
     * 示例 2：
     * 输入：grid = [[1,1,0,0]]
     * 输出：1
     */
    public static void main(String[] args) {
        int passed = 0;
        int total = 0;
        System.out.println("=== Day26_0807 1139.最大的以 1 为边界的正方形 测试 ===");
        passed += test("示例1（3x3 边框为1）", new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}}, 9, total = total + 1, total);
        passed += test("示例2（单行）", new int[][]{{1, 1, 0, 0}}, 1, total = total + 1, total);
        passed += test("单格为1", new int[][]{{1}}, 1, total = total + 1, total);
        passed += test("单格为0", new int[][]{{0}}, 0, total = total + 1, total);
        passed += test("全0 2x2", new int[][]{{0, 0}, {0, 0}}, 0, total = total + 1, total);
        passed += test("全1 2x2", new int[][]{{1, 1}, {1, 1}}, 4, total = total + 1, total);
        passed += test("全1 3x3", new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, 9, total = total + 1, total);
        passed += test("空心4x4边框", new int[][]{{1, 1, 1, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 1, 1, 1}}, 16, total = total + 1, total);
        passed += test("空心5x5边框", new int[][]{{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}, 25, total = total + 1, total);
        passed += test("单行全1", new int[][]{{1, 1, 1, 1}}, 1, total = total + 1, total);
        passed += test("2x3全1", new int[][]{{1, 1, 1}, {1, 1, 1}}, 4, total = total + 1, total);
        passed += test("最大正方形不在左上角", new int[][]{{0, 1, 1}, {0, 1, 1}, {0, 0, 0}}, 4, total = total + 1, total);
        passed += test("混合（含内部为0）", new int[][]{{1, 0, 1}, {1, 1, 1}, {1, 1, 1}}, 4, total = total + 1, total);
        passed += test("阶梯形", new int[][]{{1, 1, 1, 1}, {1, 1, 1, 0}, {1, 1, 1, 0}, {1, 1, 1, 0}}, 9, total = total + 1, total);
        passed += test("菱形", new int[][]{{0, 1, 1, 0}, {1, 1, 1, 1}, {0, 1, 1, 0}}, 4, total = total + 1, total);
        passed += test("对角线为1", new int[][]{{1, 0}, {0, 1}}, 1, total = total + 1, total);
        passed += test("十字", new int[][]{{0, 0, 1, 0, 0}, {0, 1, 1, 1, 0}, {1, 1, 1, 1, 1}, {0, 1, 1, 1, 0}, {0, 0, 1, 0, 0}}, 9, total = total + 1, total);
        System.out.println("=== 汇总：" + passed + "/" + total + " 通过 ===");
    }

    private static int test(String name, int[][] grid, int expected, int index, int total) {
        int actual = findMaxMatrixElementCount(grid);
        boolean ok = expected == actual;
        System.out.printf("[%d/%d] %s：预期 %d，实际 %d %s%n", index, total, name, expected, actual, ok ? "✅ 通过" : "❌ 失败");
        return ok ? 1 : 0;
    }

    // 暴力，先找一条边，再找左、下、右，时间复杂度约为 O(n^4)
    // 规律：正方形、只有0和1（满1的边前缀和是固定的)
    private static int findMaxMatrixElementCount(int[][] nums) {
        // 一行内的前缀和
        int[][] rowPrefixSum = new int[nums.length + 1][nums[0].length + 1];
        // 一列内的前缀和
        int[][] columPrefixSum = new int[nums.length + 1][nums[0].length + 1];
        for (int i = 1; i < rowPrefixSum.length; i++) {
            for (int j = 1; j < rowPrefixSum[0].length; j++) {
                rowPrefixSum[i][j] = rowPrefixSum[i][j - 1] + nums[i - 1][j - 1];
                columPrefixSum[i][j] = columPrefixSum[i - 1][j] + nums[i - 1][j - 1];
            }
        }

        // 只使用前缀和，能降到 O(n^3)
        // 最大边长
        int maxMatrixLength = 0;
        for (int i = 1; i < rowPrefixSum.length; i++) {
            for (int j = 1; j < rowPrefixSum[0].length; j++) {
                // (i+1,j+1) 才算是真正的正方形左上角
                // 最大边长
                int maxLength = Math.min(rowPrefixSum[0].length - j, columPrefixSum.length - i);
                // 最大往小找，找到正方形则跳出
                for (int diff = maxLength - 1; diff >= 0; diff--) {
                    int length = diff + 1;
                    // 重点：求真正的顶边长度，必须是 j+diff - (j-1) 才是，j+diff-j 并不是
                    if (rowPrefixSum[i][j+diff] - rowPrefixSum[i][j-1] != length) continue;     // 顶边
                    if (columPrefixSum[i+diff][j] - columPrefixSum[i-1][j] != length) continue; // 左边
                    if (columPrefixSum[i+diff][j+diff] - columPrefixSum[i-1][j+diff] != length) continue; // 右边
                    if (rowPrefixSum[i+diff][j+diff] - rowPrefixSum[i+diff][j-1] != length) continue;    // 下边

                    maxMatrixLength = Math.max(maxMatrixLength, length);
                    break;
                }
            }
        }
        return maxMatrixLength * maxMatrixLength;
    }
}
