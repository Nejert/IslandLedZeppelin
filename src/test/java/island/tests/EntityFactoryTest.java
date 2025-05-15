package island.tests;


import com.javarush.island.kazakov.entity.abstraction.Entity;
import com.javarush.island.kazakov.entity.misc.EntityFactory;
import com.javarush.island.kazakov.entity.misc.EntityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;


public class EntityFactoryTest {
    private final Map<EntityType, Entity> entities;

    public EntityFactoryTest() {
        Class<EntityFactory> entityFactoryClass = EntityFactory.class;
        try {
            Field field = entityFactoryClass.getDeclaredField("entities");
            field.setAccessible(true);
            entities = Collections.unmodifiableMap((Map<EntityType, Entity>) field.get(entityFactoryClass));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadTest(){
        for (EntityType entityType : EntityType.values()) {
            Assertions.assertNotNull(entities.get(entityType));
        }
    }

    @Test
    void integrityTest() {
        for (EntityType entityType : EntityType.values()) {
            Entity test = EntityFactory.newEntity(entityType);
            Assertions.assertNotEquals(entities.get(entityType), test);
            Assertions.assertEquals(entities.get(entityType).getClass(), test.getClass());
        }
    }
}
