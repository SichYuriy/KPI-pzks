package com.gmail.at.sichyuriyy.computer.systems.expressiontree.optimizer;

import com.gmail.at.sichyuriyy.computer.systems.expressiontree.TreeNode;
import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishToken;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class OneWayTreeOptimizer extends AbstractOperationsInRowOptimizer {

    @Override
    protected TreeNode optimizeOperationsInARow(int operationsCount, ArrayList<TreeNode> orderedLeafs, PolishToken operation) {
        int nextLeafTakeIndex = 0;
        int operationsLeft = operationsCount - 1;
        ArrayDeque<TreeNode> currentLevelOperations = new ArrayDeque<>();
        List<TreeNode> nextLevelOperations = new ArrayList<>();

        TreeNode rootOperation = new TreeNode(new PolishToken(operation.getValue(), operation.getType()));
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
                    TreeNode nextOperation = new TreeNode(new PolishToken(operation.getValue(), operation.getType()));
                    it.setLeft(nextOperation);
                    nextLevelOperations.add(nextOperation);
                } else {
                    it.setLeft(orderedLeafs.get(nextLeafTakeIndex));
                    nextLeafTakeIndex++;
                }
                currentLevelOperations.pollLast();
                continue;
            }

            TreeNode next;
            TreeNode nextLeaf = orderedLeafs.get(nextLeafTakeIndex);
            if (operationsLeft == 0
                    || nextLeaf.getOptimizedHeight() >= calculateOneWayTreeHeight(orderedLeafs, operationsLeft)) {
                next = nextLeaf;
                nextLeafTakeIndex++;
            } else {
                next = new TreeNode(new PolishToken(operation.getValue(), operation.getType()));
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
        return rootOperation;
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
}
