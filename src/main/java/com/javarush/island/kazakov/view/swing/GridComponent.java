package com.javarush.island.kazakov.view.swing;

import com.javarush.island.kazakov.config.Config;
import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.map.GameMap;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Map;
import java.util.List;

public class GridComponent extends JComponent {
    private final boolean debug;
    private final CellComponent[][] cellComponents;
    private final Color backgroundColor;
    private final GameMap gameMap;
    private final StatisticsComponent statisticsComponent;

    @SuppressWarnings("unused")
    public GridComponent(GameMap gameMap) {
        this(gameMap, false);
    }

    public GridComponent(GameMap gameMap, boolean debug) {
        backgroundColor = Config.get().getBackgroundColor();
        this.debug = debug;
        this.gameMap = gameMap;
        statisticsComponent = new StatisticsComponent(gameMap, Config.get().getStatWidthPercentage());
        statisticsComponent.setBackground(backgroundColor);
        this.add(statisticsComponent);
        cellComponents = new CellComponent[gameMap.getRows()][gameMap.getCols()];
        for (int y = 0; y < gameMap.getRows(); y++) {
            for (int x = 0; x < gameMap.getCols(); x++) {
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

    @SuppressWarnings("unused")
    public Component add(Component comp, int x, int y) {
        return cellComponents[y][x].add(comp);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
    public void update() {
        for (int y = 0; y < gameMap.getRows(); y++) {
            for (int x = 0; x < gameMap.getCols(); x++) {
                Map<Class<? extends Entity>, List<Entity>> visitors = gameMap.getCell(y, x).getVisitors();
                CellComponent cellComponent = cellComponents[y][x];
                cellComponent.clear();
                visitors.values().stream()
                        .filter(entities -> !entities.isEmpty())
                        .map(entities -> new EntityComponent(entities.get(0), entities.size(), debug))
                        .forEach(cellComponent::add);
            }
        }
        statisticsComponent.update();
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
            int statWidth = Math.round((float) getWidth() / 100 * statisticsComponent.getPercentWidth());
            int cellWidth = Math.round((float) (parent.getWidth() - statWidth) / gameMap.getCols());
            int cellHeight = Math.round((float) parent.getHeight() / gameMap.getRows());
            components[0].setBounds(0, 0, statWidth, parent.getHeight());
            int col = 0, row = 0;
            for (int i = 1; i < components.length; i++) {
                components[i].setBounds(cellWidth * col+statWidth, cellHeight * row, cellWidth, cellHeight);
                col++;
                if (col >= gameMap.getCols()) {
                    col = 0;
                    row++;
                }
            }
        }
    }
}
