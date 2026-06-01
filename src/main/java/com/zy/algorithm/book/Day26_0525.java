package com.zy.algorithm.book;

import java.util.List;

public class Day26_0525 {

    public static void main(String[] args) {
        // 测试用例1: root = [25,1,3,1,3,0,2], 输出: "adz"
        Node root1 = new Node(25);
        root1.left = new Node(1);
        root1.right = new Node(3);
        root1.left.left = new Node(1);
        root1.left.right = new Node(3);
        root1.right.left = new Node(0);
        root1.right.right = new Node(2);
        System.out.println("测试1: " + findMinString(root1) + " (期望: adz)");

        // 测试用例2: root = [2,2,1,null,1,0,null,0], 输出: "abc"
        Node root2 = new Node(2);
        root2.left = new Node(2);
        root2.right = new Node(1);
        root2.left.right = new Node(1);
        root2.right.left = new Node(0);
        root2.left.right.left = new Node(0);
        System.out.println("测试2: " + findMinString(root2) + " (期望: abc)");
    }

    /**
     * 给定一颗根结点为 root 的二叉树，树中的每一个结点都有一个 [0, 25] 范围内的值，分别代表字母 'a' 到 'z'。
     * 返回 按字典序最小的字符串，该字符串从这棵树的一个叶结点开始，到根结点结束。
     * 注：字符串中任何较短的前缀在 字典序上 都是 较小 的：
     * 例如，在字典序上 "ab" 比 "aba" 要小。叶结点是指没有子结点的结点。
     * leetcode.988
     * @return
     */
    private static String sMinString = null;
    private static String findMinString(Node root) {
        dfs(root, new StringBuilder());
        return sMinString;
    }

    // 深度遍历，把每种字符串拼接出来
    private static void dfs(Node node, StringBuilder stringBuilder) {
        if (node == null) {
            return;
        }

        stringBuilder.append((char) (node.value + 'a'));
        // 叶子节点
        if (node.left == null && node.right == null) {
            // reverse StringBuilder 翻转字符串方法
            String currentString = stringBuilder.reverse().toString();
            if (sMinString == null || sMinString.compareTo(currentString) >= 0) {
                sMinString = currentString;
            }
            // 最后需要翻转回来
            stringBuilder.reverse();
        }

        dfs(node.left, stringBuilder);
        dfs(node.right, stringBuilder);

        // 最关键步骤，已经遍历完成的节点，需要删除
        stringBuilder.deleteCharAt(stringBuilder.length() - 1); 
    }

    static class Node {
        int value;
        Node left = null;
        Node right = null;
        Node(int value) { this.value = value; }
    }
}
