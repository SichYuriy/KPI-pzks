package com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate;

import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishTokenType;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;

public class AfterValueState extends AbstractParserState {

    public AfterValueState(StateData state) {
        super(state);
    }

    @Override
    protected ParserState readConstant(Token token) {
        reportError(token, "Unexpected constant, expected operator or ) instead");
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readParenthesisOpen(Token token) {
        reportError(token, "Unexpected (, expected operator or ) instead");
        return new ExpressionBeginningState(getState());
    }

    @Override
    protected ParserState readParenthesisClose(Token token) {
        pushUntilOpenParenthesis();
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readPlusOperator(Token token) {
        addOperatorToPolishNotation(token.getValue(), PolishTokenType.BINARY_PLUS);
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readMinusOperator(Token token) {
        addOperatorToPolishNotation(token.getValue(), PolishTokenType.BINARY_MINUS);
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readMultiplyOperator(Token token) {
        addOperatorToPolishNotation(token.getValue(), PolishTokenType.MULTIPLY);
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readDivideOperator(Token token) {
        addOperatorToPolishNotation(token.getValue(), PolishTokenType.DIVIDE);
        return new AfterOperatorState(getState());
    }

    @Override
    protected ParserState readVariable(Token token) {
        reportError(token, "Unexpected var");
        return new AfterValueState(getState());
    }

    @Override
    protected ParserState readSinFunction(Token token) {
        reportError(token, "Unexpected function");
        return new AfterFunctorState(getState());
    }

    @Override
    protected ParserState readCosFunction(Token token) {
        reportError(token, "Unexpected function");
        return new AfterFunctorState(getState());
    }

    @Override
    protected ParserState readEndOfExpression(Token token) {
        if (getState().openParenthesisCount > 0) {
            reportError(token, "Expected ) before end of expression");
        }
        while (!getState().operations.empty()) {
            getState().polishNotation.add(getState().operations.pop());
        }
        return this;
    }
}
