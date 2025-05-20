package com.javarush.island.kazakov.entity.misc;

import com.javarush.island.kazakov.entity.abstraction.Entity;

import java.util.Map;

public class EntityFactory {
    private static final Map<EntityType, Entity> entities;

    private EntityFactory() {
    }

    static {
        entities = EntityConfigLoader.loadPrototypes("/kazakov/entityConfig.json");
    }

    public static Entity newEntity(EntityType entityType) {
        return entities.get(entityType).clone();
    }
}
