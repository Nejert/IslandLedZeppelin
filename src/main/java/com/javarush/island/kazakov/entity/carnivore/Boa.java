package com.javarush.island.kazakov.entity.carnivore;

import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Carnivore;

@Metric(
        weight = 15,
        maxQuantity = 30,
        maxSteps = 1,
        saturation = 3,
        icon = Default.BOA_ICON,
        imageIcon = Default.BOA_IMAGE
)
public class Boa extends Carnivore {
    public Boa(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    private Boa(Boa boa) {
        super(boa);
    }

    @Override
    public Boa clone() {
        return new Boa(this);
    }
}
