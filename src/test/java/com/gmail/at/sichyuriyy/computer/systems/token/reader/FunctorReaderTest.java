package com.gmail.at.sichyuriyy.computer.systems.token.reader;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctorReaderTest {

    private FunctorReader subject = new FunctorReader();

    @Test
    public void canRead() {
        assertThat(subject.canRead("1+sin", 2)).isTrue();
        assertThat(subject.canRead("1+cos", 2)).isTrue();
        assertThat(subject.canRead("1+coss", 2)).isFalse();
        assertThat(subject.canRead("1+sin1", 2)).isFalse();
        assertThat(subject.canRead("1+sin.", 2)).isTrue();
    }
}