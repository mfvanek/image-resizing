/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.pojos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DimensionTest {

    @Test
    void constructor() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Dimension(0, 0));
        assertEquals("Width should be positive", e.getLocalizedMessage());

        e = assertThrows(IllegalArgumentException.class, () -> new Dimension(-1, 0));
        assertEquals("Width should be positive", e.getLocalizedMessage());

        e = assertThrows(IllegalArgumentException.class, () -> new Dimension(1, 0));
        assertEquals("Height should be positive", e.getLocalizedMessage());
    }

    @Test
    void widthAndHeight() {
        Dimension d1 = new Dimension(100);
        assertEquals(100, d1.getWidth());
        assertEquals(100, d1.getHeight());

        Dimension d2 = new Dimension(123, 321);
        assertEquals(123, d2.getWidth());
        assertEquals(321, d2.getHeight());
    }

    @Test
    void equals() {
        Dimension d1 = new Dimension(100);
        Dimension d2 = new Dimension(123, 321);
        assertEquals(d1, d1);
        assertEquals(d1, new Dimension(100));
        assertNotEquals(d1, d2);

        assertEquals(d1.hashCode(), d1.hashCode());
        assertEquals(d1.hashCode(), new Dimension(100).hashCode());
        assertNotEquals(d1.hashCode(), d2.hashCode());
    }
}