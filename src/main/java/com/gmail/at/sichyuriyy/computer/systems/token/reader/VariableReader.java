package com.gmail.at.sichyuriyy.computer.systems.token.reader;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import com.gmail.at.sichyuriyy.computer.systems.token.TokenType;

import java.util.Set;

public class VariableReader implements TokenReader {
    private final Set<String> keyWords = Set.of("sin", "cos");

    @Override
    public boolean canRead(String str, int startPosition) {
        var firstChar = str.charAt(startPosition);
        if (!Character.isAlphabetic(firstChar)) {
            return false;
        }
        var variableName = TokenReaderUtil.readLiteral(str, startPosition);
        return !keyWords.contains(variableName);
    }

    @Override
    public Token readToken(String str, int startPosition) {
        var variableName = TokenReaderUtil.readLiteral(str, startPosition);
        return new Token(variableName, TokenType.VARIABLE, startPosition);
    }
}
