package com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.ParenthesisExpression;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.ParenthesisToken;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.partreader.ExpressionTokenPartReader;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.partreader.FunctionTokenPartReader;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.partreader.TokenPartReader;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.partreader.ValueTokenPartReader;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.*;

public class ParenthesisExpressionTransformer {

    private List<TokenPartReader> tokenPartReaders = List.of(
            new ValueTokenPartReader(),
            new ExpressionTokenPartReader(this),
            new FunctionTokenPartReader(this)
    );

    public ParenthesisExpression transform(Expression expression) {
        ArrayList<Token> tokens = new ArrayList<>(expression.getTokens().subList(0, expression.getTokens().size() - 1));
        return transform(tokens);
    }

    public ParenthesisExpression transform(ArrayList<Token> tokens) {
        int nextTokenIndex = 0;
        List<ParenthesisToken> output = new ArrayList<>();
        while (nextTokenIndex != tokens.size()) {
            NextToken nextToken = readNextToken(tokens, nextTokenIndex);
            nextTokenIndex = nextToken.nextTokenIndex;
            output.add(nextToken.token);
        }
        return new ParenthesisExpression(output);
    }

    private NextToken readNextToken(ArrayList<Token> tokens, int nextTokenIndex) {
        ParenthesisToken outputToken = new ParenthesisToken();
        if (isMinus(tokens.get(nextTokenIndex))) outputToken.setNegative(true);
        if (isPlusOrMinus(tokens.get(nextTokenIndex))) nextTokenIndex++;

        while (nextTokenIndex != tokens.size() && !isPlusOrMinus(tokens.get(nextTokenIndex))) {
            nextTokenIndex = readNextTokenPart(tokens, nextTokenIndex, outputToken);
        }
        return new NextToken(outputToken, nextTokenIndex);
    }

    private int readNextTokenPart(ArrayList<Token> tokens, int nextTokenIndex, ParenthesisToken outputToken) {
        Token nextToken = tokens.get(nextTokenIndex);
        boolean multiply = !isDivide(nextToken);
        if (isMultiplyOrDivide(nextToken)) nextTokenIndex++;

        Token tokenPart = tokens.get(nextTokenIndex);
        return tokenPartReaders.stream()
                .filter(reader -> reader.canRead(tokenPart))
                .findFirst().orElseThrow()
                .readTokenPart(tokens, nextTokenIndex, multiply, outputToken);
    }

    @AllArgsConstructor
    private static class NextToken {
        private ParenthesisToken token;
        private int nextTokenIndex;
    }
}
