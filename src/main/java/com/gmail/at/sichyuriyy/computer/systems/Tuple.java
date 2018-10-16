package com.gmail.at.sichyuriyy.computer.systems;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tuple<K, V> {
    private K key;
    private V value;
}

