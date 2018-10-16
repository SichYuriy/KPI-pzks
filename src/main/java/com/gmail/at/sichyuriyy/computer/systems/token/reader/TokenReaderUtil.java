package com.gmail.at.sichyuriyy.computer.systems.token.reader;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;

class TokenReaderUtil {
    static String readLiteral(String str, int startPosition) {
        if (!isAlphabetic(str.charAt(startPosition))) {
            throw new IllegalArgumentException(str + "at " + startPosition + " does not contain literal");
        }
        int endIndex = startPosition + 1;
        while (endIndex != str.length()
                && (isAlphabetic(str.charAt(endIndex)) || isDigit(str.charAt(endIndex)))) {
            endIndex++;
        }
        return str.substring(startPosition, endIndex);
    }
}
