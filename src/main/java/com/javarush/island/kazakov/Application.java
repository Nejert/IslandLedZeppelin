package com.javarush.island.kazakov;

import com.javarush.island.kazakov.component.Eating;
import com.javarush.island.kazakov.component.Movable;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Animal;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.entity.misc.EntityFactory;
import com.javarush.island.kazakov.entity.misc.EntityType;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.util.Location;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Application {
    private static boolean running = true;

    public static void main(String[] args) {
        Cell[][] cells = new Cell[Default.ROWS][Default.COLS];
        //init
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                cells[y][x] = new Cell(new Location(x, y));
            }
        }
        //add entity
//        cells[0][0].add(EntityFactory.newEntity(EntityType.WOLF));
        cells[1][1].add(EntityFactory.newEntity(EntityType.RABBIT));

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
            ScheduledFuture<?> scheduledFuture = ses.scheduleWithFixedDelay(() -> {
                update(cells);
            }, 0, 2, TimeUnit.SECONDS);

        while (running){
            try {
                ses.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new IslandException(e);
            }
        }
        ses.shutdown();
    }

    private static void update(Cell[][] cells) {
        display(cells);
        spawnPlants(cells);
        moveAll(cells);
        decreaseSaturation(cells);
        eatAll(cells);
        killStarvingAnimals(cells);
        checkSimulationEnd(cells);
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
            for (Entity entity : entry.getValue()) {
                if (entity instanceof Eating e) {
                    e.eat(cell);
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
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int growsProbability = 10;
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                if (random.nextInt(100) < growsProbability) {
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

    private static void display(Cell[][] cells) {
        System.out.println();
        for (int y = 0; y < Default.ROWS; y++) {
            System.out.print("|");
            for (int x = 0; x < Default.COLS; x++) {
                System.out.print(cells[y][x] + "|");
            }
            System.out.println();
        }
    }
}

