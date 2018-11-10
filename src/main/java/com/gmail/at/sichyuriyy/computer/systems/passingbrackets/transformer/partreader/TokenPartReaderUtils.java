package com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.partreader;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;

import java.util.ArrayList;

import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.isCloseParen;
import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.isOpenParen;

public class TokenPartReaderUtils {

    public static int findExpressionEndIndex(ArrayList<Token> tokens, int expressionStartIndex) {
        int openParenCount = 0;
        do {
            Token token = tokens.get(expressionStartIndex);
            if (isOpenParen(token)) {
                openParenCount++;
            } else if (isCloseParen(token)) {
                openParenCount--;
            }
            expressionStartIndex++;
        } while (openParenCount != 0);

        return expressionStartIndex - 1;
    }
}
