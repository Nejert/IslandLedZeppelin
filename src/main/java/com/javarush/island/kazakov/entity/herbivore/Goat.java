package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Herbivore;

@Metric(
        weight = 60,
        maxQuantity = 140,
        maxSteps = 3,
        saturation = 10,
        icon = Default.GOAT_ICON,
        imageIcon = Default.GOAT_IMAGE
)
public class Goat extends Herbivore {
    public Goat(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Goat(Goat goat) {
        super(goat);
    }

    @Override
    public Goat clone() {
        return new Goat(this);
    }
}
