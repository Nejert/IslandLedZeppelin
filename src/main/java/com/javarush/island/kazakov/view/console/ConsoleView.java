package com.javarush.island.kazakov.view.console;

import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.util.Util;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.view.View;
import javafx.util.Pair;

import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleView implements View {
    private final GameMap gameMap;

    public ConsoleView(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < gameMap.getRows(); y++) {
            sb.append("|");
            for (int x = 0; x < gameMap.getCols(); x++) {
                sb.append(gameMap.getCell(y, x)).append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String displayStats() {
        String s = Util.gatherMapInfo(gameMap).values().stream()
                .map(stringIntegerPair -> stringIntegerPair.getKey() + ":\t" + stringIntegerPair.getValue() + "\n")
                .collect(Collectors.joining());
        return s;
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
