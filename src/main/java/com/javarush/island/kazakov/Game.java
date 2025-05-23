package com.javarush.island.kazakov;

import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Animal;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.entity.misc.EntityFactory;
import com.javarush.island.kazakov.entity.misc.EntityType;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.map.Spawner;
import com.javarush.island.kazakov.system.AbstractSystem;
import com.javarush.island.kazakov.view.View;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Game extends Thread {
    public static final int CORE_AMT = Runtime.getRuntime().availableProcessors();
    private final GameMap gameMap;
    private final List<AbstractSystem> systems;
    private final List<View> view;
    private boolean running;
    private ScheduledExecutorService scheduledExecutor;
    private ExecutorService executor;
    private Spawner spawner;

    public Game(GameMap gameMap, List<AbstractSystem> systems, View... view) {
        this.gameMap = gameMap;
        this.systems = systems;
        this.view = List.of(view);
    }

    @Override
    public void run() {
        initialize();
        executor = Executors.newFixedThreadPool(CORE_AMT);
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        running = true;
        scheduledExecutor.scheduleAtFixedRate(this::update, 0, 1000, TimeUnit.MILLISECONDS);
    }

        private void initialize() {
        spawner = new Spawner(gameMap);
        spawner.initialSpawn();
    }
//    private void initialize() {
//        spawner = new Spawner(gameMap);
//        gameMap.getCell(0,0).add(EntityFactory.newEntity(EntityType.WOLF));
//        gameMap.getCell(1,1).add(EntityFactory.newEntity(EntityType.RABBIT));
//    }

    private void update() {
        executor.submit(() -> spawner.spawnPlants());
        systems.forEach(s -> executor.submit(s::update));
        view.forEach(v -> executor.submit(v::update));
        executor.submit(this::checkSimulationEnd);
        if (!running){
            executor.shutdown();
            scheduledExecutor.shutdown();
        }
    }

    private void checkSimulationEnd() {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                Cell cell = gameMap.getCell(y, x);
                synchronized (cell) {
                    for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cell.getVisitors().entrySet()) {
                        for (Entity entity : entry.getValue()) {
                            if (entity instanceof Animal) {
                                running = true;
                                return;
                            }
                        }
                    }
                }
            }
        }
        running = false;
    }
}
