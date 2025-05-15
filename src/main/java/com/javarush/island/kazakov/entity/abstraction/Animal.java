package com.javarush.island.kazakov.entity.abstraction;

import com.javarush.island.kazakov.component.Eating;
import com.javarush.island.kazakov.component.Movable;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.util.Direction;
import com.javarush.island.kazakov.util.Location;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;


@Getter
public abstract class Animal extends Entity implements Movable, Eating {
    private final int maxSteps;
    private final double saturation;
    private final double saturationDecreaseFactor;
    private double currentSaturation;

    protected Animal(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity);
        this.maxSteps = maxSteps;
        this.saturation = saturation;
        this.currentSaturation = saturation;
        this.saturationDecreaseFactor = saturation / Default.ANIMAL_MOVES_COUNT;
    }

    protected Animal(Animal a) {
        super(a);
        this.maxSteps = a.maxSteps;
        this.saturation = a.saturation;
        this.currentSaturation = a.currentSaturation;
        this.saturationDecreaseFactor = a.saturationDecreaseFactor;
    }

    public void setCurrentSaturation(double currentSaturation) {
        this.currentSaturation = currentSaturation;
        if (this.currentSaturation > saturation){
            this.currentSaturation = saturation;
            setHungry(false);
        }
    }

    public void decreaseSaturation() {
        currentSaturation-=saturationDecreaseFactor;
        if (currentSaturation < saturation) {
            setHungry(true);
        }
        if (currentSaturation <= 0) {
            setDead(true);
        }
    }

    @Override
    public void move(Cell origin, Cell[][] cells) {
        ThreadLocalRandom rd = ThreadLocalRandom.current();
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
