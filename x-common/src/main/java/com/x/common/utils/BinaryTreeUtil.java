package com.x.common.utils;

import com.x.common.dto.BinaryTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2023/10/31 10:45
 */
public class BinaryTreeUtil {

    /**
     * 计算二叉树深度
     *
     * @param binaryTree 二叉树
     * @param depth      深度
     * @return 二叉树深度
     */
    public static int depth(BinaryTree binaryTree, int depth) {
        if (binaryTree == null) {
            return depth;
        }
        return Math.max(depth(binaryTree.getLeft(), depth + 1), depth(binaryTree.getRight(), depth + 1));
    }

    /**
     * 向二叉树左子结点添加新节点
     *
     * @param parent 二叉树
     * @param child  新节点
     */
    public static void addLeft(BinaryTree parent, BinaryTree child) {
        if (child == null) {
            return;
        }
        if (parent.getLeft() == null) {
            parent.setLeft(child);
        } else {
            parent.getLeft().add(child);
        }
    }

    /**
     * 向二叉树右子结点添加新节点
     *
     * @param parent 二叉树
     * @param child  新节点
     */
    public static void addRight(BinaryTree parent, BinaryTree child) {
        if (child == null) {
            return;
        }
        if (parent.getRight() == null) {
            parent.setRight(child);
        } else {
            parent.getRight().add(child);
        }
    }

    /**
     * 从二叉树中删除具有给定值的节点。
     *
     * @param binaryTree  二叉树
     * @param targetValue 要删除的节点的值
     * @return 更新后的二叉树
     */
    public static BinaryTree deleteNode(BinaryTree binaryTree, int targetValue) {
        if (binaryTree == null) {
            return null;
        }

        if (targetValue < binaryTree.getValue()) {
            binaryTree.setLeft(deleteNode(binaryTree.getLeft(), targetValue));
        } else if (targetValue > binaryTree.getValue()) {
            binaryTree.setRight(deleteNode(binaryTree.getRight(), targetValue));
        } else {
            if (binaryTree.getLeft() == null) {
                return binaryTree.getRight();
            } else if (binaryTree.getRight() == null) {
                return binaryTree.getLeft();
            } else {
                binaryTree.setValue(findMinValue(binaryTree.getRight()));
                binaryTree.setRight(deleteNode(binaryTree.getRight(), binaryTree.getValue()));
            }
        }
        return binaryTree;
    }

    /**
     * 查找子节点深度
     *
     * @param binaryTree   二叉树
     * @param targetValue  子节点值
     * @param currentDepth 深度
     * @return 子节点深度
     */
    public static int findDepthByValue(BinaryTree binaryTree, int targetValue, int currentDepth) {
        if (binaryTree == null) {
            return -1;
        }

        if (binaryTree.getValue() == targetValue) {
            return currentDepth;
        }

        // 递归搜索左子树和右子树
        int leftDepth = findDepthByValue(binaryTree.getLeft(), targetValue, currentDepth + 1);
        if (leftDepth != -1) {
            return leftDepth;
        }
        return findDepthByValue(binaryTree.getRight(), targetValue, currentDepth + 1);
    }


    /**
     * 使用数组中的值构建平衡二叉树
     *
     * @param values 节点值
     * @param start  开始值
     * @param end    结束值
     * @return 平衡二叉树
     */
    public static BinaryTree buildBalancedTree(List<Integer> values, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        return BinaryTree.builder()
                .value(values.get(mid))
                .left(buildBalancedTree(values, start, mid - 1))
                .right(buildBalancedTree(values, mid + 1, end))
                .build();
    }

    /**
     * 将原始二叉树转换为平衡二叉树
     *
     * @param binaryTree 二叉树
     * @return 平衡二叉树
     */
    public static BinaryTree convertToBalancedTree(BinaryTree binaryTree) {
        List<Integer> values = new ArrayList<>();
        inOrderTraversal(binaryTree, values);
        return buildBalancedTree(values, 0, values.size() - 1);
    }

    /**
     * 中序遍历原始二叉树并将节点值存储在数组中
     *
     * @param binaryTree 二叉树
     * @param values     二叉树节点值
     */
    public static void inOrderTraversal(BinaryTree binaryTree, List<Integer> values) {
        if (binaryTree == null) {
            return;
        }
        inOrderTraversal(binaryTree.getLeft(), values);
        values.add(binaryTree.getValue());
        inOrderTraversal(binaryTree.getRight(), values);
    }

    /**
     * 查找给定二叉树中的最小值。
     *
     * @param binaryTree 二叉树
     * @return 最小值
     */
    private static int findMinValue(BinaryTree binaryTree) {
        while (binaryTree.getLeft() != null) {
            binaryTree = binaryTree.getLeft();
        }
        return binaryTree.getValue();
    }

    public static void main(String[] args) {
        BinaryTree b1 = BinaryTree.builder().value(1).build();
        BinaryTree b2 = BinaryTree.builder().value(2).build();
        BinaryTree b3 = BinaryTree.builder().value(3).build();
        BinaryTree b4 = BinaryTree.builder().value(4).build();
        BinaryTree b5 = BinaryTree.builder().value(5).build();
        BinaryTree b6 = BinaryTree.builder().value(6).build();
        BinaryTree b7 = BinaryTree.builder().value(7).build();
        BinaryTree b8 = BinaryTree.builder().value(8).build();

        BinaryTree b9 = BinaryTree.builder().value(9).build();
        b7.addNode(Arrays.asList(b8, b9, b3, b5, b1, b2, b4, b6));

        System.out.println(b7.depth());
        System.out.println(JsonUtil.toJsonStr(b7));
        System.out.println("-------------------------");

        b7.delete(3);
        System.out.println(JsonUtil.toJsonStr(b7));
        System.out.println("-------------------------");

        b7.convertToBalancedTree();
        System.out.println(JsonUtil.toJsonStr(b7));
        System.out.println("-------------------------");
    }
}
