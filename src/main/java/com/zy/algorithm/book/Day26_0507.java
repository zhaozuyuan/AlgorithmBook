package com.zy.algorithm.book;

public class Day26_0507 {
    public static void main(String[] args) {
        assert bigNumberMultiplication("11", "22").equals("22");
    }

    /**
     * leetcode.43
     * 大数相乘
     * @param num1 高位数在前
     * @param num2
     * @return 乘积，高位数在前
     */
    public static String bigNumberMultiplication(String num1, String num2) {
        // 结果，低位数在前
        int[] result = new int[num1.length() + num2.length()];
        for (int i1 = num1.length() - 1; i1 >= 0; --i1) {
            for (int i2 = num2.length() - 1; i2 >= 0; --i2) {
                int product = (num1.charAt(i1) - '0') * (num2.charAt(i2) - '0');

                int currentIndex = result.length - (i1 + i2) - 2;
                int sum = result[currentIndex] + product;
                result[currentIndex] = sum % 10;
                while (sum >= 10){
                   ++currentIndex;
                   sum = result[currentIndex] + sum / 10;
                   result[currentIndex] = sum % 10;
                }
            }
        }

        // 转换成高位数在前
        for (int i = result.length - 1; i >= 0; --i) {
            if(result[i] != 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = i; j >= 0; --j) {
                    builder.append(result[j]);
                }
                return builder.toString();
            }
        }
        return "0";
    }
}
