package com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate;

import com.gmail.at.sichyuriyy.computer.systems.lab2.PolishToken;
import com.gmail.at.sichyuriyy.computer.systems.lab2.PolishTokenType;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;

import static com.gmail.at.sichyuriyy.computer.systems.lab2.PolishTokenType.*;

public class ExpressionBeginningState extends AbstractParserState {

    public ExpressionBeginningState(StateData data) {
        super(data);
    }

    @Override
    protected ParserState readConstant(Token token) {
        getState().polishOutput.add(new PolishToken(token.getValue(), VALUE));
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readParenthesisOpen(Token token) {
        getState().operations.add(new PolishToken(token.getValue(), OPEN_PARENTHESIS));
        return new ExpressionBeginningState(getState());
    }

    @Override
    protected ParserState readParenthesisClose(Token token) {
        reportError(token, "Unexpected ) at the beginning of expression.");
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readPlusOperator(Token token) {
        getState().operations.add(new PolishToken(token.getValue(), UNARY_PLUS));
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readMinusOperator(Token token) {
        getState().operations.add(new PolishToken(token.getValue(), UNARY_MINUS));
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readMultiplyOperator(Token token) {
        reportError(token, "Unexpected * operator at the beginning of expression");
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readDivideOperator(Token token) {
        reportError(token, "Unexpected / operator at the beginning of expression");
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readVariable(Token token) {
        getState().polishOutput.add(new PolishToken(token.getValue(), VALUE));
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readSinFunction(Token token) {
        addOperatorToPolishNotation(token.getValue(), COS);
        return new AfterFunctorState(getState());
    }

    @Override
    protected ParserState readCosFunction(Token token) {
        addOperatorToPolishNotation(token.getValue(), COS);
        return new AfterFunctorState(getState());
    }

    @Override
    protected ParserState readEndOfExpression(Token token) {
        reportError(token, "Unexpected end of expression");
        return this;
    }
}