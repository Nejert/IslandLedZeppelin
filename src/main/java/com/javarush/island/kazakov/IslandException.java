package com.javarush.island.kazakov;

public class IslandException extends RuntimeException {

    public IslandException(Throwable cause) {
        super(cause);
    }

    public IslandException(String message, Throwable cause) {
        super(message, cause);
    }
}
