package com.javarush.island.kazakov.map;

import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.util.Location;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Cell {
    private final Map<Class<? extends Entity>, List<Entity>> visitors;
    private final Location location;

    public Cell(Location location) {
        this.visitors = new HashMap<>();
        this.location = location;
    }

    public List<Entity> add(Entity entity) {
        List<Entity> entities = visitors.get(entity.getClass());
        if (entities == null) {
            entities = new ArrayList<>();
        }
        entities.add(entity);
        return visitors.put(entity.getClass(), entities);
    }

    public boolean remove(Entity entity) {
        List<Entity> entities = visitors.get(entity.getClass());
        if (entities == null || entities.isEmpty()) {
            return false;
        }
        return visitors.get(entity.getClass()).remove(entity);
    }

    public List<Entity> get(Class<? extends Entity> entityClass) {
        return visitors.get(entityClass);
    }



    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : visitors.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                stringBuilder.append(entry.getValue().get(0).getIcon()).append(entry.getValue().size());
            }
        }
        return stringBuilder.isEmpty() ? "E" : stringBuilder.toString();
    }
}
