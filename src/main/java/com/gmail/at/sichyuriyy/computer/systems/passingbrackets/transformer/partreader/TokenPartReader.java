package com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.partreader;

import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.ParenthesisToken;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;

import java.util.ArrayList;

public interface TokenPartReader {
    boolean canRead(Token token);
    int readTokenPart(ArrayList<Token> tokens, int nextTokenIndex, boolean multiply, ParenthesisToken outputToken);
}
