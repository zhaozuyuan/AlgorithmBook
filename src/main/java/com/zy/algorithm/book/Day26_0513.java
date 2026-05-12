package com.zy.algorithm.book;

public class Day26_0513 {
    public static void main(String[] args) {
        System.out.println("result:" + (fillNums2(new int[]{1,3,6}, new int[]{2,4,5,3}) == 4));
    }

    /**
     * 将 num1 转换成 nums2，并找出最少的操作次数
     * 操作：
     * 1. nums1[i] 通过加减1变成 nums2[i]，每次加减记为1次
     * 2. 可以通过复制的方式把 nums1[i] 追加到 nums2[n] 上
     * @param nums1 size=n
     * @param nums2 size=n+1
     */
    private static int fillNums2(int[] nums1, int[] nums2) {
        int result = 0;
        int targetNum = nums2[nums1.length - 1];
        int additionalCount = Integer.MAX_VALUE;
        for (int i = 0; i < nums1.length; ++i) {
            int x = Math.max(nums1[i], nums2[i]);
            int y = Math.min(nums1[i], nums2[i]);

            // 必须要操作的次数
            result += (x - y);

            // 剪枝：证明已经是最少的额外操作次数了
            if (additionalCount == 1) {
                continue;
            }

            // 在数字区间，则中间肯定可以直接 copy 过去，次数为1
            // 不在数字区间，则需要的加减操作
            if (x >= targetNum && targetNum <= y) {
                additionalCount = 1;
            } else {
                int current = 1 + Math.min(Math.abs(targetNum - x), Math.abs(targetNum - y));
                additionalCount = Math.min(additionalCount, current);
            }
        }
        return additionalCount + result;
    }
}
