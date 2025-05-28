package com.javarush.island.kazakov.entity.herbivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Herbivore;

@Metric(
        weight = 400,
        maxQuantity = 50,
        maxSteps = 2,
        saturation = 50,
        icon = Default.BOAR_ICON,
        imageIcon = Default.BOAR_IMAGE
)
public class Boar extends Herbivore {
    public Boar(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    private Boar(Boar boar) {
        super(boar);
    }

    @Override
    public Boar clone() {
        return new Boar(this);
    }
}
