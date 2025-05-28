package com.javarush.island.kazakov.entity.plant;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Entity;

@Metric(
        weight = 1,
        maxQuantity = 200,
        icon = Default.PLANT_ICON,
        imageIcon = Default.PLANT_IMAGE
)
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
