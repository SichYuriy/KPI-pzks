package com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate;

import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishToken;
import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishTokenType;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxError;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import com.gmail.at.sichyuriyy.computer.systems.token.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishTokenType.OPEN_PARENTHESIS;
import static java.util.Map.entry;

@Data
@AllArgsConstructor
public abstract class AbstractParserState implements ParserState {

    private final Map<TokenType, Function<Token, ParserState>> readers = Map.ofEntries(
            entry(TokenType.CONSTANT, this::readConstant),
            entry(TokenType.PARENTHESIS_OPEN, this::readParenthesisOpenAndIncrementCount),
            entry(TokenType.PARENTHESIS_CLOSE, this::readParenthesisCloseAndDecrementCount),
            entry(TokenType.PLUS_OPERATOR, this::readPlusOperator),
            entry(TokenType.MINUS_OPERATOR, this::readMinusOperator),
            entry(TokenType.MULTIPLY_OPERATOR, this::readMultiplyOperator),
            entry(TokenType.DIVIDE_OPERATOR, this::readDivideOperator),
            entry(TokenType.VARIABLE, this::readVariable),
            entry(TokenType.SIN_FUNCTION, this::readSinFunction),
            entry(TokenType.COS_FUNCTION, this::readCosFunction),
            entry(TokenType.UNKNOWN_TOKEN, this::readUnknownToken),
            entry(TokenType.END_OF_EXPRESSION, this::readEndOfExpression)
    );

    private final StateData state;

    @Override
    public ParserState readNextToken(Token token) {
        return readers.get(token.getType()).apply(token);
    }

    protected void reportError(Token token, String error) {
        state.foundErrors.add(new SyntaxError(error, token.getPosition(), token.getValue()));
    }

    private void incrementOpenParenthesisCount() {
        state.openParenthesisCount++;
    }

    private void decrementOpenParenthesisCount(Token token) {
        if (state.openParenthesisCount == 0) {
            reportError(token, "Needs opening parenthesis before closing one.");
        } else {
            state.openParenthesisCount--;
        }
    }

    @Override
    public List<SyntaxError> getFoundErrors() {
        return state.foundErrors;
    }

    @Override
    public List<PolishToken> getPolishNotation() {
        return state.polishNotation;
    }

    private ParserState readParenthesisOpenAndIncrementCount(Token token) {
        incrementOpenParenthesisCount();
        return readParenthesisOpen(token);
    }

    private ParserState readParenthesisCloseAndDecrementCount(Token token) {
        decrementOpenParenthesisCount(token);
        return readParenthesisClose(token);
    }

    protected abstract ParserState readConstant(Token token);

    protected abstract ParserState readParenthesisOpen(Token token);

    protected abstract ParserState readParenthesisClose(Token token);

    protected abstract ParserState readPlusOperator(Token token);

    protected abstract ParserState readMinusOperator(Token token);

    protected abstract ParserState readMultiplyOperator(Token token);

    protected abstract ParserState readDivideOperator(Token token);

    protected abstract ParserState readVariable(Token token);

    protected abstract ParserState readSinFunction(Token token);

    protected abstract ParserState readCosFunction(Token token);

    private ParserState readUnknownToken(Token token) {
        reportError(token, "Unknown token");
        return new AfterUnknownTokenState(getState());
    }

    protected abstract ParserState readEndOfExpression(Token token);

    protected void addOperatorToPolishNotation(String value, PolishTokenType type) {
        if (!state.foundErrors.isEmpty()) {
            return;
        }
        while (!state.operations.isEmpty()
                && !state.operations.peek().getType().equals(OPEN_PARENTHESIS)
                && state.operations.peek().getType().getPriority() >= type.getPriority()) {
            state.polishNotation.add(state.operations.pop());
        }
        state.operations.add(new PolishToken(value, type));
    }

    protected void pushUntilOpenParenthesis() {
        if (!state.foundErrors.isEmpty()) {
            return;
        }
        boolean parenthesisDeleted = false;
        while (!parenthesisDeleted) {
            PolishToken token = state.operations.pop();
            if (token.getType().equals(OPEN_PARENTHESIS)) {
                parenthesisDeleted = true;
            } else {
                state.polishNotation.add(token);
            }
        }
    }
}
