package com.javarush.island.kazakov.system;

import com.javarush.island.kazakov.component.Eating;
import com.javarush.island.kazakov.entity.abstraction.Animal;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.entity.misc.EatingProbability;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.util.Rnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EatSystem extends AbstractSystem {

    public EatSystem(GameMap gameMap) {
        super(gameMap);
    }

    @Override
    public void update() {
        for (int y = 0; y < gameMap.getRows(); y++) {
            for (int x = 0; x < gameMap.getCols(); x++) {
                Cell cell = gameMap.getCell(y, x);
                cell.lock();
                try {
                    eat(cell);
                } finally {
                    cell.unlock();
                }
            }
        }
        accept(this::killStarvingAnimals);
    }

    private void eat(Cell cell) {
        List<Entity> allEntities = cell.getVisitors().entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());
        int size = allEntities.size();
        for (int i = 0; i < size; ) {
            Entity eater = allEntities.get(i);
            if (eater instanceof Eating e) {
                Entity pray = findVictim((Animal) eater, allEntities);
                if (pray != null) {
                    e.eat(pray);
                    cell.remove(pray);
                    allEntities.remove(pray);
                }
            }
            if (allEntities.size() == size) {
                i++;
            } else {
                size = allEntities.size();
            }
        }
    }

    private Entity findVictim(Animal eater, List<Entity> cellVisitors) {
        if (!eater.isHungry()) return null;
        for (Entity pray : cellVisitors) {
            int probability = EatingProbability.getProbability(eater, pray);
            if (Rnd.get(probability)) {
                return pray;
            }
        }
        return null;
    }

    private void killStarvingAnimals(Cell ignoredCell, List<Entity> cellVisitors) {
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
