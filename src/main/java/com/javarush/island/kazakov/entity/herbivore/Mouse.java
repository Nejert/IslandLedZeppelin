package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Herbivore;

@Metric(
        weight = 0.05,
        maxQuantity = 500,
        maxSteps = 1,
        saturation = 0.01,
        icon = Default.MOUSE_ICON,
        imageIcon = Default.MOUSE_IMAGE
)
public class Mouse extends Herbivore {
    public Mouse(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Mouse(Mouse mouse) {
        super(mouse);
    }

    @Override
    public Mouse clone() {
        return new Mouse(this);
    }
}
