package com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate.ExpressionBeginningState;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate.ParserState;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate.StateData;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;

import java.util.List;

public class SyntaxParser {

    public List<SyntaxError> findErrors(Expression expression) {
        return parseExpression(expression).getFoundErrors();
    }

    public ParserState parseExpression(Expression expression) {
        ParserState currentState = new ExpressionBeginningState(new StateData());
        for (Token token: expression.getTokens()) {
            currentState = currentState.readNextToken(token);
        }
        return currentState;
    }
}
