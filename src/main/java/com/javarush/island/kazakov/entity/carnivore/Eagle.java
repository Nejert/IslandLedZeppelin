package com.javarush.island.kazakov.entity.carnivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Carnivore;

@Metric(
        weight = 6,
        maxQuantity = 20,
        maxSteps = 3,
        saturation = 1,
        icon = Default.EAGLE_ICON,
        imageIcon = Default.EAGLE_IMAGE
)
public class Eagle extends Carnivore {
    public Eagle(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Eagle(Eagle eagle) {
        super(eagle);
    }

    @Override
    public Eagle clone() {
        return new Eagle(this);
    }
}
