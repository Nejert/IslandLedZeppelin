package com.javarush.island.kazakov.system;

import com.javarush.island.kazakov.component.Movable;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Animal;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.util.Direction;
import com.javarush.island.kazakov.util.Location;
import com.javarush.island.kazakov.util.Rnd;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class MoveSystem {
    private final GameMap gameMap;

    public MoveSystem(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void update() {
        move(this::moveAllInCell);
        move(this::resetFlag);
        move(this::decreaseSaturation);
    }

    private void move(BiConsumer<Cell, List<Entity>> action) {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                Cell cell = gameMap.getCell(y, x);
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cell.getVisitors().entrySet()) {
                    action.accept(cell, entry.getValue());
                }
            }
        }
    }

    private void resetFlag(Cell cell, List<Entity> cellVisitors) {
        cellVisitors.forEach(entity -> entity.setMoved(false));
    }

    private void moveAllInCell(Cell cell, List<Entity> cellVisitors) {
        for (int i = 0; i < cellVisitors.size(); ) {
            Entity entity = cellVisitors.get(i);
            if (entity instanceof Movable m && !entity.isMoved()) {
                Cell newCell = findDestinationCell(cell, ((Animal) entity).getMaxSteps());
                m.move(cell, newCell);
            } else {
                i++;
            }
        }

    }

    private void decreaseSaturation(Cell cell, List<Entity> cellVisitors) {
        for (int i = 0; i < cellVisitors.size(); i++) {
            Entity entity = cellVisitors.get(i);
            if (entity instanceof Animal animal) {
                animal.decreaseSaturation();
            }
        }
    }

    public Cell findDestinationCell(Cell origin, int maxSteps) {
        int steps = Rnd.random(maxSteps + 1); // (0,maxSteps) may chose not to move
        Location startLoc = origin.getLocation();
        Location newLoc = startLoc;
        for (int i = 0; i < steps; i++) {
            boolean isLocationChosen = false;
            while (!isLocationChosen) {
                int directionIndex = Rnd.random(Direction.values().length);
                Direction direction = Direction.values()[directionIndex];
                newLoc = choseNewLocation(direction, newLoc);
                isLocationChosen = newLoc != startLoc;
            }
        }
        return gameMap.getCell(newLoc.y(), newLoc.x());
    }

    private Location choseNewLocation(Direction direction, Location oldLocation) {
        int tempX = oldLocation.x();
        int tempY = oldLocation.y();
        switch (direction) {
            case UP -> tempY--;
            case DOWN -> tempY++;
            case LEFT -> tempX--;
            case RIGHT -> tempX++;
        }
        if (tempX >= 0 && tempY >= 0 && tempX < Default.COLS && tempY < Default.ROWS) {
            return new Location(tempX, tempY);
        }
        return oldLocation;
    }
}
