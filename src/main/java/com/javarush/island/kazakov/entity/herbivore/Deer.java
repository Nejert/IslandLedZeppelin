package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.entity.abstraction.Herbivore;

public class Deer extends Herbivore {
    public Deer(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Deer(Deer deer) {
        super(deer);
    }

    @Override
    public Deer clone() {
        return new Deer(this);
    }
}
