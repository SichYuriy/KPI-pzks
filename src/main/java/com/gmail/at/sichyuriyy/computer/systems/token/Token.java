package com.gmail.at.sichyuriyy.computer.systems.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Token {
    private final String value;
    private final TokenType type;
    private final int position;
}
