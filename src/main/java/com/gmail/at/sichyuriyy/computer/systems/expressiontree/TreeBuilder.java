package com.gmail.at.sichyuriyy.computer.systems.expressiontree;

import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishToken;

import java.util.List;
import java.util.Stack;

public class TreeBuilder {

    public TreeNode buildTree(List<PolishToken> polishNotation) {
        Stack<TreeNode> treeNodes = new Stack<>();
        for (PolishToken token: polishNotation) {
            if (!token.getType().isOperation()) {
                treeNodes.add(new TreeNode(token));
            } else {
                if (token.getType().isBinary()) {
                    TreeNode right = treeNodes.pop();
                    TreeNode left = treeNodes.pop();
                    treeNodes.add(new TreeNode(token, left, right));
                } else {
                    TreeNode right = treeNodes.pop();
                    treeNodes.add(new TreeNode(token, null, right));
                }
            }
        }
        return treeNodes.pop();
    }
}
