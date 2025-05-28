package com.javarush.island.kazakov.entity.carnivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Carnivore;

@Metric(
        weight = 500,
        maxQuantity = 5,
        maxSteps = 2,
        saturation = 80,
        icon = Default.BEAR_ICON,
        imageIcon = Default.BEAR_IMAGE
)
public class Bear extends Carnivore {
    public Bear(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Bear(Bear bear) {
        super(bear);
    }

    @Override
    public Bear clone() {
        return new Bear(this);
    }
}
