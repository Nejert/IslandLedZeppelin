package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Herbivore;

@Metric(
        weight = 400,
        maxQuantity = 20,
        maxSteps = 4,
        saturation = 60,
        icon = Default.HORSE_ICON,
        imageIcon = Default.HORSE_IMAGE
)
public class Horse extends Herbivore {
    public Horse(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Horse(Horse horse) {
        super(horse);
    }

    @Override
    public Horse clone() {
        return new Horse(this);
    }
}
