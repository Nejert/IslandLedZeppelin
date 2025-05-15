package com.javarush.island.kazakov.entity.carnivore;

import com.javarush.island.kazakov.entity.abstraction.Carnivore;

public class Boa extends Carnivore {
    public Boa(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Boa(Boa boa) {
        super(boa);
    }

    @Override
    public Boa clone() {
        return new Boa(this);
    }
}
