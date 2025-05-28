package com.javarush.island.kazakov.entity.carnivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Carnivore;

@Metric(
        weight = 8,
        maxQuantity = 30,
        maxSteps = 2,
        saturation = 2,
        icon = Default.FOX_ICON,
        imageIcon = Default.FOX_IMAGE
)
public class Fox extends Carnivore {
    public Fox(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Fox(Fox fox) {
        super(fox);
    }

    @Override
    public Fox clone() {
        return new Fox(this);
    }
}
