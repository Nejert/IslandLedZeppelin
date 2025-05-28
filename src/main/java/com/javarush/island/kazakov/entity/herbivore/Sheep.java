package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Herbivore;

@Metric(
        weight = 70,
        maxQuantity = 140,
        maxSteps = 3,
        saturation = 15,
        icon = Default.SHEEP_ICON,
        imageIcon = Default.SHEEP_IMAGE
)
public class Sheep extends Herbivore {
    public Sheep(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Sheep(Sheep sheep) {
        super(sheep);
    }

    @Override
    public Sheep clone() {
        return new Sheep(this);
    }
}
