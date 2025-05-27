package com.javarush.island.kazakov.view.swing;

import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.view.View;

import javax.swing.*;

public class SwingView implements View {
    private final GameMap gameMap;
    private WindowManager windowManager;

    public SwingView(GameMap gameMap) {
        this.gameMap = gameMap;
        SwingUtilities.invokeLater(() -> windowManager = new WindowManager("Island", gameMap, 640, 480));
    }

    @Override
    public void update() {
        windowManager.update();
    }

    @Override
    public void exit() {
        windowManager.setVisible(false);
        windowManager.dispose();
    }
}
