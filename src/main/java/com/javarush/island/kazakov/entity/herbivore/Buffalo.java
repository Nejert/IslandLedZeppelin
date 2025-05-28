package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Herbivore;

@Metric(
        weight = 700,
        maxQuantity = 10,
        maxSteps = 3,
        saturation = 100,
        icon = Default.BUFFALO_ICON,
        imageIcon = Default.BUFFALO_IMAGE
)
public class Buffalo extends Herbivore {
    public Buffalo(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Buffalo(Buffalo buffalo) {
        super(buffalo);
    }

    @Override
    public Buffalo clone() {
        return new Buffalo(this);
    }
}
