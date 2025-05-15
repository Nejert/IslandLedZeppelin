package com.javarush.island.kazakov.util;

public record Location(int x, int y) {
    public Location(Location location) {
        this(location.x, location.y);
    }
}
