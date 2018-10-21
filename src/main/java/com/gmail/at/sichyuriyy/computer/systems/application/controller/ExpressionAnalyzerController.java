package com.gmail.at.sichyuriyy.computer.systems.application.controller;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.ExpressionReader;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxError;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxParser;
import com.gmail.at.sichyuriyy.computer.systems.util.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpressionAnalyzerController {

    private SyntaxParser syntaxParser = new SyntaxParser();
    private ExpressionReader expressionReader = new ExpressionReader();

    @GetMapping("/analyze")
    public ExpressionAnalysisResultDto analyze(@RequestParam String expression) {
        String exp = StringUtil.deleteWhiteSpaces(expression);
        Expression tokenExpression = expressionReader.readExpression(exp);
        List<SyntaxError> syntaxErrors = syntaxParser.findErrors(tokenExpression);
        return new ExpressionAnalysisResultDto(exp, syntaxErrors);
    }
}
