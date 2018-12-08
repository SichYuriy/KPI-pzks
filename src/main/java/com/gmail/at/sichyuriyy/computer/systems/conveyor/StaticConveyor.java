package com.gmail.at.sichyuriyy.computer.systems.conveyor;

import com.gmail.at.sichyuriyy.computer.systems.conveyor.history.ExecutionHistory;
import com.gmail.at.sichyuriyy.computer.systems.conveyor.history.LayerHistory;
import com.gmail.at.sichyuriyy.computer.systems.conveyor.history.TactHistory;
import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishTokenType;

import java.util.*;

public class StaticConveyor implements Conveyor {

    private final Map<PolishTokenType, Double> operationsTime;
    private final TaskGenerator taskGenerator;

    private final List<Operation> layers;

    public StaticConveyor(Map<PolishTokenType, Double> operationsTime,
                          TaskGenerator taskGenerator,
                          int layersCount) {
        this.operationsTime = operationsTime;
        this.taskGenerator = taskGenerator;
        this.layers = new ArrayList<>();
        for (int i = 0; i < layersCount; i++) {
            this.layers.add(null);
        }
    }

    @Override
    public ExecutionHistory run() {
        ExecutionHistory executionHistory = new ExecutionHistory();
        while (taskGenerator.hasMoreTasks() || !isEmpty()) {
            Optional<PolishTokenType> current = getCurrentOperationType();
            if (current.isPresent()) {
                taskGenerator.takeNextTypeOf(current.get())
                        .ifPresent(operation -> layers.set(0, operation));
            } else {
                taskGenerator.takeAnyNext()
                        .ifPresent(operation -> layers.set(0, operation));
            }
            executionHistory.addTactHistory(runNextTact());
        }
        return executionHistory;
    }

    private Optional<PolishTokenType> getCurrentOperationType() {
        return layers.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(Operation::getType);
    }

    private TactHistory runNextTact() {
        TactHistory tactHistory = collectTactHistory(layers);
        if (layers.get(layers.size() - 1) != null) {
            taskGenerator.finishExecution(layers.get(layers.size() - 1));
        }
        layers.remove(layers.size() - 1);
        layers.add(0, null);
        return tactHistory;
    }

    private TactHistory collectTactHistory(List<Operation> layers) {
        List<LayerHistory> layerHistories = new ArrayList<>();
        for (Operation layer: layers) {
            if (layer == null) {
                layerHistories.add(new LayerHistory("", 0));
            } else {
                String id = layer.getId() != 0 ? "[" + layer.getId() + "] " : "";
                layerHistories.add(new LayerHistory(id + layer.getLabel(), operationsTime.get(layer.getType())));
            }
        }
        double tactTime = layerHistories.stream().mapToDouble(LayerHistory::getEffectiveTime).max().orElse(0);
        return new TactHistory(layerHistories, tactTime);

    }

    private boolean isEmpty() {
        return layers.stream().allMatch(Objects::isNull);
    }
}
