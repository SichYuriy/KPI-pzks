package com.gmail.at.sichyuriyy.computer.systems.expressiontree.optimizer;

import com.gmail.at.sichyuriyy.computer.systems.expressiontree.TreeNode;
import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishToken;
import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishTokenType;

import java.util.ArrayDeque;
import java.util.ArrayList;

public abstract class AbstractOperationsInRowOptimizer implements TreeOptimizer {

    @Override
    public TreeNode optimize(TreeNode node) {
        if (!node.getPolishToken().getType().equals(PolishTokenType.BINARY_PLUS)
                && !node.getPolishToken().getType().equals(PolishTokenType.MULTIPLY)
                && !node.getPolishToken().getType().equals(PolishTokenType.BINARY_MINUS)
                && !node.getPolishToken().getType().equals(PolishTokenType.DIVIDE)) {
            return optimizeChildrenOnly(node);
        }
        if (node.getPolishToken().getType().equals(PolishTokenType.BINARY_MINUS)
                || node.getPolishToken().getType().equals(PolishTokenType.DIVIDE)) {
            return optimizeDivideOrMinusRow(node);
        }

        PolishToken operationToken =  node.getPolishToken();
        PolishTokenType operation = operationToken.getType();
        int operationsInARowCount = calculateOperationsInARow(node, operation);
        if (operationsInARowCount == 1) {
            return optimizeChildrenOnly(node);
        }
        ArrayList<TreeNode> leafs = getOptimizedLeafs(node, operation);
        leafs.sort((leaf1, leaf2) -> leaf2.getOptimizedHeight() - leaf1.getOptimizedHeight());

        TreeNode result = optimizeOperationsInARow(operationsInARowCount, leafs, operationToken);

        calculateHeightOfOperations(result, operation);
        return result;
    }

    private TreeNode optimizeDivideOrMinusRow(TreeNode node) {
        PolishTokenType currentType = node.getPolishToken().getType();
        if (!node.getLeft().getPolishToken().getType().equals(currentType)) {
            return optimizeChildrenOnly(node);
        }
        ArrayList<TreeNode> leafs = getLeftRowLeafs(node);
        TreeNode root = new TreeNode(new PolishToken(node.getPolishToken().getValue(), currentType));
        root.setLeft(leafs.get(leafs.size() - 1));
        leafs.remove(leafs.size() - 1);
        PolishTokenType reverseType = currentType.equals(PolishTokenType.BINARY_MINUS) ?
                PolishTokenType.BINARY_PLUS : PolishTokenType.MULTIPLY;
        String reverseValue = currentType.equals(PolishTokenType.BINARY_MINUS) ? "+" : "*";
        PolishToken reverseToken = new PolishToken(reverseValue, reverseType);
        TreeNode currentNode = root;
        for (int i = 0; i < leafs.size() - 1; i++) {
            TreeNode rightChild = new TreeNode(reverseToken);
            rightChild.setLeft(leafs.get(i));
            currentNode.setRight(rightChild);
            currentNode = rightChild;
        }
        currentNode.setRight(leafs.get(leafs.size() - 1));
        return optimizeChildrenOnly(root);
    }

    private ArrayList<TreeNode> getLeftRowLeafs(TreeNode node) {
        var tokenType = node.getPolishToken().getType();
        var currentNode = node;
        ArrayList<TreeNode> leafs = new ArrayList<>();
        while (currentNode.getPolishToken().getType().equals(tokenType)) {
            leafs.add(currentNode.getRight());
            currentNode = currentNode.getLeft();
        }
        leafs.add(currentNode);
        return leafs;
    }

    protected abstract TreeNode optimizeOperationsInARow(int operationsCount, ArrayList<TreeNode> orderedLeafs, PolishToken operation);

    private void calculateHeightOfOperations(TreeNode rootOperation, PolishTokenType operation) {
        int leftHeight;
        int rightHeight;
        if (rootOperation.getLeft().getPolishToken().getType().equals(operation)) {
            calculateHeightOfOperations(rootOperation.getLeft(), operation);
            leftHeight = rootOperation.getLeft().getOptimizedHeight();
        } else {
            leftHeight = rootOperation.getLeft().getOptimizedHeight();
        }
        if (rootOperation.getRight().getPolishToken().getType().equals(operation)) {
            calculateHeightOfOperations(rootOperation.getRight(), operation);
            rightHeight = rootOperation.getRight().getOptimizedHeight();
        } else {
            rightHeight = rootOperation.getRight().getOptimizedHeight();
        }

        rootOperation.setOptimizedHeight(Math.max(leftHeight + 1, rightHeight + 1));
    }

    @SuppressWarnings("ConstantConditions")
    private ArrayList<TreeNode> getOptimizedLeafs(TreeNode node, PolishTokenType operation) {
        ArrayDeque<TreeNode> operations = new ArrayDeque<>();
        operations.add(node);
        ArrayList<TreeNode> leafs = new ArrayList<>();
        while (!operations.isEmpty()) {
            TreeNode it = operations.pollFirst();
            if (it.getLeft().getPolishToken().getType().equals(operation)) {
                operations.addLast(it.getLeft());
            } else {
                leafs.add(optimize(it.getLeft()));
            }
            if (it.getRight().getPolishToken().getType().equals(operation)) {
                operations.add(it.getRight());
            } else {
                leafs.add(optimize(it.getRight()));
            }
        }
        return leafs;
    }

    private int calculateOperationsInARow(TreeNode node, PolishTokenType operation) {
        int result = 1;
        if (node.getLeft().getPolishToken().getType().equals(operation)) {
            result += calculateOperationsInARow(node.getLeft(), operation);
        }
        if (node.getRight().getPolishToken().getType().equals(operation)) {
            result += calculateOperationsInARow(node.getRight(), operation);
        }
        return result;
    }

    private TreeNode optimizeChildrenOnly(TreeNode node) {
        int leftHeight = 0;
        int rightHeight = 0;
        if (node.getLeft() != null) {
            node.setLeft(optimize(node.getLeft()));
            leftHeight = node.getLeft().getOptimizedHeight();
        }
        if (node.getRight() != null) {
            node.setRight(optimize(node.getRight()));
            rightHeight = node.getRight().getOptimizedHeight();
        }
        node.setOptimizedHeight(Math.max(leftHeight, rightHeight) + 1);
        return node;
    }
}
