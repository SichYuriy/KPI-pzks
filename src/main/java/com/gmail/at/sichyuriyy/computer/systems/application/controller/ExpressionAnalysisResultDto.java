package com.gmail.at.sichyuriyy.computer.systems.application.controller;

import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishToken;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionAnalysisResultDto {
    private String expression;
    private List<SyntaxError> errors = new ArrayList<>();
    private List<PolishToken> polishNotation;
    private TreeNodeDto root;
}
