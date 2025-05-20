package com.javarush.island.kazakov.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.javarush.island.kazakov.entity.misc.EntityType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SpawnProbability {
    private static final Map<EntityType, Integer> spawnProbability;

    private SpawnProbability() {
    }

    static {
        spawnProbability = new HashMap<>();
        spawnProbability.put(EntityType.WOLF, 30);
        spawnProbability.put(EntityType.BOA, 20);
        spawnProbability.put(EntityType.FOX, 40);
        spawnProbability.put(EntityType.BEAR, 10);
        spawnProbability.put(EntityType.EAGLE, 15);
        spawnProbability.put(EntityType.HORSE, 5);
        spawnProbability.put(EntityType.DEER, 5);
        spawnProbability.put(EntityType.RABBIT, 60);
        spawnProbability.put(EntityType.MOUSE, 60);
        spawnProbability.put(EntityType.GOAT, 30);
        spawnProbability.put(EntityType.SHEEP, 30);
        spawnProbability.put(EntityType.BOAR, 20);
        spawnProbability.put(EntityType.BUFFALO, 5);
        spawnProbability.put(EntityType.DUCK, 60);
        spawnProbability.put(EntityType.CATERPILLAR, 80);
        spawnProbability.put(EntityType.PLANT, 10);

        ObjectMapper objectMapper = new YAMLMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(SpawnProbability.class.getResource("/kazakov/InitialSpawnProbabilityConfig.yaml"));
            Iterator<String> it = jsonNode.fieldNames();
            while (it.hasNext()) {
                String key = it.next();
                int probability = jsonNode.get(key).asInt();
                spawnProbability.put(EntityType.valueOf(key.toUpperCase()), probability);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int get(EntityType type){
        return spawnProbability.get(type);
    }
}
