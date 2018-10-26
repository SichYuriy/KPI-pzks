package com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate;

import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishToken;
import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxError;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StateData {
    List<SyntaxError> foundErrors = new ArrayList<>();
    int openParenthesisCount;

    List<PolishToken> polishNotation = new ArrayList<>();
    Stack<PolishToken> operations = new Stack<>();
}
