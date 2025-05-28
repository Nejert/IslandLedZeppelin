package com.javarush.island.kazakov.view.swing;

import lombok.Getter;
import com.javarush.island.kazakov.entity.abstraction.Entity;

import javax.swing.*;
import java.awt.*;

public class EntityComponent extends JComponent {
    @Getter
    private final Entity entity;
    private final ImageIcon icon;
    private int quantity;
    private final boolean debug;
    private final float imageFontRatio;
    private final Dimension iconSize;
    private final Point iconPosition;
    private final Dimension textSize;
    private final Point textPosition;
    private FontMetrics fontMetrics;
    private Font font;

    public EntityComponent(Entity entity, int quantity) {
        this(entity, quantity, false);
    }

    public EntityComponent(Entity entity, int quantity, boolean debug) {
        this.entity = entity;
        this.quantity = quantity;
        this.debug = debug;

        icon = entity.getImageIcon();
        int originalIconSize = Math.min(icon.getIconWidth(), icon.getIconHeight());
        iconSize = new Dimension(originalIconSize, originalIconSize);
        iconPosition = new Point(0, 0);

        textSize = new Dimension(0, 0);
        textPosition = new Point(0, 0);

        int initialFontSize = 30;
        font = new Font("Arial", Font.BOLD, initialFontSize);
        imageFontRatio = (float) initialFontSize / iconSize.width;
        fontMetrics = getFontMetrics(font);
        calcTextBounds();
        if (debug) {
            this.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    private void setSize(int size) {
        iconSize.width = iconSize.height = size;
        font = font.deriveFont(imageFontRatio * size);
        fontMetrics = getFontMetrics(font);
    }

    public void setBounds(int x, int y, int size) {
        setSize(size);
        setBounds(x, y, size, size);
    }

    private void calcTextBounds() {
        textSize.width = fontMetrics.stringWidth(String.valueOf(quantity));
        textSize.height = fontMetrics.getHeight();
        textPosition.x = iconPosition.x;
        textPosition.y = iconSize.height - textSize.height + fontMetrics.getAscent() + iconPosition.y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.drawImage(icon.getImage(), iconPosition.x, iconPosition.y, iconSize.width, iconSize.height, null);

        g2d.setFont(font);
        calcTextBounds();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(textPosition.x, textPosition.y - fontMetrics.getAscent(), textSize.width, textSize.height);
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.valueOf(quantity), textPosition.x, textPosition.y);

        if (debug) {
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.YELLOW);
            g2d.drawRect(iconPosition.x, iconPosition.y, iconSize.width, iconSize.height); //icon border
            g2d.setColor(Color.GREEN);
            g2d.drawRect(textPosition.x, textPosition.y - fontMetrics.getAscent(), textSize.width, textSize.height); //quantity border
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.RED);
            g2d.drawRect(iconPosition.x, iconPosition.y, iconSize.width, iconSize.height); //whole component border
        }
    }
}
