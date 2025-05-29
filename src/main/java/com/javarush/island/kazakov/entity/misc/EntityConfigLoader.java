package com.javarush.island.kazakov.entity.misc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.island.kazakov.IslandException;
import com.javarush.island.kazakov.component.annotation.Metric;
import com.javarush.island.kazakov.entity.abstraction.Entity;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class EntityConfigLoader {

    private EntityConfigLoader() {
    }

    public static Map<EntityType, Entity> loadPrototypes(String path) {
        Map<EntityType, Entity> entities = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonEntityNode;
        try {
            jsonEntityNode = objectMapper.readTree(EntityConfigLoader.class.getResource(path));
        } catch (IOException e) {
            throw new IslandException("Unable to read entities config ", e);
        }
        for (Iterator<String> it = jsonEntityNode.fieldNames(); it.hasNext(); ) {
            String name = it.next();
            EntityType type = EntityType.valueOf(name.toUpperCase());
            JsonNode jsonEntity = jsonEntityNode.get(name);
            Class<? extends Entity> entityClass = type.getClazz();
            if (entityClass.isAnnotationPresent(Metric.class)) {
                Metric metric = entityClass.getAnnotation(Metric.class);
                Constructor<?> constructor = entityClass.getConstructors()[0];
                Entity entity;

                JsonNode jsonWeight = jsonEntity.get("weight");
                JsonNode jsonMaxQuantity = jsonEntity.get("maxQuantity");
                JsonNode jsonMaxSteps = jsonEntity.get("maxSteps");
                JsonNode jsonSaturation = jsonEntity.get("saturation");
                JsonNode jsonIcon = jsonEntity.get("icon");
                JsonNode jsonImageIcon = jsonEntity.get("imageIcon");

                double weight = jsonWeight != null ? jsonWeight.asDouble() : metric.weight();
                int maxQuantity = jsonMaxQuantity != null ? jsonMaxQuantity.asInt() : metric.maxQuantity();
                int maxSteps = jsonMaxSteps != null ? jsonMaxSteps.asInt() : metric.maxSteps();
                double saturation = jsonSaturation != null ? jsonSaturation.asDouble() : metric.saturation();
                String icon = jsonIcon != null ? jsonIcon.asText() : metric.icon();
                String imageIcon = jsonImageIcon != null ? jsonImageIcon.asText() : metric.imageIcon();
                try {
                    if (constructor.getParameterCount() > 2) {
                        entity = (Entity) constructor.newInstance(weight, maxQuantity, maxSteps, saturation);
                    } else {
                        entity = (Entity) constructor.newInstance(weight, maxQuantity);
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new IslandException("Unable to instantiate an object: ", e);
                }
                entity.setIcon(icon);
                entity.setImageIcon(loadImageIcon(imageIcon));
                entities.put(type, entity);
            }
        }
        return entities;
    }

    private static ImageIcon loadImageIcon(String path) {
        URL resource = Objects.requireNonNull(EntityFactory.class.getResource(path));
        return new ImageIcon(resource);
    }
}
