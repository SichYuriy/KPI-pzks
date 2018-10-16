package com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator;

import lombok.Data;

@Data
public class SyntaxError {
    private final String errorDescription;
    private final int position;
    private final String tokenValue;
}
