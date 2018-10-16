package com.gmail.at.sichyuriyy.computer.systems.token.reader;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import com.gmail.at.sichyuriyy.computer.systems.token.TokenType;

import java.util.Map;

public class ParenthesisReader implements TokenReader {
    private final Map<Character, TokenType> parenthesis = Map.of(
        '(', TokenType.PARENTHESIS_OPEN,
        ')', TokenType.PARENTHESIS_CLOSE
    );

    @Override
    public boolean canRead(String str, int startPosition) {
        return parenthesis.containsKey(str.charAt(startPosition));
    }

    @Override
    public Token readToken(String str, int startPosition) {
        TokenType type = parenthesis.get(str.charAt(startPosition));
        return new Token(str.substring(startPosition, startPosition + 1), type, startPosition);
    }
}
