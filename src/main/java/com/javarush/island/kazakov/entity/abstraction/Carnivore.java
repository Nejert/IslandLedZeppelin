package com.javarush.island.kazakov.entity.abstraction;

import com.javarush.island.kazakov.entity.plant.Plant;
import com.javarush.island.kazakov.map.Cell;

import java.util.List;
import java.util.Map;

public abstract class Carnivore extends Animal {
    protected Carnivore(double weight, int maxQuantity, int maxSteps, double saturation) {
        super(weight, maxQuantity, maxSteps, saturation);
    }

    @Override
    public void eat(Cell origin) {
        for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : origin.getVisitors().entrySet()) {
            List<Entity> value = entry.getValue();
            for (int i = 0; i < value.size();) {
                Entity entity = value.get(i);
                if (entity instanceof Herbivore h) {
                    this.setCurrentSaturation(this.getCurrentSaturation() + h.getWeight());
                    value.remove(i);
                } else {
                    i++;
                }
            }
        }
    }

    protected Carnivore(Carnivore c) {
        super(c);
    }
}
