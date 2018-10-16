package com.gmail.at.sichyuriyy.computer.systems.token.reader;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import org.junit.Test;

import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.CONSTANT;
import static org.assertj.core.api.Assertions.assertThat;

public class ConstantReaderTest {

    private ConstantReader subject = new ConstantReader();

    @Test
    public void canRead() {
        assertThat(subject.canRead("a+2+3+5", 2)).isTrue();
        assertThat(subject.canRead("a+2.3+1", 1)).isFalse();
        assertThat(subject.canRead("0", 0)).isTrue();
    }

    @Test
    public void readToken_whenValidInteger_shouldReadInteger() {
        Token actual = subject.readToken("a+123+2", 2);
        assertThat(actual.getValue()).isEqualTo("123");
        assertThat(actual.getType()).isEqualTo(CONSTANT);
        assertThat(actual.getPosition()).isEqualTo(2);
    }

    @Test
    public void readToken_whenEndsWithDot_shouldReadOnlyValidInteger() {
        Token actual = subject.readToken("a+123.+2", 2);
        assertThat(actual.getValue()).isEqualTo("123");
        assertThat(actual.getType()).isEqualTo(CONSTANT);
        assertThat(actual.getPosition()).isEqualTo(2);
    }

    @Test
    public void readToken_whenValidDecimal_shouldReadDecimal() {
        Token actual = subject.readToken("a+123.02+2", 2);
        assertThat(actual.getValue()).isEqualTo("123.02");
        assertThat(actual.getType()).isEqualTo(CONSTANT);
        assertThat(actual.getPosition()).isEqualTo(2);
    }

    @Test
    public void readToken_whenZero_shouldReadZero() {
        Token actual = subject.readToken("a+0+2", 2);
        assertThat(actual.getValue()).isEqualTo("0");
        assertThat(actual.getType()).isEqualTo(CONSTANT);
        assertThat(actual.getPosition()).isEqualTo(2);
    }

    @Test
    public void readToken_whenFewZeros_shouldReadFirstZero() {
        Token actual = subject.readToken("a+00+2", 2);
        assertThat(actual.getValue()).isEqualTo("0");
        assertThat(actual.getType()).isEqualTo(CONSTANT);
        assertThat(actual.getPosition()).isEqualTo(2);
    }

    @Test
    public void readToken_whenZeroWithDot_shouldReadOnlyZero() {
        Token actual = subject.readToken("a+0.+2", 2);
        assertThat(actual.getValue()).isEqualTo("0");
        assertThat(actual.getType()).isEqualTo(CONSTANT);
        assertThat(actual.getPosition()).isEqualTo(2);
    }

    @Test
    public void readToken_whenZeroWithFractionPart_shouldReadDecimal() {
        Token actual = subject.readToken("a+0.02+2", 2);
        assertThat(actual.getValue()).isEqualTo("0.02");
        assertThat(actual.getType()).isEqualTo(CONSTANT);
        assertThat(actual.getPosition()).isEqualTo(2);
    }
}