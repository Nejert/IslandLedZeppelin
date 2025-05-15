package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.entity.abstraction.Herbivore;

public class Buffalo extends Herbivore {
    public Buffalo(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Buffalo(Buffalo buffalo) {
        super(buffalo);
    }

    @Override
    public Buffalo clone() {
        return new Buffalo(this);
    }
}
