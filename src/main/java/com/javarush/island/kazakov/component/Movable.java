package com.javarush.island.kazakov.component;

import com.javarush.island.kazakov.map.Cell;

public interface Movable {
    void move(Cell fromCell, Cell[][] cells);
}
