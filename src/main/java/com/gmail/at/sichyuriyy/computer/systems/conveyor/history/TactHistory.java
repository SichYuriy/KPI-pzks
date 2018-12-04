package com.gmail.at.sichyuriyy.computer.systems.conveyor.history;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TactHistory {
    private List<LayerHistory> layerHistories;
    private double time;
}
