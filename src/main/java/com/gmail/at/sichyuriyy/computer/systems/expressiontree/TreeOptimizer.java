package com.gmail.at.sichyuriyy.computer.systems.expressiontree;

import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishToken;
import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishTokenType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class TreeOptimizer {

    public TreeNode optimize(TreeNode node) {
        if (!node.getPolishToken().getType().equals(PolishTokenType.BINARY_PLUS)
                && !node.getPolishToken().getType().equals(PolishTokenType.MULTIPLY)) {
            return optimizeChildrenOnly(node);
        }
        PolishToken operationToken =  node.getPolishToken();
        PolishTokenType operation = operationToken.getType();
        int operationsInARowCount = calculateOperationsInARow(node, operation);
        if (operationsInARowCount == 1) {
            return optimizeChildrenOnly(node);
        }
        ArrayList<TreeNode> leafs = getOptimizedLeafs(node, operation);
        leafs.sort((leaf1, leaf2) -> leaf2.getOptimizedHeight() - leaf1.getOptimizedHeight());


        int nextLeafTakeIndex = 0;
        int operationsLeft = operationsInARowCount - 1;
        ArrayDeque<TreeNode> currentLevelOperations = new ArrayDeque<>();
        List<TreeNode> nextLevelOperations = new ArrayList<>();

        TreeNode rootOperation = new TreeNode(new PolishToken(operationToken.getValue(), operation));
        currentLevelOperations.add(rootOperation);

        while (!currentLevelOperations.isEmpty() || !nextLevelOperations.isEmpty()) {
            if(currentLevelOperations.isEmpty()) {
                currentLevelOperations.addAll(nextLevelOperations);
                nextLevelOperations.clear();
            }

            TreeNode it = currentLevelOperations.getLast();

            if (currentLevelOperations.size() == 1 &&
                    it.getRight() != null) {
                if (operationsLeft > 0) {
                    operationsLeft--;
                    TreeNode nextOperation = new TreeNode(new PolishToken(operationToken.getValue(), operation));
                    it.setLeft(nextOperation);
                    nextLevelOperations.add(nextOperation);
                } else {
                    it.setLeft(leafs.get(nextLeafTakeIndex));
                    nextLeafTakeIndex++;
                }
                currentLevelOperations.pollLast();
                continue;
            }

            TreeNode next;
            TreeNode nextLeaf = leafs.get(nextLeafTakeIndex);
            if (operationsLeft == 0
                    || nextLeaf.getOptimizedHeight() >= calculateOneWayTreeHeight(leafs, operationsLeft)) {
                next = nextLeaf;
                nextLeafTakeIndex++;
            } else {
                next = new TreeNode(new PolishToken(operationToken.getValue(), operation));
                operationsLeft--;
                nextLevelOperations.add(next);
            }

            if (it.getRight() == null) {
                it.setRight(next);
            } else {
                it.setLeft(next);
                currentLevelOperations.pollLast();
            }
        }
        calculateHeightOfOperations(rootOperation, operation);
        return rootOperation;
    }

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

    private int calculateOneWayTreeHeight(ArrayList<TreeNode> leafs, int operationsLeft) {
        int size = leafs.size();
        int max = Math.max(leafs.get(size - 1).getOptimizedHeight() + operationsLeft, leafs.get(size - 2).getOptimizedHeight() + operationsLeft);
        for (int i = 1, j = size - 3; i < operationsLeft; i++, j--) {
            int height = leafs.get(j).getOptimizedHeight() + operationsLeft - i;
            if (height > max) {
                max = height;
            }
        }
        return max;
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
