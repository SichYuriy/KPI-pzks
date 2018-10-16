package com.gmail.at.sichyuriyy.computer.systems;

import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxError;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxParser;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ExpressionReader reader = new ExpressionReader();
        SyntaxParser syntaxParser = new SyntaxParser();
        //noinspection InfiniteLoopStatement
        while (true) {
            String str = in.nextLine();
            Expression exp = reader.readExpression(str);
            List<SyntaxError> errors = syntaxParser.findErrors(exp);
            errors.forEach(Main::print);
        }
    }

    public static void print(SyntaxError error) {
        System.out.println(String.format("At position %d[%s]: %s", error.getPosition(), error.getTokenValue(), error.getErrorDescription()));
    }
}
