package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Herbivore;

@Metric(
        weight = 300,
        maxQuantity = 20,
        maxSteps = 4,
        saturation = 50,
        icon = Default.DEER_ICON,
        imageIcon = Default.DEER_IMAGE
)
public class Deer extends Herbivore {
    public Deer(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Deer(Deer deer) {
        super(deer);
    }

    @Override
    public Deer clone() {
        return new Deer(this);
    }
}
