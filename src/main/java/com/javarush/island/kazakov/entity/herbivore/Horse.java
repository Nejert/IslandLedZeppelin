package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.entity.abstraction.Herbivore;

public class Horse extends Herbivore {
    public Horse(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Horse(Horse horse) {
        super(horse);
    }

    @Override
    public Horse clone() {
        return new Horse(this);
    }
}
