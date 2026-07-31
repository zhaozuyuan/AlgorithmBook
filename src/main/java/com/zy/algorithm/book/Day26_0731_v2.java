package com.zy.algorithm.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day26_0731_v2 {

    /**
     * leetcode.1325 删除给定值的叶子节点（中等）
     * 给你一棵以 root 为根的二叉树和一个整数 target，请你删除所有值为 target 的叶子节点。
     * 注意，一旦删除值为 target 的叶子节点，它的父节点可能会变成叶子节点；如果新叶子节点的值恰好也是 target，那么这个节点也应该被删除（你需要继续删除，直到不能再删除为止）。
     *
     * 示例 1：
     * 输入：root = [1,2,3,2,null,2,4], target = 2
     * 输出：[1,null,3,null,4]
     * 解释：绿色叶子节点（值为 2）被删除，删除后父节点又变为值为 2 的叶子节点，继续删除。
     */
    public static void main(String[] args) {
        Day26_0731_v2 s = new Day26_0731_v2();

        // 官方示例 1
        s.runTest(Arrays.asList(1, 2, 3, 2, null, 2, 4), 2, Arrays.asList(1, null, 3, null, 4));
        // 官方示例 2
        s.runTest(Arrays.asList(1, 3, 3, 3, 2), 3, Arrays.asList(1, 3, null, null, 2));
        // 官方示例 3：连续删除连锁反应
        s.runTest(Arrays.asList(1, 2, null, 2, null, 2), 2, Arrays.asList(1));
        // 根节点值为 target 但不是叶子：不应删除
        s.runTest(Arrays.asList(2, 1, 3), 2, Arrays.asList(2, 1, 3));
        // 根节点值为 target，左子树保留、右子叶删除：根仍保留
        s.runTest(Arrays.asList(2, 1, 2), 2, Arrays.asList(2, 1));
        // 全部删光，根变为叶子后也被删
        s.runTest(Arrays.asList(2, 2, 2), 2, Arrays.asList());
        // 单节点根
        s.runTest(Arrays.asList(2), 2, Arrays.asList());
        // 没有匹配值，整棵树保留
        s.runTest(Arrays.asList(1, 2, 3), 5, Arrays.asList(1, 2, 3));
    }

    private void runTest(List<Integer> treeVals, int target, List<Integer> expected) {
        Node root = buildTree(treeVals);
        Node actualRoot = deleteLeaf(root, target);
        List<Integer> actual = toList(actualRoot);
        String status = actual.equals(expected) ? "PASS" : "FAIL";
        System.out.println("tree=" + treeVals + ", target=" + target
                + "  expected=" + expected + ", actual=" + actual + "  [" + status + "]");
    }

    // 按层级顺序（含 null）构造二叉树
    private Node buildTree(List<Integer> vals) {
        if (vals == null || vals.isEmpty()) {
            return null;
        }
        Node root = new Node(vals.get(0));
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        int idx = 1;
        while (!queue.isEmpty() && idx < vals.size()) {
            Node cur = queue.poll();
            Integer lv = vals.get(idx++);
            if (lv != null) {
                cur.left = new Node(lv);
                queue.offer(cur.left);
            }
            if (idx < vals.size()) {
                Integer rv = vals.get(idx++);
                if (rv != null) {
                    cur.right = new Node(rv);
                    queue.offer(cur.right);
                }
            }
        }
        return root;
    }

    // 层级遍历序列化，去掉尾部 null
    private List<Integer> toList(Node root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur == null) {
                res.add(null);
                continue;
            }
            res.add(cur.val);
            queue.offer(cur.left);
            queue.offer(cur.right);
        }
        while (!res.isEmpty() && res.get(res.size() - 1) == null) {
            res.remove(res.size() - 1);
        }
        return res;
    }

    class Node {
        int val;
        Node left;
        Node right;

        Node() {
        }

        Node(int _val) {
            val = _val;
        }
    }

    // 先删子节点，再删父节点，后序遍历
    private static Node deleteLeaf(Node node, int target) {
        delete(null, node, target);
        if (node.val == target && node.left == null && node.right == null) {
            return null;
        } else {
            return node;
        }
    }

    private static void delete(Node root, Node node, int target) {
        if (node == null) {
            return;
        }

        delete(node, node.left, target);
        delete(node, node.right, target);

        if (target == node.val) {
            if (root != null && node.left == null && node.right == null) {
                if (root.left == node) {
                    root.left = null;
                } else {
                    root.right = null;
                }
            }
        }
    }
}
