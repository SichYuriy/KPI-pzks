package com.gmail.at.sichyuriyy.computer.systems.passingbrackets;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import com.gmail.at.sichyuriyy.computer.systems.token.TokenType;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.*;

public class ParenthesisExpressionTransformer {

    public ParenthesisExpression transform(Expression expression) {
        ArrayList<Token> tokens = new ArrayList<>(expression.getTokens().subList(0, expression.getTokens().size() - 1));
        return transform(tokens);
    }

    private ParenthesisExpression transform(ArrayList<Token> tokens) {
        int nextTokenIndex = 0;
        List<ParenthesisToken> output = new ArrayList<>();
        while (nextTokenIndex != tokens.size()) {
            //readNextParenthesisToken

            ParenthesisToken outputToken = new ParenthesisToken();
            if (tokens.get(nextTokenIndex).getType().equals(TokenType.PLUS_OPERATOR)) {
                nextTokenIndex++;
            } else if (tokens.get(nextTokenIndex).getType().equals(TokenType.MINUS_OPERATOR)) {
                nextTokenIndex++;
                outputToken.setNegative(true);
            }

            boolean first = true;
            while (nextTokenIndex != tokens.size() && !isPlusOrMinus(tokens.get(nextTokenIndex))) {
                boolean multiply = first
                        || tokens.get(nextTokenIndex).getType().equals(TokenType.MULTIPLY_OPERATOR);
                if (!first) {
                    nextTokenIndex++;
                }
                first = false;

                Token nextToken = tokens.get(nextTokenIndex);
                if (isValue(nextToken)) {
                    outputToken.addValue(multiply, nextToken.getValue());
                    nextTokenIndex++;
                } else if (isOpenParen(nextToken)) {
                    int closingParen = findClosingParen(tokens, nextTokenIndex);
                    ParenthesisExpression exp = transform(new ArrayList<>(tokens.subList(nextTokenIndex + 1, closingParen)));
                    outputToken.addExpression(multiply, exp);
                    nextTokenIndex = closingParen + 1;
                } else {
                    int closingParen = findClosingParen(tokens, nextTokenIndex + 1);
                    ParenthesisExpression exp = transform(new ArrayList<>(tokens.subList(nextTokenIndex + 2, closingParen)));
                    outputToken.addFunction(multiply, tokens.get(nextTokenIndex).getValue(), exp);
                    nextTokenIndex = closingParen + 1;
                }
            }
            output.add(outputToken);
        }
        return new ParenthesisExpression(output);
    }

    private int findClosingParen(ArrayList<Token> tokens, int openParenIndex) {
        int openParenCount = 0;
        do {
            Token token = tokens.get(openParenIndex);
            if (isOpenParen(token)) {
                openParenCount++;
            } else if (isCloseParen(token)) {
                openParenCount--;
            }
            openParenIndex++;
        } while (openParenCount != 0);

        return openParenIndex - 1;
    }
}
