package com.javarush.island.kazakov.util;

import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    private Util() {
    }

    public static Map<Class<? extends Entity>, Pair<String, Integer>> gatherMapInfo(GameMap gameMap) {
        Map<Class<? extends Entity>, Pair<String, Integer>> stat = new HashMap<>();
        for (int y = 0; y < gameMap.getRows(); y++) {
            for (int x = 0; x < gameMap.getCols(); x++) {
                Cell cell = gameMap.getCell(y, x);
                cell.lock();
                try {
                    for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cell.getVisitors().entrySet()) {
                        if (!entry.getValue().isEmpty()) {
                            String name = entry.getValue().get(0).getIcon() + " " + entry.getKey().getSimpleName();
                            Pair<String, Integer> nameQuant;
                            if (stat.get(entry.getKey()) == null) {
                                nameQuant = new Pair<>(name, entry.getValue().size());
                            } else {
                                int sum = stat.get(entry.getKey()).getValue() + entry.getValue().size();
                                nameQuant = new Pair<>(name, sum);
                            }
                            stat.put(entry.getKey(), nameQuant);
                        }
                    }
                } finally {
                    cell.unlock();
                }
            }
        }
        return stat;
    }
}
