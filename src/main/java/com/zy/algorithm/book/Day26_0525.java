package com.zy.algorithm.book;

public class Day26_0525 {

    /**
     * 题目描述
     * 给定一颗根结点为 root 的二叉树，树中的每一个结点都有一个 [0, 25] 范围内的值，分别代表字母 'a' 到 'z'。
     * 返回 按字典序最小 的字符串，该字符串从这棵树的一个叶结点开始，到根结点结束。
     * ▎ 注：字符串中任何较短的前缀在字典序上都是较小的。例如 "ab" 比 "aba" 要小。叶结点是指没有子结点的结点。
     *
     * 示例 1：
     * 输入：root = [0,1,2,3,4,3,4]
     * 输出："dba"
     *
     *       a(0)
     *      /    \
     *    b(1)   c(2)
     *    / \    / \
     *  d(3) e(4) d(3) e(4)
     *
     * 叶→根路径：
     *   3→1→0: "dba"  ← 最小
     *   4→1→0: "eba"
     *   3→2→0: "dca"
     *   4→2→0: "eca"
     */
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

    // dfs 深度遍历 + 回溯。 (bfs 是广播遍历)
    private static String findMinString(Node node) {
        sMinString = null;
        dfs(node, new StringBuilder());
        return sMinString;
    }

    private static String sMinString = null;
    private static void dfs(Node node, StringBuilder builder) {
        builder.append((char) (node.value + 'a'));

        // 代表 dfs 一轮结束
        if (node.left == null && node.right == null) {
            String currentString = builder.reverse().toString();
            if (sMinString == null || currentString.compareTo(sMinString) < 0) {
                sMinString = currentString;
            }
            builder.reverse();
        } else {
            if (node.left != null) {
                dfs(node.left, builder);
            }
            if (node.right != null) {
                dfs(node.right, builder);
            }
        }

        // 一次遍历结束，回退当前值
        builder.deleteCharAt(builder.length() - 1);
    }

    static class Node {
        int value;
        Node left = null;
        Node right = null;
        Node(int value) { this.value = value; }
    }
}
