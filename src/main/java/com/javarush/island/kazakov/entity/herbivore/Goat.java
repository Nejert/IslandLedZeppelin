package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.entity.abstraction.Herbivore;

public class Goat extends Herbivore {
    public Goat(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Goat(Goat goat) {
        super(goat);
    }

    @Override
    public Goat clone() {
        return new Goat(this);
    }
}
