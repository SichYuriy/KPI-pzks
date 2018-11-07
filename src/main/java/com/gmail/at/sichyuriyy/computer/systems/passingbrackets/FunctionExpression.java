package com.gmail.at.sichyuriyy.computer.systems.passingbrackets;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FunctionExpression {
    private String functor;
    private ParenthesisExpression expression;
}
