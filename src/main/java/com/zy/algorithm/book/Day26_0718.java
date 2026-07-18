package com.zy.algorithm.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day26_0718 {

    /**
     * leetcode.1268 题目描述
     *
     *   设计一个搜索自动补全系统。给定产品数组 products 和搜索词 searchWord，每输入一个字符后，推荐最多 3 个具有相同前缀的产品名（按字典序最小的 3 个）。
     *   输入: products = ["mobile","mouse","moneypot","monitor","mousepad"]
     *         searchWord = "mouse"
     *   输出: [
     *     ["mobile","moneypot","monitor"],  // 输入 'm'
     *     ["mobile","moneypot","monitor"],  // 输入 'mo'
     *     ["mouse","mousepad"],             // 输入 'mou'
     *     ["mouse","mousepad"],             // 输入 'mous'
     *     ["mouse","mousepad"]              // 输入 'mouse'
     *   ]
     */
    public static void main(String[] args) {
        // 测试用例 1：题目示例
        String[] products1 = {"mobile", "mouse", "moneypot", "monitor", "mousepad"};
        String searchWord1 = "mouse";
        String[][] expected1 = {
            {"mobile", "moneypot", "monitor"},
            {"mobile", "moneypot", "monitor"},
            {"mouse", "mousepad"},
            {"mouse", "mousepad"},
            {"mouse", "mousepad"}
        };
        runTest("题目示例", products1, searchWord1, expected1);

        // 测试用例 2：单一产品
        String[] products2 = {"havana"};
        String searchWord2 = "havana";
        String[][] expected2 = {
            {"havana"}, {"havana"}, {"havana"}, {"havana"}, {"havana"}, {"havana"}
        };
        runTest("单一产品", products2, searchWord2, expected2);

        // 测试用例 3：无匹配前缀
        String[] products3 = {"abc", "def", "ghi"};
        String searchWord3 = "xyz";
        String[][] expected3 = {{}, {}, {}};
        runTest("无匹配前缀", products3, searchWord3, expected3);

        // 测试用例 4：超过3个候选，验证只取前3（按字典序）
        String[] products4 = {"baggage", "bags", "banner", "banana", "band", "bath"};
        String searchWord4 = "ba";
        String[][] expected4 = {
            {"baggage", "bags", "banana"},
            {"baggage", "bags", "banana"}
        };
        runTest("超过3个候选（取字典序前3）", products4, searchWord4, expected4);

        // 测试用例 5：搜索词长度 > 所有产品长度
        String[] products5 = {"a", "ab"};
        String searchWord5 = "abc";
        String[][] expected5 = {{"a", "ab"}, {"ab"}, {}};
        runTest("搜索词比产品长，中间无匹配", products5, searchWord5, expected5);
    }

    private static void runTest(String name, String[] products, String searchWord, String[][] expected) {
        System.out.println("=== 测试：" + name + " ===");
        System.out.println("products   = " + Arrays.toString(products));
        System.out.println("searchWord = " + searchWord);

        String[][] actual = search(products, searchWord);

        String expectedStr = array2dToString(expected);
        String actualStr   = array2dToString(actual);
        boolean pass = expectedStr.equals(actualStr);

        System.out.println("期望: " + expectedStr);
        System.out.println("结果: " + actualStr);
        System.out.println(pass ? "✅ PASS" : "❌ FAIL");
        System.out.println();
    }

    private static String array2dToString(String[][] arr) {
        List<List<String>> list = new ArrayList<>();
        for (String[] row : arr) {
            list.add(row != null ? Arrays.asList(row) : new ArrayList<>());
        }
        return list.toString();
    }

    /**
     * 核心：先排序再遍历 + 关键剪枝操作
     * 注意点: string 比较大小用 compareTo 字典序；Arrays sort 是递增排序。
     */
    private static String[][] search(String[] products, String searchWord) {
        // 两个语法注意点：
        //  1. string 比较大小的方式 compareTo ，字典序
        //  2. Arrays sort 是递增排序
        Arrays.sort(products, (String p1, String p2) -> p1.compareTo(p2));

        List<List<String>> result = new ArrayList<>();
        for (int i = 1; i <= searchWord.length(); ++i) {
            result.add(new ArrayList<>());
            String prefix = searchWord.substring(0, i);

            for (int j = 0; j < products.length; ++j) {
                if (products[j].startsWith(prefix)) {
                    result.get(i - 1).add(products[j]);
                    // 最多取3个
                    if (result.get(i - 1).size() >= 3) {
                        break;
                    }
                } else {
                    // 剪枝：已排序，一旦产品字典序 > prefix 且不匹配，后续都不可能匹配
                    if (products[j].compareTo(prefix) > 0) {
                        break;
                    }
                }
            }
        }

        String[][] resultArray = new String[result.size()][];
        for (int i = 0; i < result.size(); ++i) {
            String[] array = new String[result.get(i).size()];
            result.get(i).toArray(array);
            resultArray[i] = array;
        }
        return resultArray;
    }
}
