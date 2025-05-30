package com.javarush.island.kazakov.entity.abstraction;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public abstract class Entity implements Cloneable {
    private final static AtomicLong idCounter = new AtomicLong(1);
    private final long id = idCounter.incrementAndGet();

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

    @SuppressWarnings("CopyConstructorMissesField")
    protected Entity(Entity e) {
        this.weight = e.weight;
        this.maxQuantity = e.maxQuantity;
        icon = e.icon;
        imageIcon = e.imageIcon;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean setIcon(String icon) {
        if (this.icon == null) {
            this.icon = icon;
            return true;
        }
        return false;
    }

    @SuppressWarnings("UnusedReturnValue")
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
