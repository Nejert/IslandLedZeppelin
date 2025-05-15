package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.entity.abstraction.Herbivore;

public class Caterpillar extends Herbivore {
    public Caterpillar(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Caterpillar(Caterpillar caterpillar) {
        super(caterpillar);
    }

    @Override
    public Caterpillar clone() {
        return new Caterpillar(this);
    }
}
