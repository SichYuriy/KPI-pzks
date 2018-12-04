package com.gmail.at.sichyuriyy.computer.systems.application.controller;

import com.gmail.at.sichyuriyy.computer.systems.conveyor.history.ExecutionHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimulationResultDto {
    private ExecutionHistory executionHistory;
    private TreeNodeDto root;
    private double baseTime;
    private double speedUp;
    private double busyTime;
}
