package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Herbivore;

@Metric(
        weight = 2,
        maxQuantity = 150,
        maxSteps = 2,
        saturation = 0.45,
        icon = Default.RABBIT_ICON,
        imageIcon = Default.RABBIT_IMAGE
)
public class Rabbit extends Herbivore {
    public Rabbit(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Rabbit(Rabbit rabbit) {
        super(rabbit);
    }

    @Override
    public Rabbit clone() {
        return new Rabbit(this);
    }
}
