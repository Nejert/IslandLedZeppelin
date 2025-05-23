package com.javarush.island.kazakov.system;

import com.javarush.island.kazakov.component.Reproducible;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Animal;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.entity.misc.EntityFactory;
import com.javarush.island.kazakov.entity.misc.EntityType;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ReproduceSystem {
    private final GameMap gameMap;

    public ReproduceSystem(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void update() {
        reproduce(this::reproduceAllInCell);
    }

    private void reproduce(BiConsumer<Cell, List<Entity>> action) {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                Cell cell = gameMap.getCell(y, x);
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cell.getVisitors().entrySet()) {
                    action.accept(cell, entry.getValue());
                }
            }
        }
    }

    private void resetFlag(List<Entity> cellVisitors) {
        cellVisitors.forEach(entity -> entity.setParent(false));
    }

    private void reproduceAllInCell(Cell cell, List<Entity> cellVisitors) {
        int offspring = 0;
        for (Entity entity : cellVisitors) {
            if (entity instanceof Animal animal) {
                if (!animal.desireToBeNaughty() || animal.isParent()) {
                    continue;
                }
                Entity couple = findCouple(cellVisitors);
                if (couple != null && animal != couple) {
                    ((Animal) entity).reproduce(couple);
                    offspring++;
                }
            }
        }
        if (offspring > 0) {
            born(cellVisitors.get(0), cellVisitors, offspring);
        }
    }

    private void born(Entity entity, List<Entity> cellVisitors, int offspring) {
        resetFlag(cellVisitors);
        int maxQuantity = entity.getMaxQuantity();
        EntityType type = EntityType.valueOf(cellVisitors.get(0).getClass());
        for (int i = 0; i < offspring && cellVisitors.size() < maxQuantity; i++) {
            cellVisitors.add(EntityFactory.newEntity(type));
        }
    }

    private Entity findCouple(List<Entity> cellVisitors) {
        for (Entity entity : cellVisitors) {
            if (cellVisitors.size() >= cellVisitors.get(0).getMaxQuantity()) {
                return null;
            }
            if (entity instanceof Animal animal) {
                if (!animal.desireToBeNaughty()) {
                    continue;
                }
                if (!entity.isParent()) {
                    return entity;
                }
            }
        }
        return null;
    }
}
