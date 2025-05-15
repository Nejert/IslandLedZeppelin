package com.javarush.island.kazakov.entity.carnivore;

import com.javarush.island.kazakov.entity.abstraction.Carnivore;

public class Eagle extends Carnivore {
    public Eagle(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Eagle(Eagle eagle) {
        super(eagle);
    }

    @Override
    public Eagle clone() {
        return new Eagle(this);
    }
}
