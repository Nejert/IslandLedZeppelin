package com.javarush.island.kazakov;

import java.util.Random;

public class TempRnd {
    private static final int seed = 1234;
    private static final Random random = new Random(seed);
    public static Random get(){
        return random;
    }    
}
