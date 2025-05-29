package com.javarush.island.kazakov.entity.abstraction;

import com.javarush.island.kazakov.component.Eating;
import com.javarush.island.kazakov.component.Movable;
import com.javarush.island.kazakov.component.Reproducible;
import com.javarush.island.kazakov.config.Config;
import com.javarush.island.kazakov.map.Cell;
import lombok.Getter;

@Getter
public abstract class Animal extends Entity implements Movable, Eating, Reproducible {
    private final int maxSteps;
    private final double saturation;
    private final double saturationDecreaseFactor;
    private final double quarterOfSaturation;
    private double currentSaturation;

    protected Animal(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity);
        this.maxSteps = maxSteps;
        this.saturation = saturation;
        this.currentSaturation = saturation;
        this.saturationDecreaseFactor = saturation / Config.get().getAnimalMovesCount();
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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean desireToBeNaughty() {
        return saturation - quarterOfSaturation <= currentSaturation;
    }

    @Override
    public void reproduce(Entity entity) {
        this.setParent(true);
        entity.setParent(true);
    }

    @Override
    public void eat(Entity pray) {
        this.setCurrentSaturation(this.getCurrentSaturation() + pray.getWeight());
    }

    @Override
    public void move(Cell origin, Cell dest) {
        dest.lock();
        try {
            dest.add(this);
            setMoved(dest.contains(this));
        } finally {
            dest.unlock();
        }
        origin.lock();
        try {
            if (this.isMoved()) {
                origin.remove(this);
            }
        } finally {
            origin.unlock();
        }
    }
}
