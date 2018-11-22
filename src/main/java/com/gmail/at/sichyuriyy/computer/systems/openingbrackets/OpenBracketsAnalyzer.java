package com.gmail.at.sichyuriyy.computer.systems.openingbrackets;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.ParenthesisExpression;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.ParenthesisExpressionTransformer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class OpenBracketsAnalyzer {

    private HashMap<ParenthesisExpression, List<ParenthesisExpression>> equalForms = new HashMap<>();

    private ParenthesisExpressionTransformer transformer = new ParenthesisExpressionTransformer();

    public List<ParenthesisExpression> getAllForms(Expression expression) {
        ParenthesisExpression parenthesisExpression = transformer.transform(expression);
        return getAllForms(parenthesisExpression);
    }

    private List<ParenthesisExpression> getAllForms(ParenthesisExpression expression) {
        var result = equalForms.get(expression);
        if (result == null) {
            result = calculateEqualForms(expression);
            equalForms.put(expression, result);
        }
        return result;
    }

    private List<ParenthesisExpression> calculateEqualForms(ParenthesisExpression expression) {

        return null;
    }
}
