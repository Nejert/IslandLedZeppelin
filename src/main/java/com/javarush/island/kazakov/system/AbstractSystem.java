package com.javarush.island.kazakov.system;

import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class AbstractSystem {
    protected final GameMap gameMap;

    public AbstractSystem(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public abstract void update();

    protected void accept(BiConsumer<Cell, List<Entity>> action) {
        for (int y = 0; y < gameMap.getRows(); y++) {
            for (int x = 0; x < gameMap.getCols(); x++) {
                Cell cell = gameMap.getCell(y, x);
                cell.getVisitors().entrySet().forEach(entry -> {
                    cell.lock();
                    try {
                        action.accept(cell, entry.getValue());
                    } finally {
                        cell.unlock();
                    }
                });
            }
        }
    }
}
