package com.gmail.at.sichyuriyy.computer.systems.token.reader;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import com.gmail.at.sichyuriyy.computer.systems.token.TokenType;

import java.util.Map;

import static com.gmail.at.sichyuriyy.computer.systems.token.reader.TokenReaderUtil.readLiteral;
import static java.lang.Character.isAlphabetic;

public class FunctorReader implements TokenReader {

    private final Map<String, TokenType> functions = Map.of(
            "sin", TokenType.SIN_FUNCTION,
            "cos", TokenType.COS_FUNCTION
    );

    @Override
    public boolean canRead(String str, int startPosition) {
        char firstChar = str.charAt(startPosition);
        if (!isAlphabetic(firstChar)) {
            return false;
        }
        String functionName = readFunctionName(str, startPosition);
        return functions.containsKey(functionName);
    }

    @Override
    public Token readToken(String str, int startPosition) {
        String functionName = readFunctionName(str, startPosition);
        TokenType type = functions.get(functionName);
        return new Token(functionName, type, startPosition);
    }

    private String readFunctionName(String str, int startPosition) {
       return readLiteral(str, startPosition);
    }
}
