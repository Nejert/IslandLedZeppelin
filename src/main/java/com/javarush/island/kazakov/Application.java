package com.javarush.island.kazakov;

import com.javarush.island.kazakov.component.Eating;
import com.javarush.island.kazakov.component.Movable;
import com.javarush.island.kazakov.component.Reproducible;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.config.SpawnProbability;
import com.javarush.island.kazakov.entity.abstraction.Animal;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.entity.misc.EntityFactory;
import com.javarush.island.kazakov.entity.misc.EntityType;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.map.Spawner;
import com.javarush.island.kazakov.system.DisplaySystem;
import com.javarush.island.kazakov.util.Location;
import com.javarush.island.kazakov.util.Rnd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class Application {
    private static boolean running = true;

    public static void main(String[] args) {
        GameMap gameMap = new GameMap(Default.ROWS, Default.COLS);
        Spawner spawner = new Spawner(gameMap);
        spawner.initialRandomSpawn();
        DisplaySystem displaySystem = new DisplaySystem(gameMap);

        //add entity


        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduledFuture = ses.scheduleWithFixedDelay(() -> {
            System.out.println(displaySystem.display());
            System.out.println(displaySystem.displayStats());
            spawnPlants(gameMap.getCells());
            moveAll(gameMap.getCells());
            decreaseSaturation(gameMap.getCells());
            eatAll(gameMap.getCells());
            killStarvingAnimals(gameMap.getCells());
            reproduceAll(gameMap.getCells());
            checkSimulationEnd(gameMap.getCells());
        }, 0, 1, TimeUnit.SECONDS);

        while (running) {
            try {
                ses.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new IslandException(e);
            }
        }
        ses.shutdown();
        /*while (running) {
            update(cells);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }*/
    }

    /*private static void update(GameMap gameMap) {
        display(cells);
        displayStatistic(cells);
        spawnPlants(cells);
        moveAll(cells);
        decreaseSaturation(cells);
        eatAll(cells);
        killStarvingAnimals(cells);
        reproduceAll(cells);
        checkSimulationEnd(cells);
    }*/

    private static void reproduceAll(Cell[][] cells) {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                reproduceAllInCell(cells[y][x]);
            }
        }
    }

    private static void reproduceAllInCell(Cell cell) {
        for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cell.getVisitors().entrySet()) {
            int offspring = 0;
            for (Entity entity : entry.getValue()) {
                if (entity instanceof Reproducible r) {
                    if (r.reproduce(cell)) {
                        offspring++;
                    }
                }
            }
            for (Entity entity : entry.getValue()) {
                if (entity instanceof Reproducible && entity.isParent()) {
                    ((Animal)entity).decreaseSaturation();
                    entity.setParent(false);
                }
            }
            for (int i = 0; i < offspring; i++) {
                entry.getValue().add(EntityFactory.newEntity(EntityType.valueOf(entry.getKey())));
            }
        }
    }

    private static void checkSimulationEnd(Cell[][] cells) {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cells[y][x].getVisitors().entrySet()) {
                    for (Entity entity : entry.getValue()) {
                        if (entity instanceof Animal) {
                            running = true;
                            return;
                        }
                    }
                }
            }
        }
        running = false;
    }

    private static void killStarvingAnimals(Cell[][] cells) {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cells[y][x].getVisitors().entrySet()) {
                    List<Entity> value = entry.getValue();
                    for (int i = 0; i < value.size(); ) {
                        Entity entity = value.get(i);
                        if (entity.isDead()) {
                            value.remove(i);
                        } else {
                            i++;
                        }
                    }
                }
            }
        }
    }

    private static void eatAll(Cell[][] cells) {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                eatAllInCell(cells[y][x]);
            }
        }
    }

    private static void eatAllInCell(Cell cell) {
        for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cell.getVisitors().entrySet()) {
            int size = entry.getValue().size();
            List<Entity> value = entry.getValue();
            for (int i = 0; i < size; ) {
                Entity entity = value.get(i);
                if (entity instanceof Eating e) {
                    e.eat(cell);
                }
                if (value.size() == size) {
                    i++;
                } else {
                    size = value.size();
                }
            }
        }
    }

    private static void decreaseSaturation(Cell[][] cells) {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                decreaseSaturationAllInCell(cells[y][x]);
            }
        }
    }

    private static void decreaseSaturationAllInCell(Cell cell) {
        for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cell.getVisitors().entrySet()) {
            List<Entity> value = entry.getValue();
            for (int i = 0; i < value.size(); i++) {
                Entity entity = value.get(i);
                if (entity instanceof Animal animal) {
                    animal.decreaseSaturation();
                }
            }
        }
    }

    private static void moveAll(Cell[][] cells) {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                moveAllInCell(cells[y][x], cells);
            }
        }
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                resetMoveFlag(cells[y][x]);
            }
        }
    }

    private static void spawnPlants(Cell[][] cells) {
        int growsProbability = 2;
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                if (Rnd.get(growsProbability)) {
                    cells[y][x].add(EntityFactory.newEntity(EntityType.PLANT));
                }
            }
        }
    }

    private static void resetMoveFlag(Cell cell) {
        for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cell.getVisitors().entrySet()) {
            entry.getValue().forEach(entity -> entity.setMoved(false));
        }
    }

    private static void moveAllInCell(Cell cell, Cell[][] cells) {
        for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cell.getVisitors().entrySet()) {
            List<Entity> listOfVisitors = entry.getValue();
            for (int i = 0; i < listOfVisitors.size(); ) {
                Entity entity = listOfVisitors.get(i);
                if (entity instanceof Movable m && !entity.isMoved()) {
                    m.move(cell, cells);
                } else {
                    i++;
                }
            }
        }
    }
}

