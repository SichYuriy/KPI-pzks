package com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.parserstate;

import com.gmail.at.sichyuriyy.computer.systems.syntaxanalizator.SyntaxError;

import java.util.ArrayList;
import java.util.List;

public class StateData {
    List<SyntaxError> foundErrors = new ArrayList<>();
    int openParenthesisCount;
}
