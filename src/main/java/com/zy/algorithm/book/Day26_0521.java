package com.zy.algorithm.book;

public class Day26_0521 {
    public static void main(String[] args) {
        // 测试用例 1: 递增数组, m=2
        int[] nums1 = {1, 2, 3, 4, 5};
        System.out.println("nums1 = {1, 2, 3, 4, 5}, m=2, result = " + findMaxProduct(nums1, 2) + " (expected = 20, 首元素选4尾元素选5: 4*5=20)");

        // 测试用例 2: 递减数组, m=2
        int[] nums2 = {5, 4, 3, 2, 1};
        System.out.println("nums2 = {5, 4, 3, 2, 1}, m=2, result = " + findMaxProduct(nums2, 2) + " (expected = 20, 首元素选5尾元素选4: 5*4=20)");

        // 测试用例 3: 混合数组, m=2
        int[] nums3 = {1, 5, 2, 4, 3};
        System.out.println("nums3 = {1, 5, 2, 4, 3}, m=2, result = " + findMaxProduct(nums3, 2) + " (expected = 20, 首元素选5尾元素选4: 5*4=20)");

        // 测试用例 4: m=1, 首尾元素指向同一下标
        int[] nums4 = {3, 5, 8, 4};
        System.out.println("nums4 = {3, 5, 8, 4}, m=1, result = " + findMaxProduct(nums4, 1) + " (expected = 64, 首元素选8尾元素选8: 8*8=64)");

        // 测试用例 5: m=2, 示例数组
        int[] nums5 = {3, 5, 8, 4};
        System.out.println("nums5 = {3, 5, 8, 4}, m=2, result = " + findMaxProduct(nums5, 2) + " (expected = 40, 首元素选5尾元素选8: 5*8=40)");

        // 测试用例 8: 只有两个元素, m=1
        int[] nums8 = {7, 3};
        System.out.println("nums8 = {7, 3}, m=1, result = " + findMaxProduct(nums8, 1) + " (expected = 49, 首元素选7: 7*7=49)");

        // 测试用例 9: 所有元素相同
        int[] nums9 = {4, 4, 4, 4};
        System.out.println("nums9 = {4, 4, 4, 4}, m=2, result = " + findMaxProduct(nums9, 2) + " (expected = 16)");

        // 测试用例 10: 包含负数
        int[] nums10 = {-3, -5, 2, 4};
        System.out.println("nums10 = {-3, -5, 2, 4}, m=2, result = " + findMaxProduct(nums10, 2) + " (expected = 15, 首元素选-3尾元素选-5: (-3)*(-5)=15)");
    }

    /**
     * 通过删减元素得到长度为 m 的子数组，求子数组首位元素最大的乘积值
     * leetcode.3584
     * @param nums
     * @param m >= 1 首位元素可以指向同一下标
     * 
     * 暴力枚举，就是每个元素都算一下所有子数组，然后找最大乘积值 n^2
     * 滑动窗口？主要是窗口尺寸不固定，没办法解决
     * 所以还是剪枝？移动首元素，计算首元素的所有乘积组合：
     *   1、如果后一位首元素小于前一位首元素，则所有乘积组合一定小于。
     *   2、如果后一位首元素大于前一位首元素，则需要算后一位首元素的最大乘积，和前一位首元素的最大乘积对比。
     */
    private static int findMaxProduct(int[] nums, int m) {
        if (nums.length < 1 || m >= nums.length) {
            return 0;
        }

        // 最大乘积的首元素下标
        int maxProductFirstIndex = 0;
        // 最大乘积
        int maxProduct = getMaxProduct(nums, maxProductFirstIndex, maxProductFirstIndex + m - 1);

        for (int i = 0; i < nums.length - m; ++i) {
            // 下一个首元素
            int nextI = i + 1;
            if (nums[nextI] <= nums[maxProductFirstIndex]) {
                // 代表所有乘积组合都 <= maxProduct，直接跳过
                continue;
            }

            // 计算后一位首元素的最大乘积
            int nextMaxProduct = getMaxProduct(nums, nextI, nextI + m - 1);
            if (nextMaxProduct > maxProduct) {
                maxProduct = nextMaxProduct;
                maxProductFirstIndex = nextI;
            }
        }
        return maxProduct;
    }

    /**
     * 最优解
     * 首元素依次移动，如果能提前知道它的最大乘积尾元素，就不用再计算所有乘积组合了。
     * 这个逻辑称为“后缀最大值”，重点就是提前把后缀的最大值都罗列出来，而不必重复去查找。
     */
    private int findMaxProduct2(int[] nums, int m) {
        int[] maxLastNums = new int[nums.length];
        // 最后一个元素最大值就是自己
        int startLastIndex = nums.length - 1;
        int maxLastNum = nums[startLastIndex];
        maxLastNums[startLastIndex] = maxLastNum;
        for (int j = startLastIndex - 1; j >= 0; --j) {
            if (maxLastNum < nums[j]) {
                maxLastNum = nums[j];
            }
            maxLastNums[j] = maxLastNum;
        }

        int maxProduct = Integer.MIN_VALUE;
        for (int i = 0; i <= nums.length - m; ++i) {
            int product = nums[i] * maxLastNums[i + m - 1];
            if (product > maxProduct) {
                maxProduct = product;
            }
        }
        return maxProduct;
    }

    /**
     * 计算所有首元素的所有乘积组合，找出最大值
     * @param nums
     * @param i 首元素下标，不变
     * @param j 尾元素的起始下标
     */
    private static int getMaxProduct(int[] nums, int i, int j) {
        int product = nums[i] * nums[j];
        for (int k = j + 1; k < nums.length; ++k) {
            int newProduct = nums[i] * nums[k];
            if (newProduct > product) {
                product = newProduct;
            }
        }
        return product;
    }
}
