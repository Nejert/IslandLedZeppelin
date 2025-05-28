package com.javarush.island.kazakov.view.swing;

import com.javarush.island.kazakov.config.Config;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.view.View;

import javax.swing.*;

public class SwingView implements View {
    private WindowManager windowManager;

    public SwingView(GameMap gameMap) {
        SwingUtilities.invokeLater(() ->
                windowManager = new WindowManager(
                        Config.get().getWindowTitle(),
                        gameMap,
                        Config.get().getWidth(),
                        Config.get().getHeight()
                ));
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
