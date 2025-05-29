package com.javarush.island.kazakov;

import com.javarush.island.kazakov.config.Config;
import com.javarush.island.kazakov.entity.abstraction.Animal;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.map.Cell;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.map.Spawner;
import com.javarush.island.kazakov.system.AbstractSystem;
import com.javarush.island.kazakov.view.View;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Game extends Thread {
    private static final int CORE_AMT = Runtime.getRuntime().availableProcessors();
    private final GameMap gameMap;
    private final List<AbstractSystem> systems;
    private final List<View> view;
    private final ScheduledExecutorService scheduledExecutor;
    private final ExecutorService executor;
    private final Spawner spawner;
    private boolean running;

    public Game(GameMap gameMap, List<AbstractSystem> systems, View... view) {
        this.gameMap = gameMap;
        this.systems = systems;
        this.view = List.of(view);
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        executor = Executors.newFixedThreadPool(CORE_AMT);
        spawner = new Spawner(gameMap);
    }

    @Override
    public void run() {
        spawner.initialSpawn();
        running = true;
        scheduledExecutor.scheduleAtFixedRate(this::update, 0, Config.get().getTickPeriodMs(), TimeUnit.MILLISECONDS);
    }

    private void update() {
        executor.submit(spawner::cyclicSpawnPlants);
        systems.forEach(s -> executor.submit(s::update));
        view.forEach(v -> executor.submit(v::update));
        executor.submit(this::checkSimulationEnd);
        if (!running) {
            executor.shutdown();
            scheduledExecutor.shutdown();
            view.forEach(View::exit);
        }
    }

    private void checkSimulationEnd() {
        for (int y = 0; y < Config.get().getRows(); y++) {
            for (int x = 0; x < Config.get().getCols(); x++) {
                Cell cell = gameMap.getCell(y, x);
                cell.lock();
                try {
                    for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : cell.getVisitors().entrySet()) {
                        for (Entity entity : entry.getValue()) {
                            if (entity instanceof Animal) {
                                running = true;
                                return;
                            }
                        }
                    }
                } finally {
                    cell.unlock();
                }
            }
        }
        running = false;
    }
}
