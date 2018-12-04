package com.gmail.at.sichyuriyy.computer.systems.conveyor;

import com.gmail.at.sichyuriyy.computer.systems.polishnotation.PolishTokenType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Operation {
    private long id;
    private PolishTokenType type;
    private String label;
}
