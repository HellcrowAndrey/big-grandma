package com.github.mapper.factories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class EntityFactoryTest {

    @Test
    void given_Source_Class_MappedFields_whenOfEntity_thenReturnObject() {
        Map<String, Object> sources = EntityFactoryMocks.sourcesActualParameters();
        Map<String, Field> fields = EntityFactoryMocks.fields(Entity_With_Default_Constructor.class);
        Entity_With_Default_Constructor exp = new Entity_With_Default_Constructor(1L, "test-entities");
        Entity_With_Default_Constructor act = EntityFactory.ofEntity(sources, fields, Entity_With_Default_Constructor.class);
        Assertions.assertEquals(exp, act);
    }

    @Test
    void given_Source_Class_MappedFields_whenOfEntity_thenReturnNull() {
        Map<String, Object> sources = EntityFactoryMocks.sourcesNotActualParameters();
        Map<String, Field> fields = EntityFactoryMocks.fields(Entity_With_Default_Constructor.class);
        Entity_With_Default_Constructor act = EntityFactory.ofEntity(sources, fields, Entity_With_Default_Constructor.class);
        Assertions.assertNull(act);
    }

    @Test
    void given_Source_Class_MappedFields_whenOfEntity_thenThrowRuntimeException() {
        Map<String, Object> sources = new HashMap<>();
        Map<String, Field> fields = new HashMap<>();
        Assertions.assertThrows(RuntimeException.class, () -> EntityFactory.ofEntity(sources, fields, Entity_Without_Default_Constructor.class));
    }

    public static class Entity_With_Default_Constructor {

        private Long id;

        private String name;

        public Entity_With_Default_Constructor() {
        }

        public Entity_With_Default_Constructor(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Entity_With_Default_Constructor)) return false;
            Entity_With_Default_Constructor entityWithDefaultConstructor = (Entity_With_Default_Constructor) o;
            return Objects.equals(id, entityWithDefaultConstructor.id) && Objects.equals(name, entityWithDefaultConstructor.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "Entity{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class Entity_Without_Default_Constructor {

        private Long id;

        private String name;

        public Entity_Without_Default_Constructor(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Entity_Without_Default_Constructor)) return false;
            Entity_Without_Default_Constructor that = (Entity_Without_Default_Constructor) o;
            return Objects.equals(id, that.id) && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "Entity_Without_Default_Constructor{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}