package com.gmail.at.sichyuriyy.computer.systems.expressiontree;

import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishToken;
import lombok.Data;

@Data
public class TreeNode {
    private PolishToken polishToken;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(PolishToken polishToken) {
        this.polishToken = polishToken;
    }

    public TreeNode(PolishToken polishToken, TreeNode left, TreeNode right) {
        this.polishToken = polishToken;
        this.left = left;
        this.right = right;
    }
}
