package com.javarush.island.kazakov.map;

import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.util.Location;
import lombok.Getter;

public class GameMap {
    @Getter
    private final Cell[][] cells;

    public GameMap(int rows, int cols) {
        cells = new Cell[rows][cols];
        init();
    }

    private void init(){
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                cells[y][x] = new Cell(new Location(x, y));
            }
        }
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

}
