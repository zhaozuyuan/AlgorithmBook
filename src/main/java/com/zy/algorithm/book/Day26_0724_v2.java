package com.zy.algorithm.book;

import java.util.*;

public class Day26_0724_v2 {

    /**
     * 题目描述：
     *
     * leetcode.39 给定一个候选人编号的集合 candidates 和一个目标数 target，找出 candidates 中所有可以使数字和为 target 的组合。
     * candidates 中的每个数字在每个组合中只能使用一次。
     *
     * 注意：解集不能包含重复的组合。
     * 示例 1：
     * 输入: candidates = [10,1,2,7,6,1,5], target = 8
     * 输出:
     * [
     * [1,1,6],
     * [1,2,5],
     * [1,7],
     * [2,6]
     * ]
     *
     * 示例 2：
     * 输入: candidates = [2,5,2,1,2], target = 5
     * 输出:
     * [
     * [1,2,2],
     * [5]
     * ]
     */
    public static void main(String[] args) {
        // ==================== 测试用例 ====================

        // 用例1：题目示例1
        test("示例1", new int[]{10, 1, 2, 7, 6, 1, 5}, 8,
                "[[1,1,6],[1,2,5],[1,7],[2,6]]");

        // 用例2：题目示例2
        test("示例2", new int[]{2, 5, 2, 1, 2}, 5,
                "[[1,2,2],[5]]");

        // 用例3：无解
        test("无解", new int[]{1, 2, 3, 4}, 20,
                "[]");

        // 用例4：全部元素构成target
        test("全部元素", new int[]{1, 2, 3}, 6,
                "[[1,2,3]]");

        // 用例5：有重复元素且单个元素等于target
        test("单个元素等于target", new int[]{5, 1, 5}, 5,
                "[[5]]");

        // 用例6：空结果（target太小）
        test("target太小", new int[]{5, 6, 7}, 3,
                "[]");

        // 用例7：多组解且含重复数字
        test("多组解含重复", new int[]{1, 1, 1, 1, 1}, 3,
                "[[1,1,1]]");
    }

    private static void test(String name, int[] candidates, int target, String expected) {
        System.out.println("=== " + name + " ===");
        System.out.println("输入: candidates = " + java.util.Arrays.toString(candidates) + ", target = " + target);
        System.out.println("预期: " + expected);

        int[][] actual = getTargetSumArrays(candidates, target);
        // 格式化实际结果为类似LeetCode的列表格式
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < actual.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(java.util.Arrays.toString(actual[i]));
        }
        sb.append("]");
        System.out.println("实际: " + sb.toString());
        System.out.println();
    }

    private static int[][] getTargetSumArrays(int[] nums, int target) {
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();
        computeSum(nums, new ArrayList<>(), 0, target, 0, result);

        int[][] resultArr = new int[result.size()][];
        for (int i = 0; i < result.size(); i++) {
            int[] temp = new int[result.get(i).size()];
            List<Integer> tempList = result.get(i);
            for (int j = 0; j < result.get(i).size(); j++) {
                temp[j] = tempList.get(j);
            }
            resultArr[i] = temp;
        }
        return resultArr;
    }

    // 暴力回溯 + 剪枝
    private static void computeSum(
            int[] nums,
            List<Integer> subset,
            int subsetSum,
            int target,
            int index,
            List<List<Integer>> result
    ) {
        for (int i = index; i < nums.length; i++) {
            // 同层去重：跳过相同的元素，避免产生重复组合
            if (i > index && nums[i] == nums[i - 1]) {
                continue;
            }

            int sum = nums[i] + subsetSum;

            if(sum <= target) {
                subset.add(nums[i]);

                if (sum == target) {
                    // 记录本次结果，并且末尾数字已定，没必须再往后探索
                    result.add(new ArrayList<>(subset));
                    subset.remove(subset.size() - 1);
                    break;
                }

                // 继续添加新的元素
                computeSum(nums, subset, sum, target, i + 1, result);

                // 回退当前元素
                subset.remove(subset.size() - 1);
            } else {
                // 剪枝：当前元素已经大于target，后续元素肯定也大于target，直接退出
                break;
            }
        }
    }
}
