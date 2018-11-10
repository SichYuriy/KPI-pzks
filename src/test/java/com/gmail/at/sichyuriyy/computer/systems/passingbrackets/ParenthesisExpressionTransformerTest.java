package com.gmail.at.sichyuriyy.computer.systems.passingbrackets;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.ExpressionReader;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.ParenthesisExpressionTransformer;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParenthesisExpressionTransformerTest {

    private ExpressionReader expressionReader = new ExpressionReader();
    private ParenthesisExpressionTransformer subject = new ParenthesisExpressionTransformer();

    @Test
    public void test1() {
        Expression exp = expressionReader.readExpression("2/3+2*sin(2+2)+3*(a-b)");
        ParenthesisExpression actual = subject.transform(exp);

        ParenthesisExpression twoPlusToExp = new ParenthesisExpression();
        twoPlusToExp.setTerms(List.of(
                ParenthesisToken.builder()
                        .multiplyVars(List.of("2"))
                        .build(),
                ParenthesisToken.builder()
                        .multiplyVars(List.of("2"))
                        .build()));
        FunctionExpression functionExpression = new FunctionExpression("sin", twoPlusToExp);

        ParenthesisExpression aMinusBExp = new ParenthesisExpression();
        aMinusBExp.setTerms(List.of(
                ParenthesisToken.builder().multiplyVars(List.of("a")).build(),
                ParenthesisToken.builder().multiplyVars(List.of("b")).negative(true).build()
        ));

        ParenthesisExpression expected = new ParenthesisExpression();
        expected.setTerms(List.of(
                ParenthesisToken.builder()
                        .multiplyVars(List.of("2"))
                        .divideVars(List.of("3"))
                        .build(),
                ParenthesisToken.builder()
                        .multiplyVars(List.of("2"))
                        .multiplyFunctions(List.of(functionExpression))
                        .build(),
                ParenthesisToken.builder()
                        .multiplyVars(List.of("3"))
                        .multiplyExpressions(List.of(aMinusBExp))
                        .build()
        ));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void test2() {
        Expression exp = expressionReader.readExpression("-(a+b)*(a-b)+2-c");
        ParenthesisExpression actual = subject.transform(exp);

        ParenthesisExpression aPlusBExp = new ParenthesisExpression();
        aPlusBExp.setTerms(List.of(
                ParenthesisToken.builder()
                        .multiplyVars(List.of("a"))
                        .build(),
                ParenthesisToken.builder()
                        .multiplyVars(List.of("b"))
                        .build()
        ));

        ParenthesisExpression aMinusBExp = new ParenthesisExpression();
        aMinusBExp.setTerms(List.of(
                ParenthesisToken.builder()
                        .multiplyVars(List.of("a"))
                        .build(),
                ParenthesisToken.builder()
                        .negative(true)
                        .multiplyVars(List.of("b"))
                        .build()
        ));

        ParenthesisExpression expected = new ParenthesisExpression();
        expected.setTerms(List.of(
                ParenthesisToken.builder()
                        .negative(true)
                        .multiplyExpressions(List.of(aPlusBExp, aMinusBExp))
                        .build(),
                ParenthesisToken.builder()
                        .multiplyVars(List.of("2"))
                        .build(),
                ParenthesisToken.builder()
                        .negative(true)
                        .multiplyVars(List.of("c"))
                        .build()
        ));

        assertThat(actual).isEqualTo(expected);
    }
}