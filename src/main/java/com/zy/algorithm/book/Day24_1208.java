package com.zy.algorithm.book;

public class Day24_1208 {

    /**
     * 在一个x、y轴方向均有序递增的二维数组找目标数字:
     *      1, 5, 6
     *      2, 7, 8
     *      3, 9, 10
     * 二维数组: [第几行][第几列]
     * 思路: x、y轴都是递增的，顺着找没办法确认目标数字在x轴还是y轴上，应该不断逼近目标数字
     *
     * dimension: 尺寸、维度（dimensional 维度的）
     *   two dimension array: 二维数组
     *   近义词: matrix 矩阵
     * row: 行
     * column: 列
     */
    public static boolean existInOrderedTwoDimensionalArray(int target, int[][] array) {
        if (array == null || array.length == 0 || array[0].length == 0) {
            return false;
        }

        int rowCount = array.length;
        int columnCount = array[0].length;

        // 有右上角开始找，左边的数字比它小，下边的数字比它大
        for (int i = 0, j = columnCount - 1; i < rowCount && j >= 0;) {
            int value = array[i][j];
            if (value == target) {
                return true;
            } else if (value < target) { // target更大，朝下方查找，行数++
                ++i;
            } else { // target更小，朝左边查找，列数--
                --j;
            }

        }

        // 时间复杂度 O(n) 空间复杂度 O(1)
        return false;
    }
}