package com.gmail.at.sichyuriyy.computer.systems.polishnotation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PolishToken {
    private String value;
    private PolishTokenType type;
}
