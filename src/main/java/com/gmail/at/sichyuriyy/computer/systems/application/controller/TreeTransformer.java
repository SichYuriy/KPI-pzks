package com.gmail.at.sichyuriyy.computer.systems.application.controller;

import com.gmail.at.sichyuriyy.computer.systems.expressiontree.TreeNode;

public class TreeTransformer {

    public TreeNodeDto toDto(TreeNode node) {
        TreeNodeDto nodeDto = new TreeNodeDto();
        nodeDto.setText(new TreeNodeDto.NameAware(node.getPolishToken().getValue()));
        if (node.getLeft() != null) {
            nodeDto.getChildren().add(toDto(node.getLeft()));
        }
        if (node.getRight() != null) {
            nodeDto.getChildren().add(toDto(node.getRight()));
        }
        return nodeDto;
    }
}
