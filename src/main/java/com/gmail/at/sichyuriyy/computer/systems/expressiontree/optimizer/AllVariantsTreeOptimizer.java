package com.gmail.at.sichyuriyy.computer.systems.expressiontree.optimizer;

import com.gmail.at.sichyuriyy.computer.systems.expressiontree.TreeNode;
import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AllVariantsTreeOptimizer extends AbstractOperationsInRowOptimizer {

    @Override
    protected TreeNode optimizeOperationsInARow(int operationsCount, ArrayList<TreeNode> orderedLeafs, PolishToken operation) {
        ArrayList<Integer> levels = getTheBestTree(operationsCount, orderedLeafs.stream()
                .map(TreeNode::getOptimizedHeight)
                .collect(Collectors.toCollection(ArrayList::new)));
        return buildTree(levels, orderedLeafs, operation);
    }

    private TreeNode buildTree(ArrayList<Integer> operationsLevels, ArrayList<TreeNode> orderedLeafs, PolishToken operation) {
        TreeNode root = new TreeNode(new PolishToken(operation.getValue(), operation.getType()));
        LinkedList<TreeNode> prevLevel = new LinkedList<>();
        LinkedList<TreeNode> currentLevel = new LinkedList<>();
        prevLevel.add(root);
        int nextLeafToTake = 0;

        for (int i = 1; i < operationsLevels.size(); i++) {
            for (int j = 0; j < operationsLevels.get(i); j++) {
                TreeNode operationToAdd = new TreeNode(new PolishToken(operation.getValue(), operation.getType()));
                currentLevel.add(operationToAdd);
                TreeNode last = prevLevel.getLast();
                if (last.getRight() == null) {
                    last.setRight(operationToAdd);
                } else {
                    last.setLeft(operationToAdd);
                    prevLevel.removeLast();
                }
            }
            nextLeafToTake = fillLevelWithLeafs(prevLevel, orderedLeafs, nextLeafToTake);

            prevLevel = currentLevel;
            currentLevel = new LinkedList<>();
        }
        fillLevelWithLeafs(prevLevel, orderedLeafs, nextLeafToTake);
        return root;
    }

    private int fillLevelWithLeafs(List<TreeNode> level, ArrayList<TreeNode> orderedLeafs, int nextLeafToTake) {
        for (TreeNode node : level) {
            if (node.getLeft() == null) {
                node.setLeft(orderedLeafs.get(nextLeafToTake++));
            }
            if (node.getRight() == null) {
                node.setRight(orderedLeafs.get(nextLeafToTake++));
            }
        }
        return nextLeafToTake;
    }

    private ArrayList<Integer> getTheBestTree(int operationsCount, ArrayList<Integer> orderedLeafs) {
        ArrayList<Integer> bestLevelsDistribution = new ArrayList<>();
        AtomicInteger minHeight = new AtomicInteger(Integer.MAX_VALUE);
        goThroughAllVariants(operationsCount, (levels) -> {
            int height = calculateHeight(levels, orderedLeafs);
            if (height < minHeight.get()) {
                minHeight.set(height);
                bestLevelsDistribution.clear();
                bestLevelsDistribution.addAll(levels);
            }
        });
        return bestLevelsDistribution;
    }

    private int calculateHeight(List<Integer> levels, ArrayList<Integer> orderedLeafs) {
        int maxHeight = 0;
        int nexLeafTake = 0;
        for (int currentLevel = 1; currentLevel < levels.size(); currentLevel++) {
            for (int i = 0; i < levels.get(currentLevel - 1) * 2 - levels.get(currentLevel); i++) {
                int height = orderedLeafs.get(nexLeafTake++) + currentLevel;
                if (height > maxHeight) {
                    maxHeight = height;
                }
            }
        }
        int lastLevel = levels.size();
        for (int i = 0; i < levels.get(levels.size() - 1); i++) {
            int height = orderedLeafs.get(nexLeafTake++) + lastLevel;
            if (height > maxHeight) {
                maxHeight = height;
            }
        }
        return maxHeight;
    }

    private void goThroughAllVariants(int operationsCount, TreeVariantConsumer func) {
        LinkedList<Integer> levels = new LinkedList<>();
        levels.add(1);
        goThroughAllVariants(operationsCount - 1, levels, func);
    }

    private void goThroughAllVariants(int operationsLeft, LinkedList<Integer> levels, TreeVariantConsumer func) {
        for (int i = 0; i < operationsLeft && i < levels.getLast() * 2; i++) {
            int nextLevel = i + 1;
            levels.add(nextLevel);
            if (operationsLeft - nextLevel == 0) {
                func.apply(levels);
            } else {
                goThroughAllVariants(operationsLeft - nextLevel, levels, func);
            }
            levels.removeLast();
        }
    }

    interface TreeVariantConsumer {
        void apply(List<Integer> levels);
    }
}
