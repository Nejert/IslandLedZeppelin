package com.javarush.island.kazakov.map;

import com.javarush.island.kazakov.config.SpawnProbability;
import com.javarush.island.kazakov.entity.misc.EntityFactory;
import com.javarush.island.kazakov.entity.misc.EntityType;
import com.javarush.island.kazakov.util.Rnd;

public class Spawner {
    private final GameMap gameMap;

    public Spawner(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    private void spawn(int probability, EntityType type, int quantity) {
        for (int y = 0; y < gameMap.getRows(); y++) {
            for (int x = 0; x < gameMap.getCols(); x++) {
                for (int k = 0; k < Rnd.random(quantity); k++) {
                    if (Rnd.get(probability)) {
                        gameMap.getCell(y, x).add(EntityFactory.newEntity(type));
                    }
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public void initialRandomSpawn() {
        for (EntityType type : EntityType.values()) {
            spawn(Rnd.random(0, 100), type, EntityFactory.newEntity(type).getMaxQuantity());
        }
    }

    public void initialSpawn() {
        for (EntityType type : EntityType.values()) {
            spawn(SpawnProbability.get(type), type, EntityFactory.newEntity(type).getMaxQuantity());
        }
    }

    public void cyclicSpawnPlants() {
        spawn(SpawnProbability.get(EntityType.PLANT), EntityType.PLANT, 2);
    }
}
