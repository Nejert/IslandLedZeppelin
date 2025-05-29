package com.javarush.island.kazakov.view.swing;

import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.entity.misc.EntityFactory;
import com.javarush.island.kazakov.entity.misc.EntityType;
import com.javarush.island.kazakov.map.GameMap;
import com.javarush.island.kazakov.util.Util;
import javafx.util.Pair;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StatisticsComponent extends JComponent {
    private final GameMap gameMap;
    @Getter
    private final int percentWidth;
    private Color backgroundColor;

    public StatisticsComponent(GameMap gameMap, int percentWidth) {
        this.gameMap = gameMap;
        this.percentWidth = percentWidth;
        this.setLayout(new StatLayout());
        this.setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void setBackground(Color bg) {
        backgroundColor = bg;
    }

    public void update() {
        this.removeAll();
        Map<Class<? extends Entity>, Pair<String, Integer>> infoMap = Util.gatherMapInfo(gameMap);
        infoMap.forEach((key, value) -> {
            Entity entity = EntityFactory.newEntity(EntityType.valueOf(key));
            EntityComponent component = new EntityComponent(entity, value.getValue());
            this.add(component);
        });
        this.revalidate();
        this.repaint();
    }

    private static class StatLayout implements LayoutManager {

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
            int count = components.length;
            int size = parent.getWidth() * count > parent.getHeight() ?
                    Math.round((float) parent.getHeight() / count) :
                    parent.getWidth();
            for (int i = 0; i < count; i++) {
                EntityComponent entity = (EntityComponent) components[i];
                int x = parent.getWidth() / 2 - size / 2;
                int y = size * i;
                entity.setBounds(x, y, size);
            }
        }
    }
}
