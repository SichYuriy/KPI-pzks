package com.gmail.at.sichyuriyy.computer.systems;

import com.gmail.at.sichyuriyy.computer.systems.application.controller.ExpressionAnalyzerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class PassingBracketsTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(x+y)-3*7-(x+y)+4+7", List.of(
                        "-3*7+4+7+(x+y)*(1-1)",
                        "4+(x+y)*(1-1)+7*(-3+1)",
                        "(x+y)-(x+y)+4+7*(-3+1)"
                )},
                {"(x+y)-(x+y)", List.of(
                        "(x+y)*(1-1)"
                )},
                {"7*m-7*n", List.of(
                        "7*(m-n)"
                )},
                {"2*b+b", List.of(
                        "b*(2+1)"
                )},
                {"7*x*y+7*m*x", List.of(
                        "7*x*(y+m)"
                )},
                {"(x+y)-(x+y)+(x+y)-(x+y)", List.of(
                        "(x+y)-(x+y)+(x+y)*(1-1)",
                        "(x+y)*(1-1)+(x+y)*(1-1)",
                        "(x+y)*(1-1)*(1+1)",
                        "-(x+y)+(x+y)*(1+1-1)",
                        "(x+y)*(-1+1+1-1)",
                        "(x+y)+(x+y)*(-1+1-1)",
                        "-(x+y)-(x+y)+(x+y)*(1+1)",
                        "(x+y)*(1+1)+(x+y)*(-1-1)",
                        "(x+y)+(x+y)+(x+y)*(-1-1)"
                )},
                {"(x+y)-(x*(2-3*x)+y*(2-3*x))*(x+y)", List.of(
                        "(x+y)*(1-x*(2-3*x)-y*(2-3*x))",
                        "(x+y)*(1+(2-3*x)*(-x-y))",
                        "(x+y)-(x+y)*(2-3*x)*(x+y)",
                        "(x+y)*(1-(2-3*x)*(x+y))"
                )},
                {"2*(x-y)+ 4*(x-y)", List.of(
                        "(x-y)*(2+4)"
                )},
                {"(v-n)*(2*(x-y)+ 4*(x-y))-(v-n)", List.of(
                        "(v-n)*(2*(x-y)+4*(x-y)-1)",
                        "(v-n)*(-1+(x-y)*(2+4))",
                        "(v-n)*(x-y)*(2+4)-(v-n)"
                )},
                {"(v-n)*(2*(x-y)+ 4*(x-y))-(v-n)*(4*x-5*x)", List.of(
                        "(v-n)*(2*(x-y)+4*(x-y)-4*x+5*x)",
                        "(v-n)*(-4*x+5*x+(x-y)*(2+4))",
                        "(v-n)*((x-y)*(2+4)+x*(-4+5))",
                        "(v-n)*(2*(x-y)+5*x+4*(x-y-x))",
                        "(v-n)*(2*(x-y)+5*x+4*(-y+x*(1-1)))",
                        "(v-n)*(2*(x-y)+4*(x-y)+x*(-4+5))",
                        "(v-n)*(x-y)*(2+4)-(v-n)*(4*x-5*x)",
                        "(v-n)*(x-y)*(2+4)-x*(v-n)*(4-5)",
                        "(v-n)*((x-y)*(2+4)-x*(4-5))",
                        "(v-n)*(2*(x-y)+4*(x-y))-x*(v-n)*(4-5)",
                        "(v-n)*(2*(x-y)+4*(x-y)-x*(4-5))"
                )},

        });
    }

    private ExpressionAnalyzerController subject = new ExpressionAnalyzerController();

    @Parameterized.Parameter
    public String inputExpression;
    @Parameterized.Parameter(1)
    public List<String> expectedOutputExpressions;

    @Test
    public void test() {
        assertThat(subject.passBrackets(inputExpression)).isEqualTo(expectedOutputExpressions);
    }
}
