package com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate;

import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishToken;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxError;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;

import java.util.List;

public interface ParserState {
    ParserState readNextToken(Token token);

    List<SyntaxError> getFoundErrors();
    List<PolishToken> getPolishNotation();
}
