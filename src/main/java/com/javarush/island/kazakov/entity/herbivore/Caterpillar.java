package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Herbivore;

@Metric(
        weight = 0.01,
        maxQuantity = 1000,
        icon = Default.CATERPILLAR_ICON,
        imageIcon = Default.CATERPILLAR_IMAGE
)
public class Caterpillar extends Herbivore {
    public Caterpillar(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Caterpillar(Caterpillar caterpillar) {
        super(caterpillar);
    }

    @Override
    public Caterpillar clone() {
        return new Caterpillar(this);
    }
}
