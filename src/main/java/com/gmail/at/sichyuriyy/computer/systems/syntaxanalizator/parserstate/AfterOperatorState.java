package com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;

public class AfterOperatorState extends AbstractParserState {
    public AfterOperatorState(StateData state) {
        super(state);
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
        reportError(token, "Unexpected ) after operator");
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readPlusOperator(Token token) {
        reportError(token, "Unexpected few operators in a row");
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readMinusOperator(Token token) {
        reportError(token, "Unexpected few operators in a row");
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readMultiplyOperator(Token token) {
        reportError(token, "Unexpected few operators in a row");
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readDivideOperator(Token token) {
        reportError(token, "Unexpected few operators in a row");
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
