package com.javarush.island.kazakov.view.swing;

import javax.swing.*;
import java.awt.*;

public class EntityGroupComponent extends JComponent {
    private final int capacity;

    public EntityGroupComponent(int capacity, boolean debug) {
        this.capacity = capacity;
        if (debug) {
            this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1), Color.GREEN));
        }
        this.setLayout(new EntityGroupLayout());
    }

    public EntityGroupComponent() {
        this(4, false);
    }

    public int getCapacity() {
        return capacity;
    }

    private class EntityGroupLayout implements LayoutManager {
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
            int size = 0;
            int correction = 0;
            int x = 0;
            int y = 0;
            Component[] components = getComponents();
            int count = components.length;
            if (count == 1) {
                if (parent.getWidth() < parent.getHeight()) {
                    size = parent.getWidth();
                    y = parent.getHeight() / 2 - size / 2;
                } else if (parent.getWidth() >= parent.getHeight()) {
                    size = parent.getHeight();
                    x = parent.getWidth() / 2 - size / 2;
                }
                ((EntityComponent) components[0]).setBounds(x, y, size);
            } else if (count > 1) {
                if (parent.getWidth() < parent.getHeight()) {
                    size = parent.getHeight() / count;
                    if (size > parent.getWidth()) {
                        size = parent.getWidth();
                        correction = (parent.getHeight() - size * count) / 2;
                    }
                    x = parent.getWidth() / 2 - size / 2;
                    for (int i = 0; i < count; i++) {
                        EntityComponent entity = (EntityComponent) components[i];
                        y = size * i + correction;
                        entity.setBounds(x, y, size);
                    }
                } else if (parent.getWidth() >= parent.getHeight()) {
                    size = parent.getWidth() / count;
                    if (size > parent.getHeight()) {
                        size = parent.getHeight();
                        correction = (parent.getWidth() - size * count) / 2;
                    }
                    y = parent.getHeight() / 2 - size / 2;
                    for (int i = 0; i < count; i++) {
                        EntityComponent entity = (EntityComponent) components[i];
                        x = size * i + correction;
                        entity.setBounds(x, y, size);
                    }
                }
            }

        }
    }
}
