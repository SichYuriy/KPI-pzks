package com.gmail.at.sichyuriyy.computer.systems;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import org.junit.Test;

import java.util.List;

import static com.gmail.at.sichyuriyy.computer.systems.TokenAssert.assertThat;
import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ExpressionReaderTest {

    private ExpressionReader subject = new ExpressionReader();

    @Test
    public void readExpression() {
        Expression actual = subject.readExpression("(1 + 2) + 3 / sin(pi/2)...");
        List<Token> tokens = actual.getTokens();
        assertThat(actual.toString()).isEqualTo("(1+2)+3/sin(pi/2)...");
        assertThat(tokens).hasSize(16);
        assertThat(tokens.get(0)).typeOf(PARENTHESIS_OPEN).hasValue("(").atPosition(0);
        assertThat(tokens.get(1)).typeOf(CONSTANT).hasValue("1").atPosition(1);
        assertThat(tokens.get(2)).typeOf(PLUS_OPERATOR).hasValue("+").atPosition(2);
        assertThat(tokens.get(3)).typeOf(CONSTANT).hasValue("2").atPosition(3);
        assertThat(tokens.get(4)).typeOf(PARENTHESIS_CLOSE).hasValue(")").atPosition(4);
        assertThat(tokens.get(5)).typeOf(PLUS_OPERATOR).hasValue("+").atPosition(5);
        assertThat(tokens.get(6)).typeOf(CONSTANT).hasValue("3").atPosition(6);
        assertThat(tokens.get(7)).typeOf(DIVIDE_OPERATOR).hasValue("/").atPosition(7);
        assertThat(tokens.get(8)).typeOf(SIN_FUNCTION).hasValue("sin").atPosition(8);
        assertThat(tokens.get(9)).typeOf(PARENTHESIS_OPEN).hasValue("(").atPosition(11);
        assertThat(tokens.get(10)).typeOf(VARIABLE).hasValue("pi").atPosition(12);
        assertThat(tokens.get(11)).typeOf(DIVIDE_OPERATOR).hasValue("/").atPosition(14);
        assertThat(tokens.get(12)).typeOf(CONSTANT).hasValue("2").atPosition(15);
        assertThat(tokens.get(13)).typeOf(PARENTHESIS_CLOSE).hasValue(")").atPosition(16);
        assertThat(tokens.get(14)).typeOf(UNKNOWN_TOKEN).hasValue("...").atPosition(17);
        assertThat(tokens.get(15)).typeOf(END_OF_EXPRESSION);
    }
}
