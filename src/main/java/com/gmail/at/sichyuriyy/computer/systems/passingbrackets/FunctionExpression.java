package com.gmail.at.sichyuriyy.computer.systems.passingbrackets;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FunctionExpression {
    private String functor;
    private ParenthesisExpression expression;

    public FunctionExpression makeClone() {
        return new FunctionExpression(functor, expression.makeClone());
    }

    @Override
    public String toString() {
        return functor + "(" + expression + ")";
    }
}
