package com.javarush.island.kazakov.entity.carnivore;

import com.javarush.island.kazakov.entity.abstraction.Carnivore;

public class Fox extends Carnivore {
    public Fox(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Fox(Fox fox) {
        super(fox);
    }

    @Override
    public Fox clone() {
        return new Fox(this);
    }
}
