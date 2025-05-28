package com.javarush.island.kazakov.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.javarush.island.kazakov.IslandException;
import lombok.AccessLevel;
import lombok.Getter;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class Config {
    @Getter(AccessLevel.NONE)
    private static volatile Config instance;
    @Getter(AccessLevel.NONE)
    private static final Lock lock = new ReentrantLock();
    private int rows;
    private int cols;
    private int animalMovesCount;
    private int birthProbability;
    private int tickPeriodMs;
    private int width;
    private int height;
    private int gridStrokeWidth;
    private int statWidthPercentage;
    private int entityGroupCapacity;
    private Color backgroundColor;
    private Color gridLinesColor;
    private String windowTitle;
    private String spawnConfig;
    private String entityConfig;

    private Config() {
        loadDefaults();
        loadConfig();
    }

    private void loadDefaults(){
        rows = Default.ROWS;
        cols = Default.COLS;
        animalMovesCount = Default.ANIMAL_MOVES_COUNT;
        birthProbability = Default.BIRTH_PROBABILITY;
        tickPeriodMs = Default.TICK_PERIOD_MS;
        width = Default.WIDTH;
        height = Default.HEIGHT;
        gridStrokeWidth = Default.GRID_STROKE_WIDTH;
        statWidthPercentage = Default.STAT_WIDTH_PERCENTAGE;
        entityGroupCapacity = Default.ENTITY_GROUP_CAPACITY;
        backgroundColor = Default.BACKGROUND_COLOR;
        gridLinesColor = Default.GRID_LINES_COLOR;
        windowTitle = Default.WINDOW_TITLE;
        spawnConfig = Default.SPAWN_CONFIG;
        entityConfig = Default.ENTITY_CONFIG;
    }

    private void loadConfig() {
        ObjectMapper mapper = new XmlMapper();
        try {
            JsonNode jsonConfig = mapper.readTree(Config.class.getResource(Default.CONFIG));
            for (Iterator<String> it = jsonConfig.fieldNames(); it.hasNext(); ) {
                String fieldName = it.next();
                JsonNode fieldNode = jsonConfig.get(fieldName);
                Field field = Config.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                if (field.getType() == int.class) {
                    field.set(this, fieldNode.asInt());
                } else if (field.getType() == String.class) {
                    field.set(this, fieldNode.asText());
                } else {
                    field.set(this, Color.decode(fieldNode.asText()));
                }
            }
        } catch (IOException e) {
            throw new IslandException("Unable to read common config. ", e);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IslandException("Unable to associate data element. ", e);
        }
    }

    public static Config get() {
        Config result = instance;
        if (result != null) {
            return result;
        }
        lock.lock();
        try {
            if (instance == null) {
                instance = new Config();
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }
}
