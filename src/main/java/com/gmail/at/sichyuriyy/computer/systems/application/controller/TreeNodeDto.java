package com.gmail.at.sichyuriyy.computer.systems.application.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNodeDto {

    private NameAware text;
    private List<TreeNodeDto> children = new ArrayList<>();

    @Data
    @AllArgsConstructor
    static class NameAware {
        private String name;
    }
}
