package com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.partreader;

import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.ParenthesisToken;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;

import java.util.ArrayList;

import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.isValue;

public class ValueTokenPartReader implements TokenPartReader {
    @Override
    public boolean canRead(Token token) {
        return isValue(token);
    }

    @Override
    public int readTokenPart(ArrayList<Token> tokens, int nextTokenIndex, boolean multiply, ParenthesisToken outputToken) {
        outputToken.addValue(multiply, tokens.get(nextTokenIndex).getValue());
        return nextTokenIndex + 1;
    }
}
