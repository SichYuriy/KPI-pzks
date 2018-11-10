package com.gmail.at.sichyuriyy.computer.systems.token;

public enum TokenType {
    CONSTANT,
    PARENTHESIS_OPEN,
    PARENTHESIS_CLOSE,
    PLUS_OPERATOR,
    MINUS_OPERATOR,
    MULTIPLY_OPERATOR,
    DIVIDE_OPERATOR,
    VARIABLE,
    SIN_FUNCTION,
    COS_FUNCTION,
    UNKNOWN_TOKEN,
    END_OF_EXPRESSION;

    public static boolean isPlusOrMinus(Token token) {
        return token.getType().equals(PLUS_OPERATOR)
                || token.getType().equals(MINUS_OPERATOR);
    }

    public static boolean isValue(Token token) {
        return token.getType().equals(CONSTANT)
                || token.getType().equals(VARIABLE);
    }

    public static boolean isOpenParen(Token token) {
        return token.getType().equals(PARENTHESIS_OPEN);
    }

    public static boolean isCloseParen(Token token) {
        return token.getType().equals(PARENTHESIS_CLOSE);
    }

    public static boolean isMultiplyOrDivide(Token token) {
        return token.getType().equals(MULTIPLY_OPERATOR)
                || token.getType().equals(DIVIDE_OPERATOR);
    }

    public static boolean isDivide(Token token) {
        return token.getType().equals(DIVIDE_OPERATOR);
    }

    public static boolean isMinus(Token token) {
        return token.getType().equals(MINUS_OPERATOR);
    }

    public static boolean isFunction(Token token) {
        return token.getType().equals(SIN_FUNCTION)
                || token.getType().equals(COS_FUNCTION);
    }
}
