package com.javarush.island.kazakov.entity.misc;

import com.javarush.island.kazakov.entity.abstraction.Entity;

import java.util.HashMap;
import java.util.Map;

public class EatingProbability {
    private EatingProbability() {
    }
/*
      ┌──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┐
      │ Wolf │ Boa  │ Fox  │ Bear │Eagle │Horse │ Deer │Rabbit│Mouse │ Goat │Sheep │ Boar │ Buff │ Duck │Cater │Plant │
      ├──────┼──────┼──────┼──────┼──────┼──────┼──────┼──────┼──────┼──────┼──────┼──────┼──────┼──────┼──────┼──────┤
Wolf     0      0      0      0      0      10     15     60     80     60     70     15     10     40     0      0
Boa      0      0      15     0      0      0      0      20     40     0      0      0      0      10     0      0
Fox      0      0      0      0      0      0      0      70     90     0      0      0      0      60     40     0
Bear     0      80     0      0      0      40     80     80     90     70     70     50     20     10     0      0
Eagle    0      0      10     0      0      0      0      90     90     0      0      0      0      80     0      0
Horse    0      0      0      0      0      0      0      0      0      0      0      0      0      0      0      100
Deer     0      0      0      0      0      0      0      0      0      0      0      0      0      0      0      100
Rabbit   0      0      0      0      0      0      0      0      0      0      0      0      0      0      0      100
Mouse    0      0      0      0      0      0      0      0      0      0      0      0      0      0      90     100
Goat     0      0      0      0      0      0      0      0      0      0      0      0      0      0      0      100
Sheep    0      0      0      0      0      0      0      0      0      0      0      0      0      0      0      100
Boar     0      0      0      0      0      0      0      0      50     0      0      0      0      0      90     100
Buff     0      0      0      0      0      0      0      0      0      0      0      0      0      0      0      100
Duck     0      0      0      0      0      0      0      0      0      0      0      0      0      0      90     100
Cater    0      0      0      0      0      0      0      0      0      0      0      0      0      0      0      100
*/

    //stolen eating probability table
    private static final int[][] eatingProbabilityTable = {
            {0, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0},  // Wolf
            {0, 0, 15, 0, 0, 0, 0, 20, 40, 0, 0, 0, 0, 10, 0, 0},       // Boa
            {0, 0, 0, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0},       // Fox
            {0, 80, 0, 0, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0}, // Bear
            {0, 0, 10, 0, 0, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0},       // Eagle
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},         // Horse
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},         // Deer
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},         // Rabbit
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},        // Mouse
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},         // Goat
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},         // Sheep
            {0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 90, 100},       // Boar
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},         // Buff
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},        // Duck
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},         // Cater
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},           // Plant
    };
    private static final Map<EntityType, Map<EntityType, Integer>> eatingProbabilityMap = new HashMap<>();

    static {
        EntityType[] type = EntityType.values();
        for (int i = 0; i < type.length; i++) {
            eatingProbabilityMap.put(type[i], new HashMap<>());
            for (int j = 0; j < type.length; j++) {
                eatingProbabilityMap.get(type[i]).put(type[j], eatingProbabilityTable[i][j]);
            }
        }
    }

    public static int getProbability(Entity eater, Entity prey) {
        EntityType eaterType = EntityType.valueOf(eater.getClass());
        EntityType prayType = EntityType.valueOf(prey.getClass());
        return eatingProbabilityMap.get(eaterType).get(prayType);
    }
}
