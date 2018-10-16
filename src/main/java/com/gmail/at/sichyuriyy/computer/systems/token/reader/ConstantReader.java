package com.gmail.at.sichyuriyy.computer.systems.token.reader;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;

import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.CONSTANT;
import static java.lang.Character.isDigit;

public class ConstantReader implements TokenReader {

    @Override
    public boolean canRead(String str, int startPosition) {
        return isDigit(str.charAt(startPosition));
    }

    @Override
    public Token readToken(String str, int startPosition) {
        char firstChar = str.charAt(startPosition);
        if (firstChar != '0') {
            int integerEndIndex = startPosition;
            while (integerEndIndex != str.length() && isDigit(str.charAt(integerEndIndex))) {
                integerEndIndex++;
            }
            return readFractionPart(str, startPosition, integerEndIndex);
        } else {
            return readFractionPart(str, startPosition, startPosition + 1);
        }
    }

    private Token readFractionPart(String str, int startPosition, int integerEndIndex) {
        if (integerEndIndex != str.length() && isDot(str.charAt(integerEndIndex))) {
            int fractionEndIndex = integerEndIndex + 1;
            if (fractionEndIndex != str.length() && isDigit(str.charAt(fractionEndIndex))) {
                while (fractionEndIndex != str.length() && isDigit(str.charAt(fractionEndIndex))) {
                    fractionEndIndex++;
                }
                return new Token(str.substring(startPosition, fractionEndIndex), CONSTANT, startPosition);
            }
        }
        return new Token(str.substring(startPosition, integerEndIndex), CONSTANT, startPosition);
    }

    private boolean isDot(char c) {
        return c == '.';
    }
}
