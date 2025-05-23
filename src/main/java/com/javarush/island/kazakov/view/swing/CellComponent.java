package com.javarush.island.kazakov.view.swing;

import com.javarush.island.kazakov.entity.abstraction.Entity;

import javax.swing.*;
import java.awt.*;

public class CellComponent extends JComponent {
    private EntityGroupComponent currentGroup;
    private final int groupCapacity = 5;
    private final boolean debug;

    public CellComponent(boolean debug) {
        this.debug = debug;
        this.currentGroup = new EntityGroupComponent(groupCapacity, debug);
        this.add(currentGroup, 0);
        this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1), Color.LIGHT_GRAY));
        this.setLayout(new CellLayout());

    }

    public CellComponent() {
        this(false);
    }

    @Override
    public Component add(Component comp) {
        if (currentGroup.getComponents().length >= groupCapacity) {
            currentGroup = new EntityGroupComponent(groupCapacity, debug);
            this.add(currentGroup, this.getComponentCount());
        }
        return currentGroup.add(comp);
    }

    public void clear(){
        this.removeAll();
        this.currentGroup = new EntityGroupComponent(groupCapacity, debug);
        this.add(currentGroup, 0);
    }

    public EntityComponent getEntityComponent(Class<? extends Entity> e){
        for (Component component : getComponents()) {
            EntityGroupComponent group = (EntityGroupComponent) component;
            for (Component groupComponent : group.getComponents()) {
                EntityComponent entityComponent = (EntityComponent) groupComponent;
                if (entityComponent.getEntity().getClass() == e){
                    return entityComponent;
                }
            }
        }
        return null;
    }

    private class CellLayout implements LayoutManager {

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
