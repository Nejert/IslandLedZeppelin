package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.entity.abstraction.Herbivore;

public class Sheep extends Herbivore {
    public Sheep(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Sheep(Sheep sheep) {
        super(sheep);
    }

    @Override
    public Sheep clone() {
        return new Sheep(this);
    }
}
