package com.javarush.island.kazakov.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.javarush.island.kazakov.IslandException;
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
        spawnProbability.put(EntityType.WOLF, Default.WOLF_SPAWN);
        spawnProbability.put(EntityType.BOA, Default.BOA_SPAWN);
        spawnProbability.put(EntityType.FOX, Default.FOX_SPAWN);
        spawnProbability.put(EntityType.BEAR, Default.BEAR_SPAWN);
        spawnProbability.put(EntityType.EAGLE, Default.EAGLE_SPAWN);
        spawnProbability.put(EntityType.HORSE, Default.HORSE_SPAWN);
        spawnProbability.put(EntityType.DEER, Default.DEER_SPAWN);
        spawnProbability.put(EntityType.RABBIT, Default.RABBIT_SPAWN);
        spawnProbability.put(EntityType.MOUSE, Default.MOUSE_SPAWN);
        spawnProbability.put(EntityType.GOAT, Default.GOAT_SPAWN);
        spawnProbability.put(EntityType.SHEEP, Default.SHEEP_SPAWN);
        spawnProbability.put(EntityType.BOAR, Default.BOAR_SPAWN);
        spawnProbability.put(EntityType.BUFFALO, Default.BUFFALO_SPAWN);
        spawnProbability.put(EntityType.DUCK, Default.DUCK_SPAWN);
        spawnProbability.put(EntityType.CATERPILLAR, Default.CATERPILLAR_SPAWN);
        spawnProbability.put(EntityType.PLANT, Default.PLANT_SPAWN);

        ObjectMapper objectMapper = new YAMLMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(SpawnProbability.class.getResource(Config.get().getSpawnConfig()));
            Iterator<String> it = jsonNode.fieldNames();
            while (it.hasNext()) {
                String key = it.next();
                int probability = jsonNode.get(key).asInt();
                spawnProbability.put(EntityType.valueOf(key.toUpperCase()), probability);
            }
        } catch (IOException e) {
            throw new IslandException("Unable to read spawn config ", e);
        }
    }

    public static int get(EntityType type) {
        return spawnProbability.get(type);
    }
}
