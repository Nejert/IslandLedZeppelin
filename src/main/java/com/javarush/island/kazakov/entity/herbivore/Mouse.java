package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.entity.abstraction.Herbivore;

public class Mouse extends Herbivore {
    public Mouse(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Mouse(Mouse mouse) {
        super(mouse);
    }

    @Override
    public Mouse clone() {
        return new Mouse(this);
    }
}
