package com.gmail.at.sichyuriyy.computer.systems.application.controller;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.ExpressionReader;
import com.gmail.at.sichyuriyy.computer.systems.expressiontree.TreeBuilder;
import com.gmail.at.sichyuriyy.computer.systems.expressiontree.TreeNode;
import com.gmail.at.sichyuriyy.computer.systems.expressiontree.optimizer.AllVariantsTreeOptimizer;
import com.gmail.at.sichyuriyy.computer.systems.expressiontree.optimizer.TreeOptimizer;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxError;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxParser;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate.ParserState;
import com.gmail.at.sichyuriyy.computer.systems.util.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExpressionAnalyzerController {

    private SyntaxParser syntaxParser = new SyntaxParser();
    private ExpressionReader expressionReader = new ExpressionReader();
    private TreeBuilder treeBuilder = new TreeBuilder();
    private TreeTransformer treeTransformer = new TreeTransformer();
    private TreeOptimizer treeOptimizer = new AllVariantsTreeOptimizer();

    @GetMapping("/analyze")
    public ExpressionAnalysisResultDto analyze(@RequestParam String expression) {
        String exp = StringUtil.deleteWhiteSpaces(expression);
        Expression tokenExpression = expressionReader.readExpression(exp);
        ParserState parseResult = syntaxParser.parseExpression(tokenExpression);
        List<SyntaxError> syntaxErrors = parseResult.getFoundErrors();
        if (!syntaxErrors.isEmpty()) {
            return new ExpressionAnalysisResultDto(exp, syntaxErrors, new ArrayList<>(), null, null);
        } else {
            TreeNode root = treeBuilder.buildTree(parseResult.getPolishNotation());
            return ExpressionAnalysisResultDto.builder()
                    .expression(exp)
                    .errors(syntaxErrors)
                    .polishNotation(parseResult.getPolishNotation())
                    .root(treeTransformer.toDto(root))
                    .optimizedRoot(treeTransformer.toDto(treeOptimizer.optimize(root)))
                    .build();
        }
    }
}
