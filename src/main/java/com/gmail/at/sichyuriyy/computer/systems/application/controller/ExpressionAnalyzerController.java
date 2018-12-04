package com.gmail.at.sichyuriyy.computer.systems.application.controller;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.ExpressionReader;
import com.gmail.at.sichyuriyy.computer.systems.conveyor.DynamicConveyor;
import com.gmail.at.sichyuriyy.computer.systems.conveyor.TaskGenerator;
import com.gmail.at.sichyuriyy.computer.systems.conveyor.history.ExecutionHistory;
import com.gmail.at.sichyuriyy.computer.systems.conveyor.history.LayerHistory;
import com.gmail.at.sichyuriyy.computer.systems.conveyor.history.TactHistory;
import com.gmail.at.sichyuriyy.computer.systems.expressiontree.TreeBuilder;
import com.gmail.at.sichyuriyy.computer.systems.expressiontree.TreeNode;
import com.gmail.at.sichyuriyy.computer.systems.expressiontree.optimizer.AllVariantsTreeOptimizer;
import com.gmail.at.sichyuriyy.computer.systems.expressiontree.optimizer.TreeOptimizer;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.OutOfParenthesisAnalyzer;
import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishTokenType;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxError;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxParser;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate.ParserState;
import com.gmail.at.sichyuriyy.computer.systems.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishTokenType.*;
import static java.util.Comparator.comparingDouble;
import static java.util.Objects.requireNonNull;

@RestController
public class ExpressionAnalyzerController {

    private SyntaxParser syntaxParser = new SyntaxParser();
    private ExpressionReader expressionReader = new ExpressionReader();
    private TreeBuilder treeBuilder = new TreeBuilder();
    private TreeTransformer treeTransformer = new TreeTransformer();
    private TreeOptimizer treeOptimizer = new AllVariantsTreeOptimizer();
    private OutOfParenthesisAnalyzer outOfParenthesisAnalyzer = new OutOfParenthesisAnalyzer();

    @GetMapping("/analyze")
    public ExpressionAnalysisResultDto analyze(@RequestParam String expression) {
        String exp = StringUtils.deleteWhiteSpaces(expression);
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

    @GetMapping("/pass-brackets")
    public List<String> passBrackets(@RequestParam String expression) {
        Expression tokenExpression = expressionReader.readExpression(expression);
        var result = outOfParenthesisAnalyzer.getAllForms(tokenExpression);
        return result.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    @GetMapping("/simulate")
    public SimulationResultDto simulate(@RequestParam String expression,
                                        @RequestParam int layersCount,
                                        @RequestParam double addTime,
                                        @RequestParam double subtractTime,
                                        @RequestParam double multiplyTime,
                                        @RequestParam double divideTime,
                                        @RequestParam double sinTime,
                                        @RequestParam double cosTime) {
        Expression tokenExpression = expressionReader.readExpression(expression);
        ParserState parseResult = syntaxParser.parseExpression(tokenExpression);
        TreeNode root = treeOptimizer.optimize(treeBuilder.buildTree(parseResult.getPolishNotation()));
        TaskGenerator taskGenerator = new TaskGenerator(root);
        Map<PolishTokenType, Double> operationsTime = new HashMap<>();
        operationsTime.put(BINARY_PLUS, addTime);
        operationsTime.put(BINARY_MINUS, subtractTime);
        operationsTime.put(MULTIPLY, multiplyTime);
        operationsTime.put(DIVIDE, divideTime);
        operationsTime.put(SIN, sinTime);
        operationsTime.put(COS, cosTime);
        DynamicConveyor dynamicConveyor = new DynamicConveyor(operationsTime, taskGenerator, layersCount);
        ExecutionHistory executionHistory = dynamicConveyor.run();
        double baseTime = calculateBaseTime(root, operationsTime);
        return SimulationResultDto.builder()
                .executionHistory(executionHistory)
                .root(treeTransformer.toDto(root))
                .baseTime(baseTime)
                .speedUp(baseTime / executionHistory.getExecutionTime())
                .busyTime(calculateBusyTime(executionHistory))
                .build();
    }

    @GetMapping("/simulate-all")
    public List<ExpressionSimulationResultDto> simulateAll(@RequestParam List<String> expressions,
                                        @RequestParam int layersCount,
                                        @RequestParam double addTime,
                                        @RequestParam double subtractTime,
                                        @RequestParam double multiplyTime,
                                        @RequestParam double divideTime,
                                        @RequestParam double sinTime,
                                        @RequestParam double cosTime) {

        List<ExpressionSimulationResultDto> result = new ArrayList<>();
        for (String exp: expressions) {
            SimulationResultDto simulationResultDto = simulate(exp,
                    layersCount,
                    addTime,
                    subtractTime,
                    multiplyTime,
                    divideTime,
                    sinTime,
                    cosTime);
            result.add(new ExpressionSimulationResultDto(exp, simulationResultDto));
        }
        result.sort(comparingDouble(e -> e.getSimulationResult().getExecutionHistory().getExecutionTime()));
        return result;
    }

    private double calculateBusyTime(ExecutionHistory executionHistory) {
        double totalTime = 0;
        double effectiveTime = 0;
        for (TactHistory tactHistory: executionHistory.getTactHistories()) {
            for (LayerHistory layerHistory: tactHistory.getLayerHistories()) {
                totalTime += tactHistory.getTime();
                effectiveTime += layerHistory.getEffectiveTime();
            }
        }
        return effectiveTime / totalTime;
    }

    private double calculateBaseTime(TreeNode root, Map<PolishTokenType,Double> operationsTime) {
        double result = 0;
        ArrayDeque<TreeNode> nodes = new ArrayDeque<>();
        nodes.addLast(root);
        while (!nodes.isEmpty()) {
            TreeNode currentNode = requireNonNull(nodes.pollFirst());
            result += operationsTime.get(currentNode.getPolishToken().getType());
            if (currentNode.getLeft() != null
                    && currentNode.getLeft().getPolishToken().getType().isOperation()) {
                nodes.addLast(currentNode.getLeft());
            }
            if (currentNode.getRight() != null
                    && currentNode.getRight().getPolishToken().getType().isOperation()) {
                nodes.addLast(currentNode.getRight());
            }
        }
        return result;
    }
}
