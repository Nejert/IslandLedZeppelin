package com.javarush.island.kazakov.map;

import com.javarush.island.kazakov.util.Location;
import lombok.Getter;

@Getter
public class GameMap {
    private final Cell[][] cells;
    private final int rows;
    private final int cols;

    public GameMap(int rows, int cols) {
        cells = new Cell[rows][cols];
        this.rows = rows;
        this.cols = cols;
        init();
    }

    private void init() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                cells[y][x] = new Cell(new Location(x, y));
            }
        }
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }
}
