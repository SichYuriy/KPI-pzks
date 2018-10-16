package com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate;

import com.gmail.at.sichyuriyy.computer.systems.lab2.PolishToken;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;

import static com.gmail.at.sichyuriyy.computer.systems.lab2.PolishTokenType.OPEN_PARENTHESIS;

public class AfterFunctorState extends AbstractParserState {
    public AfterFunctorState(StateData state) {
        super(state);
    }

    @Override
    protected ParserState readConstant(Token token) {
        reportError(token, "Expected ( after functor");
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readParenthesisOpen(Token token) {
        getState().operations.add(new PolishToken(token.getValue(), OPEN_PARENTHESIS));
        return new ExpressionBeginningState(getState());
    }

    @Override
    protected ParserState readParenthesisClose(Token token) {
        reportError(token, "Expected ( after functor");
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readPlusOperator(Token token) {
        reportError(token, "Expected ( after functor");
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readMinusOperator(Token token) {
        reportError(token, "Expected ( after functor");
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readMultiplyOperator(Token token) {
        reportError(token, "Expected ( after functor");
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readDivideOperator(Token token) {
        reportError(token, "Expected ( after functor");
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readVariable(Token token) {
        reportError(token, "Expected ( after functor");
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readSinFunction(Token token) {
        reportError(token, "Expected ( after functor");
        return new AfterFunctorState(getState());
    }

    @Override
    protected ParserState readCosFunction(Token token) {
        reportError(token, "Expected ( after functor");
        return new AfterFunctorState(getState());    }

    @Override
    protected ParserState readEndOfExpression(Token token) {
        reportError(token, "Unexpected end of expression");
        return this;
    }
}
