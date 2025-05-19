package com.javarush.island.kazakov.entity.abstraction;

import com.javarush.island.kazakov.TempRnd;
import com.javarush.island.kazakov.component.Eating;
import com.javarush.island.kazakov.component.Movable;
import com.javarush.island.kazakov.component.Reproducible;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.misc.EatingProbability;
import com.javarush.island.kazakov.entity.misc.EntityFactory;
import com.javarush.island.kazakov.entity.misc.EntityType;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.util.Direction;
import com.javarush.island.kazakov.util.Location;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@Getter
public abstract class Animal extends Entity implements Movable, Eating, Reproducible {
    private final int maxSteps;
    private final double saturation;
    private final double saturationDecreaseFactor;
    private final double quarterOfSaturation;
    private double currentSaturation;
    private ThreadLocalRandom rd = ThreadLocalRandom.current();
//    private Random rd = TempRnd.get();

    protected Animal(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity);
        this.maxSteps = maxSteps;
        this.saturation = saturation;
        this.currentSaturation = saturation;
        this.saturationDecreaseFactor = saturation / Default.ANIMAL_MOVES_COUNT;
        this.quarterOfSaturation = saturation / 4;
    }

    protected Animal(Animal a) {
        super(a);
        this.maxSteps = a.maxSteps;
        this.saturation = a.saturation;
        this.currentSaturation = a.currentSaturation;
        this.saturationDecreaseFactor = a.saturationDecreaseFactor;
        this.quarterOfSaturation = a.quarterOfSaturation;
    }

    public void setCurrentSaturation(double currentSaturation) {
        this.currentSaturation = currentSaturation;
        if (this.currentSaturation > saturation) {
            this.currentSaturation = saturation;
            setHungry(false);
        }
    }

    public void decreaseSaturation() {
        currentSaturation -= saturationDecreaseFactor;
        if (currentSaturation < saturation) {
            setHungry(true);
        }
        if (currentSaturation <= 0) {
            setDead(true);
        }
    }

    private boolean desireToBeNaughty() {
        return currentSaturation >= saturation - quarterOfSaturation;
    }

    @Override
    public boolean reproduce(Cell origin) {
        if (!desireToBeNaughty()) {
            return false;
        }
        for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : origin.getVisitors().entrySet()) {
            for (Entity entity : entry.getValue()) {
                if (this == entity) continue;
                if (this.isParent()) continue;
                if (entity instanceof Reproducible) {
                    if (((Animal) entity).desireToBeNaughty() && !entity.isParent()) {
                        this.setParent(true);
                        entity.setParent(true);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void eat(Cell origin) {
        if (!this.isHungry()) return;

        for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : origin.getVisitors().entrySet()) {
            List<Entity> value = entry.getValue();
            for (int i = 0; i < value.size(); ) {
                Entity entity = value.get(i);
                int probability = EatingProbability.getProbability(this, entity);
                if (rd.nextInt(100) <= probability) {
                    this.setCurrentSaturation(this.getCurrentSaturation() + entity.getWeight());
                    origin.remove(entity);
                } else {
                    i++;
                }
            }
        }
    }

    @Override
    public void move(Cell origin, Cell[][] cells) {
        int steps = rd.nextInt(this.getMaxSteps() + 1); // (0,maxSteps) may chose not to move
        Location startLoc = origin.getLocation();
        Location newLoc = startLoc;
        for (int i = 0; i < steps; i++) {
            boolean isLocationChosen = false;
            while (!isLocationChosen) {
                int directionIndex = rd.nextInt(Direction.values().length);
                Direction direction = Direction.values()[directionIndex];
                newLoc = choseNewLocation(direction, newLoc);
                isLocationChosen = newLoc != startLoc;
            }
        }
        traverseTo(origin, cells[newLoc.y()][newLoc.x()]);
    }

    private void traverseTo(Cell origin, Cell dest) {
        dest.add(this);
        setMoved(true);
        origin.remove(this);
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
