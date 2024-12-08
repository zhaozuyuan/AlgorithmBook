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


    /**
     * 最初记事本上只有一个字符 'A' 。你每次可以对这个记事本进行两种操作：
     * Copy All（复制全部）：复制这个记事本中的所有字符（不允许仅复制部分字符）。
     * Paste（粘贴）：粘贴 上一次 复制的字符。
     * 给你一个数字 n ，你需要使用最少的操作次数，在记事本上输出 恰好 n 个 'A' 。返回能够打印出 n 个 'A' 的最少操作次数。
     *
     * 示例 1：
     * 输入：3
     * 输出：3
     * 解释：
     * 最初, 只有一个字符 'A'。
     * 第 1 步, 使用 Copy All 操作。
     * 第 2 步, 使用 Paste 操作来获得 'AA'。
     * 第 3 步, 使用 Paste 操作来获得 'AAA'。
     *
     * 示例 2：
     * 输入：n = 1
     * 输出：0
     *
     * 约数：对于整数n，能整除n的数
     * 真约数：能整除n中除了n以外的数字（被n除以并除尽的最大整数）
     *
     * 思路: 顺着推肯定不行，时间复杂度太高，还是逆向思维，怎么找到最大真约数，再逐步分解真约数
     */
    public static int getMinStepToPrintNA(int n) {
        if (n <= 1) {
            return 0;
        }

        int maxDivisor = n; // 待解析的最大真约数
        int count = 0;      // 操作次数

        while (maxDivisor > 1) {
            // 找到最大真约数
            int divisor = maxDivisor / 2;
            while (maxDivisor % divisor != 0) {
                --divisor;
            }

            // 复制1次，粘贴 maxDivisor / divisor - 1次
            count = count +  maxDivisor / divisor;
            maxDivisor = divisor;
        }

        // 时间复杂度 O(logn) 空间复杂度 O(1)
        return count;
    }

    /**
     * 快速排序
     * 思路: 分治思想，选一个基准数，将数组分成两部分，左边小于基准数，右边大于基准数，递归排序
     * 时间复杂度 O(nlogn) 空间复杂度 O(1)
     */
    public static void quickSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        splitArray(nums, 0, nums.length - 1);
    }

    public static void splitArray(int[] nums, int startIndex, int endIndex) {
        if (endIndex <= startIndex) {
            return;
        }

        // 把start index上的数字当作基准数
        int baseNum = nums[startIndex];
        int leftIndex = startIndex + 1;
        int rightIndex = endIndex;
        while (leftIndex <= rightIndex) {
            // 找到能更大的/更小的值进行交换
            while (nums[leftIndex] < baseNum && leftIndex < rightIndex) {
                ++leftIndex;
            }
            while (nums[rightIndex] > baseNum && leftIndex < rightIndex) {
                --rightIndex;
            }
            if (leftIndex < rightIndex) { // 找到了，则交换
                swap(nums, leftIndex, rightIndex);
            } else if (leftIndex == rightIndex) { // 指针相遇，则继续分割和排序
                if (nums[leftIndex] > baseNum) { // 指针的数字更大，则和前一位交换
                    swap(nums, startIndex, leftIndex - 1);
                    splitArray(nums, startIndex, leftIndex - 2);
                    splitArray(nums, leftIndex, endIndex);
                } else { // 指针的数字更小，则和当前交换
                    swap(nums, startIndex, leftIndex);
                    splitArray(nums, startIndex, leftIndex - 1);
                    splitArray(nums, leftIndex + 1, endIndex);
                }
                return;
            }
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}