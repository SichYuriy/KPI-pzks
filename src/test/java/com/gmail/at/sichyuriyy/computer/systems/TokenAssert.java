package com.gmail.at.sichyuriyy.computer.systems;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import com.gmail.at.sichyuriyy.computer.systems.token.TokenType;
import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

class TokenAssert extends AbstractAssert<TokenAssert, Token> {

    private TokenAssert(Token token) {
        super(token, TokenAssert.class);
    }

    static TokenAssert assertThat(Token token) {
        return new TokenAssert(token);
    }

    public TokenAssert typeOf(TokenType type) {
        isNotNull();
        if (!Objects.equals(actual.getType(), type)) {
            failWithMessage("Expected token type to be <%s> but was <%s>", type.toString(), actual.getType().toString());
        }
        return this;
    }

    public TokenAssert hasValue(String value) {
        isNotNull();
        if (!Objects.equals(actual.getValue(), value)) {
            failWithMessage("Expected token <%s> but was <%s>", value, actual.getValue());
        }
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public TokenAssert atPosition(int position) {
        isNotNull();
        if (!Objects.equals(actual.getPosition(), position)) {
            failWithMessage("Expected token at position <%d> but was at <%d>", position, actual.getPosition());
        }
        return this;
    }
}
