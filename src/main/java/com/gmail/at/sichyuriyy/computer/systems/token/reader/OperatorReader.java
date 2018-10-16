package com.gmail.at.sichyuriyy.computer.systems.token.reader;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import com.gmail.at.sichyuriyy.computer.systems.token.TokenType;

import java.util.Map;

public class OperatorReader implements TokenReader {
    private final Map<Character, TokenType> operators = Map.of(
            '+', TokenType.PLUS_OPERATOR,
            '-', TokenType.MINUS_OPERATOR,
            '*', TokenType.MULTIPLY_OPERATOR,
            '/', TokenType.DIVIDE_OPERATOR
    );

    @Override
    public boolean canRead(String str, int startPosition) {
        return operators.containsKey(str.charAt(startPosition));
    }

    @Override
    public Token readToken(String str, int startPosition) {
        TokenType type = operators.get(str.charAt(startPosition));
        return new Token(str.substring(startPosition, startPosition + 1), type, startPosition);
    }
}
