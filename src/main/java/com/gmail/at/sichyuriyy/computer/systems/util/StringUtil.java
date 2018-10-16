package com.gmail.at.sichyuriyy.computer.systems.util;

public class StringUtil {
    public static String deleteWhiteSpaces(String str) {
        StringBuilder result = new StringBuilder();
        for (char c: str.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }
}
