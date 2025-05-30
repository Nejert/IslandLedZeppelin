package com.javarush.island.kazakov.system;

import com.javarush.island.kazakov.config.Config;
import com.javarush.island.kazakov.entity.abstraction.Animal;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.entity.misc.EntityFactory;
import com.javarush.island.kazakov.entity.misc.EntityType;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.khmelov.util.Rnd;

import java.util.List;
import java.util.stream.IntStream;

public class ReproduceSystem extends AbstractSystem {

    public ReproduceSystem(GameMap gameMap) {
        super(gameMap);
    }

    @Override
    public void update() {
        accept(this::reproduceAllInCell);
    }

    @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
    private void reproduceAllInCell(Cell ignoredCell, List<Entity> cellVisitors) {
        int offspring = 0;
        for (Entity entity : cellVisitors) {
            if (entity instanceof Animal animal) {
                if (!animal.desireToBeNaughty() || animal.isParent()) {
                    continue;
                }
                Entity couple = findCouple(cellVisitors);
                if (couple != null && animal != couple) {
                    ((Animal) entity).reproduce(couple);
                    if (Rnd.get(Config.get().getBirthProbability()))
                        offspring++;
                }
            }
        }
        if (offspring > 0) {
            born(cellVisitors.get(0), cellVisitors, offspring);
        }
    }

    private void resetFlag(List<Entity> cellVisitors) {
        cellVisitors.forEach(entity -> entity.setParent(false));
    }

    @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
    private void born(Entity entity, List<Entity> cellVisitors, int offspring) {
        resetFlag(cellVisitors);
        int maxQuantity = entity.getMaxQuantity();
        EntityType type = EntityType.valueOf(cellVisitors.get(0).getClass());
        IntStream.iterate(0, i -> i < offspring && cellVisitors.size() < maxQuantity, i -> i + 1)
                .mapToObj(i -> EntityFactory.newEntity(type))
                .forEach(cellVisitors::add);
    }

    @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
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
