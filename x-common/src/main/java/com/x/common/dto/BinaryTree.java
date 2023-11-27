package com.x.common.dto;

import com.x.common.utils.BinaryTreeUtil;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author : xuemingqi
 * @since : 2023/10/31 10:27
 */
@Data
@Builder
public class BinaryTree {
    /**
     * 值
     */
    private int value;

    /**
     * 左子树
     */
    private BinaryTree left;

    /**
     * 右子树
     */
    private BinaryTree right;

    /**
     * 数深度
     *
     * @return 深度
     */
    public int depth() {
        return BinaryTreeUtil.depth(this, 0);
    }

    /**
     * 添加子节点
     *
     * @param child 子节点
     */
    public void add(BinaryTree child) {
        if (child == null) {
            return;
        }
        getBiConsumer(child.value).accept(this, child);
    }

    public void add(int value) {
        getBiConsumer(value).accept(this, BinaryTree.builder().value(value).build());
    }

    /**
     * 批量添加子节点
     *
     * @param children 子节点集合
     */
    public void addNode(Collection<BinaryTree> children) {
        children.forEach(this::add);
    }

    public void addValue(Collection<Integer> children) {
        children.forEach(this::add);
    }

    /**
     * 从二叉树中删除具有给定值的节点。
     *
     * @param value 要删除的节点的值
     */
    public void delete(int value) {
        BinaryTreeUtil.deleteNode(this, value);
    }

    /**
     * 从二叉树中删除具有给定值的节点。
     *
     * @param values 要删除的节点的值集合
     */
    public void delete(Collection<Integer> values) {
        values.forEach(this::delete);
    }

    /**
     * 查找子节点深度
     *
     * @param targetValue 子节点值
     * @return 子节点深度
     */
    public int findDepthByValue(int targetValue) {
        return BinaryTreeUtil.findDepthByValue(this, targetValue, 0);
    }

    /**
     * 转换为平衡二叉树
     *
     * @return 平衡二叉树
     */
    public void convertToBalancedTree() {
        BinaryTree binaryTree = BinaryTreeUtil.convertToBalancedTree(this);
        this.value = binaryTree.value;
        this.left = binaryTree.left;
        this.right = binaryTree.right;
    }

    /**
     * 中序遍历二叉树
     *
     * @return 遍历结果
     */
    public List<Integer> inOrderTraversal() {
        List<Integer> list = new ArrayList<>();
        BinaryTreeUtil.inOrderTraversal(this, list);
        return list;
    }

    /**
     * 获取函数式
     *
     * @param target 值
     * @return 函数式
     */
    private BiConsumer<BinaryTree, BinaryTree> getBiConsumer(int target) {
        return target >= value ? BinaryTreeUtil::addRight : BinaryTreeUtil::addLeft;
    }
}
