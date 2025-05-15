package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.entity.abstraction.Herbivore;

public class Boar extends Herbivore {
    public Boar(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Boar(Boar boar) {
        super(boar);
    }

    @Override
    public Boar clone() {
        return new Boar(this);
    }
}
