package com.javarush.island.kazakov.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Rnd {
    private static final boolean DEBUG = false;
    private static final int SEED;
    private static final Random random;

    private Rnd() {
    }

    static {
        if (DEBUG) {
            SEED = 1234;
            random = new Random(SEED);
        } else {
            SEED = -1;
            random = null;
        }
    }

    public static int random(int bound) {
        if (DEBUG) return random.nextInt(bound);
        else return ThreadLocalRandom.current().nextInt(bound);
    }

    public static int random(int min, int max) {
        if (DEBUG) return min >= max ? min : random.nextInt(min, max);
        else return com.javarush.island.khmelov.util.Rnd.random(min, max);
    }

    public static double random(double min, double max) {
        if (DEBUG) return random.nextDouble(min, max);
        else return com.javarush.island.khmelov.util.Rnd.random(min, max);
    }

    public static boolean get(double percentProbably) {
        if (DEBUG) return random(0, 100) < percentProbably;
        return com.javarush.island.khmelov.util.Rnd.get(percentProbably);
    }
}
