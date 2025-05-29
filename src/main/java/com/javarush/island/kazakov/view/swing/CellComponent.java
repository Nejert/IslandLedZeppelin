package com.javarush.island.kazakov.view.swing;

import com.javarush.island.kazakov.config.Config;

import javax.swing.*;
import java.awt.*;

public class CellComponent extends JComponent {
    private EntityGroupComponent currentGroup;
    private final boolean debug;

    public CellComponent(boolean debug) {
        this.debug = debug;
        this.currentGroup = new EntityGroupComponent(debug);
        this.add(currentGroup, 0);
        this.setBorder(BorderFactory.createStrokeBorder(
                new BasicStroke(Config.get().getGridStrokeWidth()), Config.get().getGridLinesColor()));
        this.setLayout(new CellLayout());

    }

    @SuppressWarnings("unused")
    public CellComponent() {
        this(false);
    }

    @Override
    public Component add(Component comp) {
        if (currentGroup.getComponents().length >= Config.get().getEntityGroupCapacity()) {
            currentGroup = new EntityGroupComponent(debug);
            this.add(currentGroup, this.getComponentCount());
        }
        return currentGroup.add(comp);
    }

    public void clear() {
        this.removeAll();
        this.currentGroup = new EntityGroupComponent(debug);
        this.add(currentGroup, 0);
    }

    private static class CellLayout implements LayoutManager {

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
            for (int i = 0; i < count; i++) {
                EntityGroupComponent group = (EntityGroupComponent) components[i];
                if (parent.getWidth() > parent.getHeight()) {
                    group.setBounds(0, parent.getHeight() / count * i, parent.getWidth(), parent.getHeight() / count);
                } else {
                    group.setBounds(parent.getWidth() / count * i, 0, parent.getWidth() / count, parent.getHeight());
                }
            }
        }
    }
}
