package com.github.mapper.factories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class EntityFactoryTest {

    @Test
    void given_Source_Class_MappedFields_whenOfEntity_thenReturnObject() {
        Map<String, Object> sources = EntityFactoryMocks.sourcesActualParameters();
        Map<String, Field> fields = EntityFactoryMocks.fields(EntityFactoryMocks.aliases(), Entity_With_Default_Constructor.class);
        Entity_With_Default_Constructor exp = new Entity_With_Default_Constructor(1L, "test-entities");
        Entity_With_Default_Constructor act = EntityFactory.ofEntity(sources, fields, Entity_With_Default_Constructor.class);
        Assertions.assertEquals(exp, act);
    }

    @Test
    void given_Source_Class_MappedFields_whenOfEntity_thenReturnNull() {
        Map<String, Object> sources = EntityFactoryMocks.sourcesNotActualParameters();
        Map<String, Field> fields = EntityFactoryMocks.fields(EntityFactoryMocks.aliases(), Entity_With_Default_Constructor.class);
        Entity_With_Default_Constructor act = EntityFactory.ofEntity(sources, fields, Entity_With_Default_Constructor.class);
        Assertions.assertNull(act);
    }

    @Test
    void given_Source_Class_MappedFields_whenOfEntity_thenThrowRuntimeException() {
        Map<String, Object> sources = new HashMap<>();
        Map<String, Field> fields = new HashMap<>();
        Assertions.assertThrows(RuntimeException.class, () -> EntityFactory.ofEntity(sources, fields, Entity_Without_Default_Constructor.class));
    }

    @Test
    void givenSourceAndFieldsAndType_whenOfEntity_thenReturnEntityWithBigDecimal() {
        Map<String, Object> sources = EntityFactoryMocks.sourcesActualParametersToWithBigDecimal();
        Map<String, Field> fields = EntityFactoryMocks.fields(EntityFactoryMocks.aliasesToWithBigTypes(), EntityWithBigDecimal.class);
        EntityWithBigDecimal exp = new EntityWithBigDecimal(1L, new BigDecimal("435345345234.643"));
        EntityWithBigDecimal act = EntityFactory.ofEntity(sources, fields, EntityWithBigDecimal.class);
        Assertions.assertEquals(exp, act);
    }

    @Test
    void givenSourceAndFieldsAndType_whenOfEntity_thenReturnEntityWithBigInteger() {
        Map<String, Object> sources = EntityFactoryMocks.sourcesActualParametersToBigInteger();
        Map<String, Field> fields = EntityFactoryMocks.fields(EntityFactoryMocks.aliasesToWithBigTypes(), EntityWithBigInteger.class);
        EntityWithBigInteger exp = new EntityWithBigInteger(1L, new BigInteger("435345345234643"));
        EntityWithBigInteger act = EntityFactory.ofEntity(sources, fields, EntityWithBigInteger.class);
        Assertions.assertEquals(exp, act);
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

    public static class EntityWithBigDecimal {

        private Long id;

        private BigDecimal number;

        public EntityWithBigDecimal() {
        }

        public EntityWithBigDecimal(Long id, BigDecimal number) {
            this.id = id;
            this.number = number;
        }

        public Long getId() {
            return id;
        }

        public BigDecimal getNumber() {
            return number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EntityWithBigDecimal)) return false;
            EntityWithBigDecimal that = (EntityWithBigDecimal) o;
            return Objects.equals(id, that.id) && Objects.equals(number, that.number);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, number);
        }

        @Override
        public String toString() {
            return "EntityWithBigDecimal{" +
                    "id=" + id +
                    ", number=" + number +
                    '}';
        }
    }

    public static class EntityWithBigInteger {

        private Long id;

        private BigInteger number;

        public EntityWithBigInteger() {
        }

        public EntityWithBigInteger(Long id, BigInteger number) {
            this.id = id;
            this.number = number;
        }

        public Long getId() {
            return id;
        }

        public BigInteger getNumber() {
            return number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EntityWithBigInteger)) return false;
            EntityWithBigInteger that = (EntityWithBigInteger) o;
            return Objects.equals(id, that.id) && Objects.equals(number, that.number);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, number);
        }

        @Override
        public String toString() {
            return "EntityWithBigInteger{" +
                    "id=" + id +
                    ", number=" + number +
                    '}';
        }
    }


}