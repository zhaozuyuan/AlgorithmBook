package com.zy.algorithm.book;

public class Day26_0729 {

    /**
     * leetcode.1980 题目描述
     * 给你一个字符串数组 nums，该数组由 n 个互不相同的二进制字符串组成，且每个字符串长度都是 n。请你找出并返回一个长度为 n 且没有出现在 nums 中的二进制字符串。如果存在多种答案，只需返回任意一个即可。
     * 示例 1：
     * 输入：nums = ["01","10"]
     * 输出："11"
     * 解释："11" 没有出现在 nums 中。"00" 也是正确答案。
     *
     * 提示
     * - n == nums.length
     * - nums 中的所有字符串互不相同
     */
    public static void main(String[] args) {
        // 本题答案不唯一（任意一个长度为 n 且不在 nums 中的串都算正确），
        // 所以「预期」是一个合法性条件而非固定字符串，用条件校验代替相等比较。
        runTest(new String[]{"01", "10"});                   // 示例 1
        runTest(new String[]{"00", "01"});                   // 示例 2
        runTest(new String[]{"111", "011", "001"});          // 示例 3
        runTest(new String[]{"0"});                          // 边界：n = 1
        runTest(new String[]{"1"});                          // 边界：n = 1
        runTest(new String[]{"000", "001", "010"});          // 对角线全 0 -> 翻转全 1
        runTest(new String[]{"0000", "0001", "0010", "0011"});
    }

    private static void runTest(String[] nums) {
        String actual = findNotExist(nums);
        int n = nums.length;

        // 预期：长度为 n 且不在 nums 中的任意二进制串
        boolean lengthOk = actual.length() == n;
        boolean notInNums = true;
        for (String s : nums) {
            if (s.equals(actual)) {
                notInNums = false;
                break;
            }
        }
        boolean pass = lengthOk && notInNums;

        System.out.println("输入: " + java.util.Arrays.toString(nums));
        System.out.println("预期: 长度=" + n + " 且不在 nums 中的任意串");
        System.out.println("实际: " + actual);
        System.out.println("校验: " + (pass ? "PASS ✅" : "FAIL ❌"));
        System.out.println("------");
    }

    // 正常就用 hash set + ['0..0','1..1'] 的方式找了
    // 关键是提示中的 n == nums.length，那这就是一个二维数组
    private static String findNotExist(String[] nums) {
        char[][] charNums = new char[nums.length][nums.length];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                charNums[i][j] = nums[i].charAt(j);
            }
        }

        // 对角线上的值是这每行字符串一定存在的值，如果取反，则就是不存在的值
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            result.append((charNums[i][i] == '0') ? '1' : '0');
        }
        return result.toString();
    }
}
