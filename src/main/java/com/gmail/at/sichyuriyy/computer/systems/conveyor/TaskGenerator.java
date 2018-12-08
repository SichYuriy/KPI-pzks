package com.gmail.at.sichyuriyy.computer.systems.conveyor;

import com.gmail.at.sichyuriyy.computer.systems.expressiontree.TreeNode;
import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishTokenType;

import java.util.*;

import static java.util.Objects.requireNonNull;

public class TaskGenerator {

    private Map<Long, TreeNode> parentTasks = new HashMap<>();
    private List<Operation> availableTasks = new ArrayList<>();
    private Set<Long> executedTasks = new HashSet<>();
    private int totalTasksCount;

    public TaskGenerator(TreeNode root) {
        ArrayDeque<TreeNode> nodes = new ArrayDeque<>();
        long currentId = 1;
        nodes.addLast(root);
        root.setId(currentId++);
        while (!nodes.isEmpty()) {
            TreeNode node = requireNonNull(nodes.pollFirst());
            totalTasksCount++;
            boolean hasChild = false;
            if (node.getLeft() != null
                    && node.getLeft().getPolishToken().getType().isOperation()) {
                nodes.addLast(node.getLeft());
                node.getLeft().setId(currentId++);
                hasChild = true;
                parentTasks.put(node.getLeft().getId(), node);
            }
            if (node.getRight() != null
                    && node.getRight().getPolishToken().getType().isOperation()) {
                nodes.addLast(node.getRight());
                node.getRight().setId(currentId++);
                hasChild = true;
                parentTasks.put(node.getRight().getId(), node);
            }
            if (!hasChild) {
                makeAvailable(node);
            }
        }
    }

    public boolean hasMoreTasks() {
        return executedTasks.size() != totalTasksCount;
    }

    public Optional<Operation> takeAnyNext() {
        if (availableTasks.isEmpty()) {
            return Optional.empty();
        }
        Operation result = availableTasks.get(availableTasks.size() - 1);
        availableTasks.remove(availableTasks.size() - 1);
        return Optional.of(result);
    }

    public Optional<Operation> takeNextTypeOf(PolishTokenType type) {
       for (int i = availableTasks.size() - 1; i >=0; i--) {
           Operation task = availableTasks.get(i);
           if (task.getType().equals(type)) {
               availableTasks.remove(i);
               return Optional.of(task);
           }
       }
       return Optional.empty();
    }

    public void finishExecution(Operation operation) {
        executedTasks.add(operation.getId());
        TreeNode parent = parentTasks.get(operation.getId());
        if (parent == null) {
            return;
        }
        if (isLeftChildExecuted(parent) && isRightChildExecuted(parent)) {
            makeAvailable(parent);
        }
    }

    private boolean isLeftChildExecuted(TreeNode node) {
        return node.getLeft() == null
                || !node.getLeft().getPolishToken().getType().isOperation()
                || executedTasks.contains(node.getLeft().getId());
    }

    private boolean isRightChildExecuted(TreeNode node) {
        return node.getRight() == null
                || !node.getRight().getPolishToken().getType().isOperation()
                || executedTasks.contains(node.getRight().getId());
    }

    private void makeAvailable(TreeNode node) {
        Operation operation = new Operation(node.getId(), node.getPolishToken().getType(), node.getPolishToken().getValue());
        int indexToInsert = 0;
        Operation currentOperation = availableTasks.isEmpty() ? null : availableTasks.get(0);
        while (currentOperation != null && currentOperation.getId() < operation.getId()) {
            indexToInsert++;
            currentOperation = indexToInsert < availableTasks.size() ? availableTasks.get(indexToInsert) : null;
        }
        availableTasks.add(indexToInsert, operation);
    }
}
