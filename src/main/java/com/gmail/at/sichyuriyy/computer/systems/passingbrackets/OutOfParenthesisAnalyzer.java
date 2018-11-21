package com.gmail.at.sichyuriyy.computer.systems.passingbrackets;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.ParenthesisExpressionTransformer;

import java.util.*;

public class OutOfParenthesisAnalyzer {

    private ParenthesisExpressionTransformer transformer = new ParenthesisExpressionTransformer();

    private Map<ParenthesisExpression, Set<ParenthesisExpression>> equalForms = new HashMap<>();

    public Set<ParenthesisExpression> getAllForms(Expression expression) {
        ParenthesisExpression parenthesisExpression = transformer.transform(expression);
        return getAllForms(parenthesisExpression);
    }

    private Set<ParenthesisExpression> getAllForms(ParenthesisExpression parenthesisExpression) {
        Set<ParenthesisExpression> result = this.equalForms.get(parenthesisExpression);
        if (result == null) {
            result = this.calculateEqualForms(parenthesisExpression);
        }
        equalForms.put(parenthesisExpression, result);
        return result;
    }

    private Set<ParenthesisExpression> calculateEqualForms(ParenthesisExpression expression) {
        Set<ParenthesisExpression> firstLevelResult = getEqualFormsByTakingTokensOut(expression);
        Set<ParenthesisExpression> result = new LinkedHashSet<>();
        firstLevelResult.forEach(
                exp -> {
                    result.add(exp);
                    result.addAll(getAllForms(exp));
                }
        );

        for (int i = 0; i < expression.getTerms().size(); i++) {
            ParenthesisToken original = expression.getTerms().get(i);
            Set<ParenthesisToken> equalTokens = getAllEqualTokens(original);
            for (ParenthesisToken equalToken : equalTokens) {
                expression.getTerms().set(i, equalToken);
                result.addAll(getAllForms(expression));
            }
            expression.getTerms().set(i, original);
        }

        return result;
    }

    private Set<ParenthesisToken> getAllEqualTokens(ParenthesisToken token) {
        Set<ParenthesisToken> result = new LinkedHashSet<>();
        for (int i = 0; i < token.getMultiplyExpressions().size(); i++) {
            ParenthesisExpression exp = token.getMultiplyExpressions().get(i);
            for (ParenthesisExpression equalExpression : getAllForms(exp)) {
                ParenthesisToken clone = token.makeClone();
                clone.getMultiplyExpressions().remove(i);
                if (equalExpression.getTerms().size() == 1) {
                    clone.addAll(equalExpression.getTerms().get(0));
                } else {
                    clone.getMultiplyExpressions().add(equalExpression);
                }
                result.add(clone);
            }
        }
        for (int i = 0; i < token.getDivideFunctions().size(); i++) {
            ParenthesisExpression exp = token.getDivideExpressions().get(i);
            for (ParenthesisExpression equalExpression : getAllForms(exp)) {
                ParenthesisToken clone = token.makeClone();
                clone.getDivideExpressions().remove(i);
                clone.getDivideExpressions().add(equalExpression);
                result.add(clone);
            }
        }
        for (int i = 0; i < token.getMultiplyFunctions().size(); i++) {
            FunctionExpression func = token.getMultiplyFunctions().get(i);
            for (ParenthesisExpression equalExpression : getAllForms(func.getExpression())) {
                ParenthesisToken clone = token.makeClone();
                clone.getMultiplyFunctions().get(i).setExpression(equalExpression);
                result.add(clone);
            }
        }
        for (int i = 0; i < token.getDivideFunctions().size(); i++) {
            FunctionExpression func = token.getDivideFunctions().get(i);
            for (ParenthesisExpression equalExpression : getAllForms(func.getExpression())) {
                ParenthesisToken clone = token.makeClone();
                clone.getDivideFunctions().get(i).setExpression(equalExpression);
                result.add(clone);
            }
        }
        return result;
    }

    private Set<ParenthesisExpression> getEqualFormsByTakingTokensOut(ParenthesisExpression expression) {
        Set<ParenthesisExpression> result = new LinkedHashSet<>();

        for (int i = 0; i < expression.getTerms().size(); i++) {
            for (int j = i + 1; j < expression.getTerms().size(); j++) {
                ParenthesisToken firstToken = expression.getTerms().get(i);
                ParenthesisToken secondToken = expression.getTerms().get(j);

                ParenthesisToken commonPart = firstToken.getCommon(secondToken);
                if (!commonPart.isEmpty()) {
                    ParenthesisExpression clone = expression.makeClone();
                    clone.getTerms().remove(firstToken);
                    clone.getTerms().remove(secondToken);

                    ParenthesisToken firstParenToken = firstToken.extract(commonPart);
                    ParenthesisToken secondParenToken = secondToken.extract(commonPart);

                    ParenthesisExpression parenExp = merge(firstParenToken, secondParenToken);
                    commonPart.addExpression(true, parenExp);
                    clone.getTerms().add(commonPart);
                    result.add(clone);
                }
            }
        }
        return result;
    }

    private ParenthesisExpression merge(ParenthesisToken token1, ParenthesisToken token2) {
        ParenthesisExpression result = new ParenthesisExpression();
        result.setTerms(new ArrayList<>());
        add(result, token1);
        add(result, token2);
        return result;
    }

    private void add(ParenthesisExpression exp, ParenthesisToken token) {
        if (token.isExpression()) {
            exp.getTerms().addAll(token.getMultiplyExpressions().get(0).getTerms());
        } else {
            exp.getTerms().add(token);
        }
    }
}
