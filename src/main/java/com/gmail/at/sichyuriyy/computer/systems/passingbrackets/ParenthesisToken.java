package com.gmail.at.sichyuriyy.computer.systems.passingbrackets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParenthesisToken {

    private boolean negative;
    @Builder.Default
    private List<String> multiplyVars = new ArrayList<>();
    @Builder.Default
    private List<String> divideVars = new ArrayList<>();
    @Builder.Default
    private List<ParenthesisExpression> multiplyExpressions = new ArrayList<>();
    @Builder.Default
    private List<ParenthesisExpression> divideExpressions = new ArrayList<>();
    @Builder.Default
    private List<FunctionExpression> multiplyFunctions = new ArrayList<>();
    @Builder.Default
    private List<FunctionExpression> divideFunctions = new ArrayList<>();

    public void addValue(boolean multiply, String val) {
        if (val.equals("1")) {
            return;
        }
        if (multiply) {
            multiplyVars.add(val);
        } else {
            divideVars.add(val);
        }
    }

    public void addExpression(boolean multiply, ParenthesisExpression expression) {
        if (multiply) {
            multiplyExpressions.add(expression);
        } else {
            divideExpressions.add(expression);
        }
    }

    public void addFunction(boolean multiply, String value, ParenthesisExpression exp) {
        FunctionExpression func = new FunctionExpression(value, exp);
        if (multiply) {
            multiplyFunctions.add(func);
        } else {
            divideFunctions.add(func);
        }
    }
}