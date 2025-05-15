package com.javarush.island.kazakov.util;

import lombok.Getter;

public enum Direction {
    UP("↑"),
    DOWN("↓"),
    LEFT("←"),
    RIGHT("→");
    @Getter
    private final String arrow;

    Direction(String arrow) {
        this.arrow = arrow;
    }
}
