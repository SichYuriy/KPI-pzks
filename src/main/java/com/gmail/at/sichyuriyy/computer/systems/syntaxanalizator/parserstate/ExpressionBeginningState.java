package com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;

public class ExpressionBeginningState extends AbstractParserState {

    public ExpressionBeginningState(StateData data) {
        super(data);
    }

    @Override
    protected ParserState readConstant(Token token) {
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readParenthesisOpen(Token token) {
        return new ExpressionBeginningState(getState());
    }

    @Override
    protected ParserState readParenthesisClose(Token token) {
        reportError(token, "Unexpected ) at the beginning of expression.");
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readPlusOperator(Token token) {
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readMinusOperator(Token token) {
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
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readSinFunction(Token token) {
        return new AfterFunctorState(getState());
    }

    @Override
    protected ParserState readCosFunction(Token token) {
        return new AfterFunctorState(getState());
    }

    @Override
    protected ParserState readEndOfExpression(Token token) {
        reportError(token, "Unexpected end of expression");
        return this;
    }
}