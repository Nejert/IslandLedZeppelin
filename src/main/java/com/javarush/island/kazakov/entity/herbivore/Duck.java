package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.entity.abstraction.Herbivore;

public class Duck extends Herbivore {
    public Duck(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Duck(Duck duck) {
        super(duck);
    }

    @Override
    public Duck clone() {
        return new Duck(this);
    }
}
