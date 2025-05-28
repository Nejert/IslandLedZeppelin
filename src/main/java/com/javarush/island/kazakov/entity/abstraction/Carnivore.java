package com.javarush.island.kazakov.entity.abstraction;

public abstract class Carnivore extends Animal {
    protected Carnivore(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    protected Carnivore(Carnivore c) {
        super(c);
    }
}
