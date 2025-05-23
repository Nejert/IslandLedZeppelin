package com.javarush.island.kazakov;

import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.system.AbstractSystem;
import com.javarush.island.kazakov.view.View;
import com.javarush.island.kazakov.view.console.ConsoleView;
import com.javarush.island.kazakov.system.EatSystem;
import com.javarush.island.kazakov.system.MoveSystem;
import com.javarush.island.kazakov.system.ReproduceSystem;
import com.javarush.island.kazakov.view.swing.SwingView;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        GameMap gameMap = new GameMap(Default.ROWS, Default.COLS);
        List<AbstractSystem> systems = new ArrayList<>(){{
            add(new MoveSystem(gameMap));
            add(new EatSystem(gameMap));
            add(new ReproduceSystem(gameMap));
        }};
        View consoleView = new ConsoleView(gameMap);
        View swingView = new SwingView(gameMap);
        Game game = new Game(gameMap, systems, consoleView, swingView);
        game.start();
    }
}

