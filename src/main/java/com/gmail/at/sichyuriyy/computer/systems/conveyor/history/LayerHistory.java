package com.gmail.at.sichyuriyy.computer.systems.conveyor.history;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LayerHistory {
    private String operation;
    private double effectiveTime;
}
