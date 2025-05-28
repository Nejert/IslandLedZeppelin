package com.javarush.island.kazakov;

import com.javarush.island.kazakov.config.Config;
import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.system.AbstractSystem;
import com.javarush.island.kazakov.view.View;
import com.javarush.island.kazakov.view.console.ConsoleView;
import com.javarush.island.kazakov.system.EatSystem;
import com.javarush.island.kazakov.system.MoveSystem;
import com.javarush.island.kazakov.system.ReproduceSystem;
import com.javarush.island.kazakov.view.swing.SwingView;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        Config cfg = Config.get();
        GameMap gameMap = new GameMap(cfg.getRows(), cfg.getCols());
        List<AbstractSystem> systems = List.of(
                new MoveSystem(gameMap),
                new EatSystem(gameMap),
                new ReproduceSystem(gameMap)
        );
        View consoleView = new ConsoleView(gameMap);
        View swingView = new SwingView(gameMap);
        Game game = new Game(gameMap, systems, consoleView, swingView);
        game.start();
    }
}

