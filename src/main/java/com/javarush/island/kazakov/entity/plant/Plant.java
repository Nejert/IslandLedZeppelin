package com.javarush.island.kazakov.entity.plant;

import com.javarush.island.kazakov.entity.abstraction.Entity;

public class Plant extends Entity {
    public Plant(double weight, int maxQuantity) {
        super(weight, maxQuantity);
    }

    private Plant(Plant plant) {
        super(plant);
    }

    @Override
    public Plant clone() {
        return new Plant(this);
    }
}
