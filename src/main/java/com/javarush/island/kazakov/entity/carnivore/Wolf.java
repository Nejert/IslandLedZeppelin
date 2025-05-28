package com.javarush.island.kazakov.entity.carnivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Carnivore;

@Metric(
        weight = 50,
        maxQuantity = 30,
        maxSteps = 3,
        saturation = 8,
        icon = Default.WOLF_ICON,
        imageIcon = Default.WOLF_IMAGE
)
public class Wolf extends Carnivore {
    public Wolf(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Wolf(Wolf wolf) {
        super(wolf);
    }

    @Override
    public Wolf clone() {
        return new Wolf(this);
    }
}
