package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.entity.abstraction.Herbivore;

public class Rabbit extends Herbivore {
    public Rabbit(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Rabbit(Rabbit rabbit) {
        super(rabbit);
    }

    @Override
    public Rabbit clone() {
        return new Rabbit(this);
    }
}
