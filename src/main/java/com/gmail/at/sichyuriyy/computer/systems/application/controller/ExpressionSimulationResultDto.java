package com.gmail.at.sichyuriyy.computer.systems.application.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpressionSimulationResultDto {
    private String expression;
    private SimulationResultDto simulationResult;
}
