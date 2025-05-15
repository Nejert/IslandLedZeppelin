package com.javarush.island.kazakov.entity.misc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.island.kazakov.IslandException;
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

public class ConfigLoader {
    private ConfigLoader() {
    }

    public static Map<EntityType, Entity> loadPrototypes(String path) {
        Map<EntityType, Entity> entities = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(ConfigLoader.class.getResource(path));
        } catch (IOException e) {
            throw new IslandException("Unable to read entities config ", e);
        }

        try {
            for (Iterator<String> it = jsonNode.fieldNames(); it.hasNext(); ) {
                String name = it.next();
                EntityType type = EntityType.valueOf(name.toUpperCase());
                JsonNode node = jsonNode.get(name);

                double weight;
                int maxQuantity;
                int maxSteps;
                double saturation;
                String icon;
                ImageIcon imageIcon;
                Entity e = null;
                Constructor<? extends Entity> constructor;

                if (type == EntityType.PLANT) {
                    constructor = type.getClazz().getConstructor(double.class, int.class);
                    weight = node.get("weight").asDouble();
                    maxQuantity = node.get("maxQuantity").asInt();
                    icon = node.get("icon").asText();
                    imageIcon = loadImageIcon(node.get("imageIcon").asText());
                    e = constructor.newInstance(weight, maxQuantity);
                } else {
                    constructor = type.getClazz().getConstructor(double.class, int.class, int.class, double.class);
                    weight = node.get("weight").asDouble();
                    maxQuantity = node.get("maxQuantity").asInt();
                    maxSteps = node.get("maxSteps").asInt();
                    saturation = node.get("saturation").asDouble();
                    icon = node.get("icon").asText();
                    imageIcon = loadImageIcon(node.get("imageIcon").asText());
                    e = constructor.newInstance(weight, maxQuantity, maxSteps, saturation);
                }
                e.setIcon(icon);
                e.setImageIcon(imageIcon);
                entities.put(type, e);
            }
        } catch (NoSuchMethodException e) {
            throw new IslandException("Unable to find constructor ", e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IslandException("Unable to instantiate an object", e);
        }
        return entities;
    }

    private static ImageIcon loadImageIcon(String path) {
        URL resource = Objects.requireNonNull(EntityFactory.class.getResource(path));
        return new ImageIcon(resource);
    }
}
