package com.javarush.island.kazakov.config;

import java.awt.*;

public class Default {
    private Default() {
    }
    public static final int ROWS = 10;
    public static final int COLS = 10;
    public static final int ANIMAL_MOVES_COUNT = 10;
    public static final int BIRTH_PROBABILITY = 50;
    public static final int TICK_PERIOD_MS = 1000;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final int GRID_STROKE_WIDTH = 1;
    public static final int STAT_WIDTH_PERCENTAGE = 10;
    public static final int ENTITY_GROUP_CAPACITY = 5;
    public static final Color BACKGROUND_COLOR = Color.GRAY;
    public static final Color GRID_LINES_COLOR = Color.LIGHT_GRAY;
    public static final String WINDOW_TITLE = "Island";
    public static final String SPAWN_CONFIG = "/kazakov/initialSpawnProbabilityConfig.yaml";
    public static final String ENTITY_CONFIG = "/kazakov/entityConfig.json";
    public static final String CONFIG = "/kazakov/config.xml";
    // initial spawn probability
    public static final int WOLF_SPAWN = 30;
    public static final int BOA_SPAWN = 20;
    public static final int FOX_SPAWN = 40;
    public static final int BEAR_SPAWN = 10;
    public static final int EAGLE_SPAWN = 15;
    public static final int HORSE_SPAWN = 5;
    public static final int DEER_SPAWN = 5;
    public static final int RABBIT_SPAWN = 60;
    public static final int MOUSE_SPAWN = 60;
    public static final int GOAT_SPAWN = 30;
    public static final int SHEEP_SPAWN = 30;
    public static final int BOAR_SPAWN = 20;
    public static final int BUFFALO_SPAWN = 5;
    public static final int DUCK_SPAWN = 60;
    public static final int CATERPILLAR_SPAWN = 80;
    public static final int PLANT_SPAWN = 10;
    // image icon paths
    private static final String EMOJIS = "/kazakov/emojis";
    private static final String CARNIVORES = EMOJIS + "/carnivore";
    private static final String HERBIVORES = EMOJIS + "/herbivore";
    private static final String PLANTS = EMOJIS + "/plant";
    public static final String BEAR_IMAGE = CARNIVORES + "/bear.png";
    public static final String BOA_IMAGE = CARNIVORES + "/boa.png";
    public static final String EAGLE_IMAGE = CARNIVORES + "/eagle.png";
    public static final String FOX_IMAGE = CARNIVORES + "/fox.png";
    public static final String WOLF_IMAGE = CARNIVORES + "/wolf.png";
    public static final String BOAR_IMAGE = HERBIVORES + "/boar.png";
    public static final String BUFFALO_IMAGE = HERBIVORES + "/buffalo.png";
    public static final String CATERPILLAR_IMAGE = HERBIVORES + "/caterpillar.png";
    public static final String DEER_IMAGE = HERBIVORES + "/deer.png";
    public static final String DUCK_IMAGE = HERBIVORES + "/duck.png";
    public static final String GOAT_IMAGE = HERBIVORES + "/goat.png";
    public static final String HORSE_IMAGE = HERBIVORES + "/horse.png";
    public static final String MOUSE_IMAGE = HERBIVORES + "/mouse.png";
    public static final String RABBIT_IMAGE = HERBIVORES + "/rabbit.png";
    public static final String SHEEP_IMAGE = HERBIVORES + "/sheep.png";
    public static final String PLANT_IMAGE = PLANTS + "/plant.png";
    // icons
    public static final String BEAR_ICON = "\uD83D\uDC3B";
    public static final String BOA_ICON = "\uD83D\uDC0D";
    public static final String EAGLE_ICON = "\uD83E\uDD85";
    public static final String FOX_ICON = "\uD83E\uDD8A";
    public static final String WOLF_ICON = "\uD83D\uDC3A";
    public static final String BOAR_ICON = "\uD83D\uDC17";
    public static final String BUFFALO_ICON = "\uD83D\uDC03";
    public static final String CATERPILLAR_ICON = "\uD83D\uDC1B";
    public static final String DEER_ICON = "\uD83E\uDD8C";
    public static final String DUCK_ICON = "\uD83E\uDD86";
    public static final String GOAT_ICON = "\uD83D\uDC10";
    public static final String HORSE_ICON = "\uD83D\uDC0E";
    public static final String MOUSE_ICON = "\uD83D\uDC01";
    public static final String RABBIT_ICON = "\uD83D\uDC07";
    public static final String SHEEP_ICON = "\uD83D\uDC11";
    public static final String PLANT_ICON = "\uD83C\uDF3E";
}
