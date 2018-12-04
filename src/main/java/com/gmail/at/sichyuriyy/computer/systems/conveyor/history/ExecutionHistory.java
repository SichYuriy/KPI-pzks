package com.gmail.at.sichyuriyy.computer.systems.conveyor.history;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExecutionHistory {
    private List<TactHistory> tactHistories = new ArrayList<>();
    private double executionTime = 0;

    public void addTactHistory(TactHistory tactHistory) {
        tactHistories.add(tactHistory);
        executionTime += tactHistory.getTime();
    }
}
