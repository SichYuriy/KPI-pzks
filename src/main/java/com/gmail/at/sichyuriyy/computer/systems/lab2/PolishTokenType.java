package com.gmail.at.sichyuriyy.computer.systems.lab2;

public enum PolishTokenType {
    VALUE(false, 0),
    OPEN_PARENTHESIS(false, 0),
    CLOSE_PARENTHESIS(false, 0),
    UNARY_MINUS(true, 20),
    UNARY_PLUS(true, 20),
    BINARY_MINUS(true, 10),
    BINARY_PLUS(true, 10),
    DIVIDE(true, 30),
    MULTIPLY(true, 30),
    SIN(true, 40),
    COS(true, 40);

    public static final int FUNCTION_PRIORITY = 40;

    private final boolean isOperation;
    private final int priority;

    PolishTokenType(boolean isOperation, int priority) {
        this.isOperation = isOperation;
        this.priority = priority;
    }

    public boolean isOperation() {
        return isOperation;
    }

    public int getPriority() {
        return priority;
    }
}
