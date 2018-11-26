package com.gmail.at.sichyuriyy.computer.systems.openingbrackets;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.ParenthesisExpression;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.ParenthesisToken;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.ParenthesisExpressionTransformer;

import java.util.ArrayList;
import java.util.List;

public class OpenBracketsAnalyzer {
    private ParenthesisExpressionTransformer transformer = new ParenthesisExpressionTransformer();

    public List<ParenthesisExpression> getAllForms(Expression expression) {
        ParenthesisExpression parenthesisExpression = transformer.transform(expression);
        List<ParenthesisExpression> result = new ArrayList<>();
        result.add(parenthesisExpression.makeClone());
        result.addAll(calculateEqualForms(parenthesisExpression));
        return result;
    }

    private List<ParenthesisExpression> calculateEqualForms(ParenthesisExpression expression) {
        List<ParenthesisExpression> result = new ArrayList<>();
        for (int i = 0; i < expression.getTerms().size(); i++) {
            ParenthesisToken current = expression.getTerms().get(i);
            List<ParenthesisToken> equalTokens = calculateEqualTokens(current);
            for (ParenthesisToken equalToken: equalTokens) {
                expression.getTerms().set(i, equalToken);
                result.add(expression.makeClone());
            }
        }

        ArrayList<ParenthesisToken> terms = new ArrayList<>();
        boolean changed = false;
        for (ParenthesisToken token: expression.getTerms()) {
            if (token.isExpression()) {
                List<ParenthesisToken> termsToAdd = token.getMultiplyExpressions().get(0).makeClone().getTerms();
                if (token.isNegative()) {
                    for (ParenthesisToken term : termsToAdd) {
                        term.setNegative(!term.isNegative());
                    }
                }
                terms.addAll(termsToAdd);
                changed = true;
            } else {
                terms.add(token.makeClone());
            }
        }
        if (changed) {
            result.add(new ParenthesisExpression(terms));
        }
        return result;
    }

    private List<ParenthesisToken> calculateEqualTokens(ParenthesisToken token) {
        List<ParenthesisToken> equalTokens = new ArrayList<>(openAllNestedExpressions(token));
        if (!equalTokens.isEmpty()) {
            token = equalTokens.get(equalTokens.size() - 1).makeClone();
        }
        while (token.getMultiplyExpressions().size() > 1) {
            ParenthesisExpression exp1 = token.getMultiplyExpressions().get(0);
            ParenthesisExpression exp2 = token.getMultiplyExpressions().get(1);
            token.getMultiplyExpressions().remove(0);
            token.getMultiplyExpressions().remove(0);
            token.getMultiplyExpressions().add(0, exp1.multiply(exp2));
            equalTokens.add(token.makeClone());
        }
        if (!token.getMultiplyExpressions().isEmpty()) {
            ParenthesisToken constantPart = token.extractConstant();
            if (!constantPart.isExpression()) {
                ParenthesisExpression exp = token.getMultiplyExpressions().get(0);
                exp = exp.multiplyToken(constantPart);
                token.getMultiplyExpressions().set(0, exp);
                equalTokens.add(token.makeClone());
            }
            equalTokens.addAll(openDivideExpressions(token));
        }

        return equalTokens;
    }

    private List<ParenthesisToken> openDivideExpressions(ParenthesisToken token) {
        if (token.getMultiplyExpressions().isEmpty()) {
            throw new IllegalArgumentException();
        }
        List<ParenthesisToken> equalTokens = new ArrayList<>();
        while (!token.getDivideExpressions().isEmpty()) {
            ParenthesisExpression divExp = token.getDivideExpressions().get(0);
            token.getDivideExpressions().remove(0);
            ParenthesisExpression multiplyExp = token.getMultiplyExpressions().get(0);
            token.getMultiplyExpressions().set(0, multiplyExp.divideEach(divExp));
            equalTokens.add(token.makeClone());
        }
        return equalTokens;
    }

    private List<ParenthesisToken> openAllNestedExpressions(ParenthesisToken token) {
        List<ParenthesisToken> result = new ArrayList<>();
        for (int i = 0; i < token.getMultiplyFunctions().size(); i++) {
            for (ParenthesisExpression exp:
                    calculateEqualForms(token.getMultiplyFunctions().get(i).getExpression())) {
                token.getMultiplyFunctions().get(i).setExpression(exp);
                result.add(token.makeClone());
            }
        }
        for (int i = 0; i < token.getDivideFunctions().size(); i++) {
            for (ParenthesisExpression exp:
                    calculateEqualForms(token.getDivideFunctions().get(i).getExpression())) {
                token.getDivideFunctions().get(i).setExpression(exp);
                result.add(token.makeClone());
            }
        }
        for (int i = 0; i < token.getMultiplyExpressions().size(); i++) {
            for (ParenthesisExpression exp:
                    calculateEqualForms(token.getMultiplyExpressions().get(i))) {
                token.getMultiplyExpressions().set(i, exp);
                result.add(token.makeClone());
            }
        }
        for (int i = 0; i < token.getDivideExpressions().size(); i++) {
            for (ParenthesisExpression exp:
                    calculateEqualForms(token.getMultiplyExpressions().get(i))) {
                token.getDivideExpressions().set(i, exp);
                result.add(token.makeClone());
            }
        }

        boolean changed = false;
        for (int i = 0; i < token.getMultiplyExpressions().size(); i++) {
            if (token.getMultiplyExpressions().get(i).getTerms().size() == 1) {
                ParenthesisToken tokenToOpen = token.getMultiplyExpressions().get(i).getTerms().get(0);
                token.addAll(tokenToOpen);
                token.getMultiplyExpressions().remove(i);
                i--;
                if (tokenToOpen.isNegative()) {
                    token.setNegative(!token.isNegative());
                }
                changed = true;
            }
        }
        if (changed) {
            result.add(token.makeClone());
        }
        changed = false;
        for (int i = 0; i < token.getDivideExpressions().size(); i++) {
            if (token.getDivideExpressions().get(i).getTerms().size() == 1) {
                ParenthesisToken tokenToOpen = token.getDivideExpressions().get(i).getTerms().get(0);

                token.addAllReverse(tokenToOpen);
                token.getDivideExpressions().remove(i);
                i--;
                if (tokenToOpen.isNegative()) {
                    token.setNegative(!token.isNegative());
                }
                changed = true;
            }
        }
        if (changed) {
            result.add(token.makeClone());
        }
        return result;
    }
}
