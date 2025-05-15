package com.javarush.island.kazakov.entity.carnivore;

import com.javarush.island.kazakov.entity.abstraction.Carnivore;

public class Bear extends Carnivore {
    public Bear(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Bear(Bear bear) {
        super(bear);
    }

    @Override
    public Bear clone() {
        return new Bear(this);
    }
}
