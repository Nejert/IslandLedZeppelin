package com.javarush.island.kazakov.util;

import lombok.Getter;

@Getter
public enum Direction {
    UP("↑"),
    DOWN("↓"),
    LEFT("←"),
    RIGHT("→");
    private final String arrow;

    Direction(String arrow) {
        this.arrow = arrow;
    }
}
