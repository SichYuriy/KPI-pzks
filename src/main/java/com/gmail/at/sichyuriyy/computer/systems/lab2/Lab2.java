package com.gmail.at.sichyuriyy.computer.systems.lab2;

import com.gmail.at.sichyuriyy.computer.systems.Expression;
import com.gmail.at.sichyuriyy.computer.systems.ExpressionReader;
import com.gmail.at.sichyuriyy.computer.systems.Main;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxParser;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate.ParserState;

import java.util.Scanner;

public class Lab2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ExpressionReader reader = new ExpressionReader();
        SyntaxParser syntaxParser = new SyntaxParser();
        //noinspection InfiniteLoopStatement
        while (true) {
            String str = in.nextLine();
            Expression exp = reader.readExpression(str);
            ParserState parseResult = syntaxParser.parseExpression(exp);
            parseResult.getFoundErrors().forEach(Main::print);
            if (parseResult.getFoundErrors().isEmpty()) {
                parseResult.getPolishNotation().stream()
                        .map(PolishToken::getValue)
                        .forEach(val -> System.out.print(val + ","));
                System.out.println();
            }
        }
    }
}
