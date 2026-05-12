package com.zy.algorithm.book;

import java.util.Arrays;

public class Day26_0509 {

    public static void main(String[] args) {
        System.out.println("result:" + fillSubCollection(new int[]{3, 3, 2, 2, 2, 2}, 2));
    }

    /**
     * 填充子集合，判断 nums 能否拆分成 k 个子集合，每个子集合的和相等
     * 
     * 坑点：贪心算法，每次只会选最近的数字（只要能装进去），可能导致丢失一些数字组合
     * 修复：应当使用回溯算法，每走一步都判断最终能否完成任务，若不能完成则回溯状态，改另一步（就是暴力枚举+剪枝，避免无效尝试）
=    */
    public static boolean fillSubCollection(int[] nums, int k) {
        int sum = sumArray(nums);
        if (sum % k != 0) {
            return false;
        }
        // 每个桶的目标值
        int target = sum / k;
        // 从大到小排序，便于装桶，而不必每次都遍历整个数组
        descendingSort(nums);
        // 标记数字是否被使用
        boolean[] used = new boolean[nums.length];
        return backtrace(nums, used, 0, k, 0, target);
    }

    /**
     * 回溯算法
     */
    private static boolean backtrace(
        int[] nums, 
        boolean[] used, 
        int startIndex, 
        int k,
        int currentSum,
        int target
    ) {
        // 所有的桶都装满了
        if (k == 0) {
            return true;
        }

        // 等于目标值，装下一个桶
        // 注意，每一个新桶应该从 0 下标开始，因为前面可能有没用过的数字
        if (currentSum == target) {
            return backtrace(nums, used, 0, k - 1, 0, target);
        }

        for (int i = startIndex; i < nums.length; ++i) {
            if (used[i]) {
                continue;
            }

            // 剪枝，当前数字大于目标值，直接返回 false
            if (nums[i] > target) {
                return false;
            }

            int nextSum = currentSum + nums[i];
            // 继续尝试下一个数字
            if (nextSum > target) {
                continue;
            }
            used[i] = true;
            // 关键之处，和贪心算法的区别：当前数字装进去之后，应该直接递归到最终结果，如果结果不对，应该回溯状态，往前一步
            if (backtrace(nums, used, i + 1, k, nextSum, target)) {
                // 最终成功，返回 true
                return true;
            }
            // 回溯状态，并尝试下一个数字
            used[i] = false;

            // 剪枝，第一个数字都没放进去，直接返回 false，尝试下一个数字即可
            if (currentSum == 0) {
                return false;
            }
        }

        return false;
    }

    private static int sumArray(int[] nums) {
        int sum = 0;
        for (int n: nums) {
            sum += n;
        }
        return sum;
    }

    private static void descendingSort(int[] nums) {
        // 升序
        Arrays.sort(nums);
        for(int i = 0, j = nums.length - 1; i < j; ++i, --j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
