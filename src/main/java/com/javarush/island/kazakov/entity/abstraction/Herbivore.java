package com.javarush.island.kazakov.entity.abstraction;


public abstract class Herbivore extends Animal{
    protected Herbivore(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    protected Herbivore(Herbivore h) {
        super(h);
    }
}
