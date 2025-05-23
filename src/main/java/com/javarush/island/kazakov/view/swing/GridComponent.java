package com.javarush.island.kazakov.view.swing;

import com.javarush.island.kazakov.config.Default;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.map.GameMap;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Map;
import java.util.List;

public class GridComponent extends JComponent {
    private final boolean debug;
    private final int strokeWidth;
    private final CellComponent[][] cellComponents;
    private final Color backgroundColor;
    private final Color linesColor;
    private final GameMap gameMap;

    public GridComponent(GameMap gameMap) {
        this(gameMap, false);
    }

    public GridComponent(GameMap gameMap, boolean debug) {
        strokeWidth = 2;
        backgroundColor = Color.GRAY;
        linesColor = Color.LIGHT_GRAY;
        this.debug = debug;
        this.gameMap = gameMap;
        cellComponents = new CellComponent[Default.ROWS][Default.COLS];
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                CellComponent cellComponent = new CellComponent(debug);
                cellComponents[y][x] = cellComponent;
                this.add(cellComponent);
            }
        }
        if (debug) {
            Border lineBorder = BorderFactory.createLineBorder(Color.ORANGE, 2);
            this.setBorder(lineBorder);
        }
        this.setLayout(new LocalGridLayout());
    }

    public Component add(Component comp, int x, int y) {
        return cellComponents[y][x].add(comp);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    public void update() {
        for (int y = 0; y < Default.ROWS; y++) {
            for (int x = 0; x < Default.COLS; x++) {
                Map<Class<? extends Entity>, List<Entity>> visitors = gameMap.getCell(y, x).getVisitors();
                CellComponent cellComponent = cellComponents[y][x];
                cellComponent.clear();
                for (Map.Entry<Class<? extends Entity>, List<Entity>> entry : visitors.entrySet()) {
                    if (!entry.getValue().isEmpty()) {
                        EntityComponent entityComponent = new EntityComponent(entry.getValue().get(0), entry.getValue().size());
                        cellComponent.add(entityComponent);
                    }
                }
            }
        }
    }

    private class LocalGridLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return null;
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return null;
        }

        @Override
        public void layoutContainer(Container parent) {
            Component[] components = parent.getComponents();
            int cellWidth = Math.round((float) parent.getWidth() / Default.COLS);
            int cellHeight = Math.round((float) parent.getHeight() / Default.ROWS);
            int col = 0, row = 0;
            for (Component component : parent.getComponents()) {
                component.setBounds(cellWidth * col, cellHeight * row, cellWidth, cellHeight);
                col++;
                if (col >= Default.COLS) {
                    col = 0;
                    row++;
                }
            }
        }
    }
}
