package com.javarush.island.kazakov.component;

import com.javarush.island.kazakov.map.Cell;

public interface Reproducible {
    boolean reproduce(Cell origin);
}
