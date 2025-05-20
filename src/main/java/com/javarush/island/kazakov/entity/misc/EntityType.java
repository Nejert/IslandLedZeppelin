package com.javarush.island.kazakov.entity.misc;

import com.fasterxml.jackson.annotation.JsonValue;
import com.javarush.island.kazakov.IslandException;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.entity.carnivore.*;
import com.javarush.island.kazakov.entity.herbivore.*;
import com.javarush.island.kazakov.entity.plant.Plant;
import lombok.Getter;

@Getter
public enum EntityType {
    WOLF(Wolf.class),
    BOA(Boa.class),
    FOX(Fox.class),
    BEAR(Bear.class),
    EAGLE(Eagle.class),
    HORSE(Horse.class),
    DEER(Deer.class),
    RABBIT(Rabbit.class),
    MOUSE(Mouse.class),
    GOAT(Goat.class),
    SHEEP(Sheep.class),
    BOAR(Boar.class),
    BUFFALO(Buffalo.class),
    DUCK(Duck.class),
    CATERPILLAR(Caterpillar.class),
    PLANT(Plant.class);

    private final Class<? extends Entity> clazz;

    EntityType(Class<? extends Entity> clazz) {
        this.clazz = clazz;
    }

    public static EntityType valueOf(Class<? extends Entity> clazz) {
        for (EntityType entityType : EntityType.values()) {
            if (clazz == entityType.clazz) {
                return entityType;
            }
        }
        throw new IslandException(new IllegalArgumentException("No EntityType found for " + clazz));
    }

    @JsonValue
    public String toLowerCase() {
        return name().toLowerCase();
    }
}
