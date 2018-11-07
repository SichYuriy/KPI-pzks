package com.gmail.at.sichyuriyy.computer.systems.passingbrackets;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.ExpressionReader;
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
        ParenthesisExpression expected = new ParenthesisExpression();

        ParenthesisExpression sinExp = new ParenthesisExpression();
        sinExp.setTerms(List.of(
                ParenthesisToken.builder()
                        .multiplyVars(List.of("2"))
                        .build(),
                ParenthesisToken.builder()
                        .multiplyVars(List.of("2"))
                        .build()));
        FunctionExpression functionExpression = new FunctionExpression("sin", sinExp);

        ParenthesisExpression aMinusB = new ParenthesisExpression();
        aMinusB.setTerms(List.of(
                ParenthesisToken.builder().multiplyVars(List.of("a")).build(),
                ParenthesisToken.builder().multiplyVars(List.of("b")).negative(true).build()
        ));

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
                        .multiplyExpressions(List.of(aMinusB))
                        .build()
        ));

        assertThat(actual).isEqualTo(expected);
    }

}