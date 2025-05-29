package com.javarush.island.kazakov.system;

import com.javarush.island.kazakov.component.Movable;
import com.javarush.island.kazakov.entity.abstraction.Animal;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.util.Direction;
import com.javarush.island.kazakov.util.Location;
import com.javarush.island.kazakov.util.Rnd;

import java.util.List;

public class MoveSystem extends AbstractSystem {

    public MoveSystem(GameMap gameMap) {
        super(gameMap);
    }

    @Override
    public void update() {
        accept(this::move);
        accept(this::resetFlag);
        accept(this::decreaseSaturation);
    }

    private void move(Cell cell, List<Entity> cellVisitors) {
        for (int i = 0; i < cellVisitors.size(); ) {
            Entity entity = cellVisitors.get(i);
            if (entity instanceof Movable m && !entity.isMoved()) {
                Cell newCell = findDestinationCell(cell, ((Animal) entity).getMaxSteps());
                List<Entity> entities = newCell.get(entity.getClass());
                if (entities == null || entities.size() < entity.getMaxQuantity()) {
                    m.move(cell, newCell);
                    continue;
                }
            }
            i++;
        }
    }

    private void resetFlag(Cell ignoredCell, List<Entity> cellVisitors) {
        cellVisitors.forEach(entity -> entity.setMoved(false));
    }

    private void decreaseSaturation(Cell ignoredCell, List<Entity> cellVisitors) {
        cellVisitors.stream()
                .filter(entity -> entity instanceof Animal)
                .map(entity -> (Animal) entity)
                .forEach(Animal::decreaseSaturation);
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
        if (tempX >= 0 && tempY >= 0 && tempX < gameMap.getCols() && tempY < gameMap.getRows()) {
            return new Location(tempX, tempY);
        }
        return oldLocation;
    }
}
