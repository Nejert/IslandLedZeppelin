package com.javarush.island.kazakov.util;

public record Location(int x, int y) {
    @SuppressWarnings("unused")
    public Location(Location location) {
        this(location.x, location.y);
    }
}
