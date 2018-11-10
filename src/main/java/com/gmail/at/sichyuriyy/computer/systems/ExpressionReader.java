package com.gmail.at.sichyuriyy.computer.systems;


import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import com.gmail.at.sichyuriyy.computer.systems.token.reader.*;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.END_OF_EXPRESSION;
import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.UNKNOWN_TOKEN;
import static com.gmail.at.sichyuriyy.computer.systems.util.StringUtils.deleteWhiteSpaces;

public class ExpressionReader {

    private final List<TokenReader> tokenReaders;

    public ExpressionReader() {
        tokenReaders = new ArrayList<>();
        tokenReaders.add(new ConstantReader());
        tokenReaders.add(new FunctorReader());
        tokenReaders.add(new OperatorReader());
        tokenReaders.add(new ParenthesisReader());
        tokenReaders.add(new VariableReader());
    }

    public Expression readExpression(String str) {
        int currentPosition = 0;
        List<Token> tokens = new ArrayList<>();
        str = deleteWhiteSpaces(str);
        while (currentPosition != str.length()) {
            Token token = readToken(str, currentPosition);
            currentPosition += token.getValue().length();
            tokens.add(token);
        }
        tokens.add(new Token("", END_OF_EXPRESSION, str.length()));
        return new Expression(squashUnknownTokens(tokens));
    }

    private Token readToken(String str, int startPosition) {
        return tokenReaders.stream()
                .filter(reader -> reader.canRead(str, startPosition))
                .findFirst()
                .map(reader -> reader.readToken(str, startPosition))
                .orElse(new Token(str.substring(startPosition, startPosition + 1), UNKNOWN_TOKEN, startPosition));
    }

    private List<Token> squashUnknownTokens(List<Token> tokens) {
        List<Token> result = new ArrayList<>();
        for (Token token: tokens) {
            if (result.size() > 0) {
                Token last = result.get(result.size() - 1);
                if (last.getType().equals(UNKNOWN_TOKEN)
                        && token.getType().equals(UNKNOWN_TOKEN)) {
                    String value = token.getValue() + last.getValue();
                    int position = last.getPosition();
                    result.remove(result.size() - 1);
                    Token combined = new Token(value, UNKNOWN_TOKEN, position);
                    result.add(combined);
                    continue;
                }
            }
            result.add(token);
        }
        return result;
    }
}
