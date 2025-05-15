package com.javarush.island.kazakov.entity.carnivore;

import com.javarush.island.kazakov.entity.abstraction.Carnivore;

public class Wolf extends Carnivore {
    public Wolf(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Wolf(Wolf wolf) {
        super(wolf);
    }

    @Override
    public Wolf clone() {
        return new Wolf(this);
    }
}
