package com.gmail.at.sichyuriyy.computer.systems;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Expression {
    private List<Token> tokens;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        tokens.stream()
                .map(Token::getValue)
                .forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
