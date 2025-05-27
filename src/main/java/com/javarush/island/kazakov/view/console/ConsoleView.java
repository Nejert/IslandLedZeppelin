package com.javarush.island.kazakov.view.console;

import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.view.View;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleView implements View {
    private final GameMap gameMap;

    public ConsoleView(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < Default.ROWS; y++) {
            sb.append("|");
            for (int x = 0; x < Default.COLS; x++) {
                sb.append(gameMap.getCell(y, x)).append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String displayStats() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Class<? extends Entity>, Pair<String, Integer>> entry : gatherInfo().entrySet()) {
            sb.append(entry.getValue().getKey())
                    .append(":\t")
                    .append(entry.getValue().getValue())
                    .append("\n");
        }
        return sb.toString();
    }

    private Map<Class<? extends Entity>, Pair<String, Integer>> gatherInfo() {
        Map<Class<? extends Entity>, Pair<String, Integer>> stat = new HashMap<>();
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
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

    @Override
    public void update() {
        System.out.println(display());
        System.out.println(displayStats());
    }

    @Override
    public void exit() {
        System.out.println("Application has terminated");
    }
}
