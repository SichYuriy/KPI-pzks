package com.gmail.at.sichyuriyy.computer.systems.polishnotation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@Getter
public enum PolishTokenType {
    VALUE(false, 0),
    OPEN_PARENTHESIS(false, 0),
    CLOSE_PARENTHESIS(false, 0),
    BINARY_MINUS(true, 10, true),
    BINARY_PLUS(true, 10, true),
    DIVIDE(true, 30, true),
    MULTIPLY(true, 30, true),
    SIN(true, 40, false),
    COS(true, 40, false);

    private final boolean isOperation;
    private final int priority;
    private final boolean isBinary;

    PolishTokenType(boolean isOperation, int priority) {
        this(isOperation, priority, false);
    }
}

