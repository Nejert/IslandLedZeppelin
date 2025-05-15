package com.javarush.island.kazakov.entity.abstraction;

import com.javarush.island.kazakov.entity.plant.Plant;
import com.javarush.island.kazakov.map.Cell;

import java.util.List;
import java.util.Map;

public abstract class Herbivore extends Animal{
    protected Herbivore(double weight, int maxQuantity, int speed, double saturation) {
        super(weight, maxQuantity, speed, saturation);
    }

    @Override
    public void eat(Cell origin) {
        for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : origin.getVisitors().entrySet()) {
            List<Entity> value = entry.getValue();
            for (int i = 0; i < value.size();) {
                Entity entity = value.get(i);
                if (entity instanceof Plant plant) {
                    if (this.isHungry()) {
                        this.setCurrentSaturation(this.getCurrentSaturation() + plant.getWeight());
                        value.remove(i);
                    }
                } else {
                    i++;
                }
            }
        }
    }
    protected Herbivore(Herbivore h) {
        super(h);
    }
}
