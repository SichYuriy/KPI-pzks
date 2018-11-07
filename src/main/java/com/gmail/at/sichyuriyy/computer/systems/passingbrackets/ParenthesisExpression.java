package com.gmail.at.sichyuriyy.computer.systems.passingbrackets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParenthesisExpression {
    private List<ParenthesisToken> terms;
}
