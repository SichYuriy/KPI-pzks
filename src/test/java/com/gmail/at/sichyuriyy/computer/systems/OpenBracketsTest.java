package com.gmail.at.sichyuriyy.computer.systems;

import com.gmail.at.sichyuriyy.computer.systems.application.controller.ExpressionAnalyzerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class OpenBracketsTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(x+y)-3*7-(x+y)+4+7", List.of(
                        "(x+y)-3*7-(x+y)+4+7",
                        "x+y-3*7-x-y+4+7"
                )},
                {"(x+y)-(x+y)", List.of(
                        "(x+y)-(x+y)",
                        "x+y-x-y"
                )},
                {"7*m-7*n", List.of(
                        "7*m-7*n"
                )},
                {"(x+y)-(x+y)+(x+y)-(x+y)", List.of(
                        "(x+y)-(x+y)+(x+y)-(x+y)",
                        "x+y-x-y+x+y-x-y"
                )},
                {"(x+y)-(x*(2-3*x)+y*(2-3*x))*(x+y)", List.of(
                        "(x+y)-(x*(2-3*x)+y*(2-3*x))*(x+y)",
                        "(x+y)-((2*x-3*x*x)+y*(2-3*x))*(x+y)",
                        "(x+y)-((2*x-3*x*x)+(2*y-3*x*y))*(x+y)",
                        "(x+y)-(2*x-3*x*x+2*y-3*x*y)*(x+y)",
                        "(x+y)-(2*x*x+2*x*y-3*x*x*x-3*x*x*y+2*y*x+2*y*y-3*x*y*x-3*x*y*y)",
                        "x+y-2*x*x-2*x*y+3*x*x*x+3*x*x*y-2*y*x-2*y*y+3*x*y*x+3*x*y*y"
                )},
                {"2*(x-y)+ 4*(x-y)", List.of(
                        "2*(x-y)+4*(x-y)",
                        "(x*2-y*2)+4*(x-y)",
                        "(x*2-y*2)+(x*4-y*4)",
                        "x*2-y*2+x*4-y*4"
                )},
                {"(v-n)*(2*(x-y)+ 4*(x-y))-(v-n)", List.of(
                        "(v-n)*(2*(x-y)+4*(x-y))-(v-n)",
                        "(v-n)*((x*2-y*2)+4*(x-y))-(v-n)",
                        "(v-n)*((x*2-y*2)+(x*4-y*4))-(v-n)",
                        "(v-n)*(x*2-y*2+x*4-y*4)-(v-n)",
                        "(v*x*2-v*y*2+v*x*4-v*y*4-n*x*2+n*y*2-n*x*4+n*y*4)-(v-n)",
                        "v*x*2-v*y*2+v*x*4-v*y*4-n*x*2+n*y*2-n*x*4+n*y*4-v+n"
                )},
                {"(v-n)*(2*(x-y)+ 4*(x-y))-(v-n)*(4*x-5*x)", List.of(
                        "(v-n)*(2*(x-y)+4*(x-y))-(v-n)*(4*x-5*x)",
                        "(v-n)*((x*2-y*2)+4*(x-y))-(v-n)*(4*x-5*x)",
                        "(v-n)*((x*2-y*2)+(x*4-y*4))-(v-n)*(4*x-5*x)",
                        "(v-n)*(x*2-y*2+x*4-y*4)-(v-n)*(4*x-5*x)",
                        "(v*x*2-v*y*2+v*x*4-v*y*4-n*x*2+n*y*2-n*x*4+n*y*4)-(v-n)*(4*x-5*x)",
                        "(v*x*2-v*y*2+v*x*4-v*y*4-n*x*2+n*y*2-n*x*4+n*y*4)-(v*4*x-v*5*x-n*4*x+n*5*x)",
                        "v*x*2-v*y*2+v*x*4-v*y*4-n*x*2+n*y*2-n*x*4+n*y*4-v*4*x+v*5*x+n*4*x-n*5*x"
                )},
                {"(v-n)*(2*(x-y)+ 4*(x-y))-(v-n)*(4*x-5*x)-(v-n)*(4*x-5*x)", List.of(
                        "(v-n)*(2*(x-y)+4*(x-y))-(v-n)*(4*x-5*x)-(v-n)*(4*x-5*x)",
                        "(v-n)*((x*2-y*2)+4*(x-y))-(v-n)*(4*x-5*x)-(v-n)*(4*x-5*x)",
                        "(v-n)*((x*2-y*2)+(x*4-y*4))-(v-n)*(4*x-5*x)-(v-n)*(4*x-5*x)",
                        "(v-n)*(x*2-y*2+x*4-y*4)-(v-n)*(4*x-5*x)-(v-n)*(4*x-5*x)",
                        "(v*x*2-v*y*2+v*x*4-v*y*4-n*x*2+n*y*2-n*x*4+n*y*4)-(v-n)*(4*x-5*x)-(v-n)*(4*x-5*x)",
                        "(v*x*2-v*y*2+v*x*4-v*y*4-n*x*2+n*y*2-n*x*4+n*y*4)-(v*4*x-v*5*x-n*4*x+n*5*x)-(v-n)*(4*x-5*x)",
                        "(v*x*2-v*y*2+v*x*4-v*y*4-n*x*2+n*y*2-n*x*4+n*y*4)-(v*4*x-v*5*x-n*4*x+n*5*x)-(v*4*x-v*5*x-n*4*x+n*5*x)",
                        "v*x*2-v*y*2+v*x*4-v*y*4-n*x*2+n*y*2-n*x*4+n*y*4-v*4*x+v*5*x+n*4*x-n*5*x-v*4*x+v*5*x+n*4*x-n*5*x"
                )},
                {"(x*(x*(x*(x*(x*(x))))))+x", List.of(
                        "(x*(x*(x*(x*(x*x)))))+x",
                        "(x*(x*(x*(x*x*x))))+x",
                        "(x*(x*(x*x*x*x)))+x",
                        "(x*(x*x*x*x*x))+x",
                        "(x*x*x*x*x*x)+x",
                        "x*x*x*x*x*x+x"
                )},
                {"2*(3+f)+3-((x+y)*(w+t))", List.of(
                        "2*(3+f)+3-((x+y)*(w+t))",
                        "(3*2+f*2)+3-((x+y)*(w+t))",
                        "(3*2+f*2)+3-((x*w+x*t+y*w+y*t))",
                        "(3*2+f*2)+3-(x*w+x*t+y*w+y*t)",
                        "3*2+f*2+3-x*w-x*t-y*w-y*t"
                )},
                {"(x-(y-(z*(v+(v/(3+g))))))+d", List.of(
                        "(x-(y-(z*(v+(v/(3+g))))))+d",
                        "(x-(y-(z*(v+v/(3+g)))))+d",
                        "(x-(y-((v*z+v*z/(3+g)))))+d",
                        "(x-(y-(v*z+v*z/(3+g))))+d",
                        "(x-(y-v*z-v*z/(3+g)))+d",
                        "(x-y+v*z+v*z/(3+g))+d",
                        "x-y+v*z+v*z/(3+g)+d"
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
        LinkedHashSet<String> expected = new LinkedHashSet<>(expectedOutputExpressions);
        assertThat(subject.passBrackets(inputExpression)).isEqualTo(expected);
    }
}
