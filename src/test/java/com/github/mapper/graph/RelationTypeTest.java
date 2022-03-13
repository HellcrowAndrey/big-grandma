package com.github.mapper.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationTypeTest {

    @Test
    void givenEnumRelationType_whenIsNotDefault_thenReturnFalse() {
        assertFalse(RelationType.def.isNotDefault(), "u should check method isNotDefault");
    }

    @Test
    void givenEnumRelationType_whenIsNotDefault_thenReturnTrue() {
        assertTrue(RelationType.manyToMany.isNotDefault(), "u should check method isNotDefault");
    }

}