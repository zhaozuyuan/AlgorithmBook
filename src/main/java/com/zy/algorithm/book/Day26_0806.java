package com.zy.algorithm.book;

import java.util.*;

public class Day26_0806 {

    /**
     * leetcode.114 给你二叉树的根结点 root，请你将它展开为一个单链表：
     *
     * - 展开后的单链表应该同样使用 TreeNode，其中 right 子指针指向链表中下一个结点，而左子指针 left 始终为 null。
     * - 展开后的单链表应该与二叉树 先序遍历 顺序相同。
     *
     * 示例 1：
     *
     * 输入：root = [1,2,5,3,4,null,6]
     * 输出：[1,null,2,null,3,null,4,null,5,null,6]
     */
    public static void main(String[] args) {
        Day26_0806 t = new Day26_0806();
        // 测试用例 1：题目示例 1
        t.run("测试用例 1（题目示例 1）: [1,2,5,3,4,null,6]",
                t.buildTree(new Integer[]{1, 2, 5, 3, 4, null, 6}),
                new int[]{1, 2, 3, 4, 5, 6});
        // 测试用例 2：题目示例 2，空树
        t.run("测试用例 2（题目示例 2，空树）: []",
                t.buildTree(new Integer[0]),
                new int[0]);
        // 测试用例 3：题目示例 3，单节点
        t.run("测试用例 3（题目示例 3）: [0]",
                t.buildTree(new Integer[]{0}),
                new int[]{0});
        // 测试用例 4：完全二叉树
        t.run("测试用例 4（完全二叉树）: [1,2,3,4,5,6,7]",
                t.buildTree(new Integer[]{1, 2, 3, 4, 5, 6, 7}),
                new int[]{1, 2, 4, 5, 3, 6, 7});
        // 测试用例 5：左斜树
        t.run("测试用例 5（左斜树）: [1,2,null,3]",
                t.buildTree(new Integer[]{1, 2, null, 3}),
                new int[]{1, 2, 3});
        // 测试用例 6：右斜树
        t.run("测试用例 6（右斜树）: [1,null,2,null,3]",
                t.buildTree(new Integer[]{1, null, 2, null, 3}),
                new int[]{1, 2, 3});
        // 测试用例 7：混合树（左右子树形态不规则）
        t.run("测试用例 7（混合树）: [1,2,3,4,null,null,5]",
                t.buildTree(new Integer[]{1, 2, 3, 4, null, null, 5}),
                new int[]{1, 2, 4, 3, 5});
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
    }

    // 先序遍历：根->左->右，从根节点开始遍历，优先访问左节点，再访问右节点，所以其实是深度遍历
    private static TreeNode tree2LinkedList(TreeNode root) {
        if (root == null) {
            return null;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        List<TreeNode> nodeList = new ArrayList<>();
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            nodeList.add(node);

            TreeNode left = node.left;
            TreeNode right = node.right;
            // 先把 right 压栈，才能保证 right 后访问
            if (right != null) {
                stack.add(right);
            }
            if (left != null) {
                stack.add(left);
            }
        }

        TreeNode preNode = root;
        for (int i = 1; i < nodeList.size(); ++i) {
            preNode.left = null;
            preNode.right = nodeList.get(i);
            preNode = preNode.right;
        }
        return root;
    }

    /**
     * 根据层序数组构造二叉树，null 表示空节点
     */
    private TreeNode buildTree(Integer[] arr) {
        if (arr.length == 0 || arr[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (i < arr.length) {
            TreeNode node = q.poll();
            if (arr[i] != null) {
                node.left = new TreeNode(arr[i]);
                q.offer(node.left);
            }
            ++i;
            if (i < arr.length && arr[i] != null) {
                node.right = new TreeNode(arr[i]);
                q.offer(node.right);
            }
            ++i;
        }
        return root;
    }

    private static String fmt(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(arr[i]);
        }
        return sb.append(']').toString();
    }

    /**
     * 执行一个测试用例：调用解法（返回展开后的链表头），
     * 从链表头沿 right 走链收集实际结果，并校验 left 指针与返回头节点
     */
    private static void run(String desc, TreeNode root, int[] expected) {
        System.out.println("========== " + desc + " ==========");
        try {
            TreeNode head = tree2LinkedList(root);
            List<Integer> actualList = new ArrayList<>();
            boolean leftOk = true;
            int guard = 0; // 防御成环：最多走 3000 步（题目约束节点数 <= 2000）
            for (TreeNode node = head; node != null && guard <= 3000; node = node.right) {
                actualList.add(node.val);
                if (node.left != null) {
                    leftOk = false;
                }
                ++guard;
            }
            int[] actual = actualList.stream().mapToInt(Integer::intValue).toArray();
            boolean ok = Arrays.equals(expected, actual);
            System.out.println("预期结果: " + fmt(expected));
            System.out.println("实际结果: " + fmt(actual));
            System.out.println("链表头 == root: " + (head == root ? "✓" : "✗"));
            System.out.println("左指针检查: " + (leftOk ? "✓ 全部为 null" : "✗ 存在非 null 的 left"));
            System.out.println("对比结果: " + (ok ? "✓ 一致" : "✗ 不一致"));
        } catch (Exception e) {
            System.out.println("预期结果: " + fmt(expected));
            System.out.println("实际结果: 抛出异常 -> " + e);
        }
        System.out.println();
    }
}
