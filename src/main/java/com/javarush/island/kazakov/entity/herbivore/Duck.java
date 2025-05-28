package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Herbivore;

@Metric(
        weight = 1,
        maxQuantity = 200,
        maxSteps = 4,
        saturation = 0.15,
        icon = Default.DUCK_ICON,
        imageIcon = Default.DUCK_IMAGE
)
public class Duck extends Herbivore {
    public Duck(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Duck(Duck duck) {
        super(duck);
    }

    @Override
    public Duck clone() {
        return new Duck(this);
    }
}
