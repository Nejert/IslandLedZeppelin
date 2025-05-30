package com.javarush.island.kazakov.view.swing;

import com.javarush.island.kazakov.map.GameMap;

import javax.swing.*;

public class WindowManager extends JFrame {
    private final int width;
    private final int height;
    private final GridComponent gridComponent;

    public WindowManager(String title, GameMap gameMap, int width, int height) {
        this(title, gameMap, width, height, false);
    }

    public WindowManager(String title, GameMap gameMap, int width, int height, boolean debug) {
        super(title);
        this.width = width;
        this.height = height;
        frameInit();
        gridComponent = new GridComponent(gameMap, debug);
        this.add(gridComponent);
        this.setVisible(true);
    }

    @Override
    protected void frameInit() {
        super.frameInit();
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void update(){
        gridComponent.update();
        this.revalidate();
    }
}
