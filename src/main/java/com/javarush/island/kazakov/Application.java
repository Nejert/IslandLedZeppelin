package com.javarush.island.kazakov;

import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Animal;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.entity.misc.EntityFactory;
import com.javarush.island.kazakov.entity.misc.EntityType;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.map.Spawner;
import com.javarush.island.kazakov.view.console.ConsoleView;
import com.javarush.island.kazakov.system.EatSystem;
import com.javarush.island.kazakov.system.MoveSystem;
import com.javarush.island.kazakov.system.ReproduceSystem;
import com.javarush.island.kazakov.view.swing.WindowManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Application {
    private static boolean running = true;

    public static void main(String[] args) {
        GameMap gameMap = new GameMap(Default.ROWS, Default.COLS);
        Spawner spawner = new Spawner(gameMap);
        //spawner.initialRandomSpawn();
        //manual debug spawn
        gameMap.getCell(0,0).add(EntityFactory.newEntity(EntityType.WOLF));
        gameMap.getCell(0,0).add(EntityFactory.newEntity(EntityType.WOLF));
        gameMap.getCell(1,1).add(EntityFactory.newEntity(EntityType.RABBIT));
        gameMap.getCell(1,1).add(EntityFactory.newEntity(EntityType.RABBIT));
        gameMap.getCell(1,1).add(EntityFactory.newEntity(EntityType.RABBIT));
        gameMap.getCell(1,1).add(EntityFactory.newEntity(EntityType.RABBIT));
        //manual debug spawn

        MoveSystem moveSystem = new MoveSystem(gameMap);
        EatSystem eatSystem = new EatSystem(gameMap);
        ReproduceSystem reproduceSystem = new ReproduceSystem(gameMap);

        ConsoleView consoleView = new ConsoleView(gameMap);

        WindowManager windowManager = new WindowManager("Island", gameMap, 640, 480);


        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduledFuture = ses.scheduleWithFixedDelay(() -> {
            System.out.println(consoleView.display());
            System.out.println(consoleView.displayStats());
            spawner.spawnPlants();
            moveSystem.update();
            eatSystem.update();
            reproduceSystem.update();
            windowManager.update();
            checkSimulationEnd(gameMap.getCells());
        }, 0, 1, TimeUnit.SECONDS);
        try {
            scheduledFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        while (running) {
            try {
                ses.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new IslandException(e);
            }
        }
        ses.shutdown();

        /*while (running) {
            update(cells);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }*/
    }

    private static void checkSimulationEnd(Cell[][] cells) {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cells[y][x].getVisitors().entrySet()) {
                    for (Entity entity : entry.getValue()) {
                        if (entity instanceof Animal) {
                            running = true;
                            return;
                        }
                    }
                }
            }
        }
        running = false;
    }
}

