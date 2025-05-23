package com.javarush.island.kazakov.system;

import com.javarush.island.kazakov.component.Eating;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Animal;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.entity.misc.EatingProbability;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.util.Rnd;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class EatSystem {
    private final GameMap gameMap;

    public EatSystem(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void update() {
        eat(this::eatAllInCell);
        eat(this::killStarvingAnimals);
    }

    private void eat(BiConsumer<Cell, List<Entity>> action) {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                Cell cell = gameMap.getCell(y, x);
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cell.getVisitors().entrySet()) {
                    action.accept(cell, entry.getValue());
                }
            }
        }
    }

    private void eatAllInCell(Cell cell, List<Entity> cellVisitors) {
        int size = cellVisitors.size();
        for (int i = 0; i < size; ) {
            Entity eater = cellVisitors.get(i);
            if (eater instanceof Eating e) {
                Entity pray = findVictim((Animal) eater, cellVisitors);
                if (pray != null) {
                    e.eat(pray);
                    cell.remove(pray);
                }
            }
            if (cellVisitors.size() == size) {
                i++;
            } else {
                size = cellVisitors.size();
            }

        }
    }

    private Entity findVictim(Animal eater, List<Entity> cellVisitors) {
        if (!eater.isHungry()) return null;
        for (int i = 0; i < cellVisitors.size(); i++) {
            Entity pray = cellVisitors.get(i);
            int probability = EatingProbability.getProbability(eater, pray);
            if (Rnd.get(probability)) {
                return pray;
            }
        }
        return null;
    }

    private void killStarvingAnimals(Cell cell, List<Entity> cellVisitors) {
        for (int i = 0; i < cellVisitors.size(); ) {
            Entity entity = cellVisitors.get(i);
            if (entity.isDead()) {
                cellVisitors.remove(i);
            } else {
                i++;
            }
        }
    }
}
