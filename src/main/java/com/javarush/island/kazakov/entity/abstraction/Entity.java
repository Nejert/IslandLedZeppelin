package com.javarush.island.kazakov.entity.abstraction;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
public abstract class Entity implements Cloneable {
    private final double weight;
    private final int maxQuantity;
    private String icon;
    private ImageIcon imageIcon;
    @Setter
    private boolean moved;
    @Setter
    private boolean hungry;
    @Setter
    private boolean dead;
    @Setter
    private boolean parent;

    protected Entity(double weight, int maxQuantity) {
        this.weight = weight;
        this.maxQuantity = maxQuantity;
        icon = null;
        imageIcon = null;
    }

    protected Entity(Entity e) {
        this.weight = e.weight;
        this.maxQuantity = e.maxQuantity;
        icon = e.icon;
        imageIcon = e.imageIcon;
    }

    public boolean setIcon(String icon) {
        if (this.icon == null) {
            this.icon = icon;
            return true;
        }
        return false;
    }

    public boolean setImageIcon(ImageIcon imageIcon) {
        if (this.imageIcon == null) {
            this.imageIcon = imageIcon;
            return true;
        }
        return false;
    }

    @Override
    public abstract Entity clone();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + icon;
    }


}
