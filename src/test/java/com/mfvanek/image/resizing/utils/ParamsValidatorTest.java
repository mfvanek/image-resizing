/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.utils;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.pojos.ResizeParams;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParamsValidatorTest {

    @Test
    void withoutArgs() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ParamsValidator.getInstance(null).validate());
        assertEquals("Parameter 'args' cannot be null", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> ParamsValidator.getInstance(new String[0]).validate());
        assertEquals("Invalid number of arguments; should be 3 arguments: path-to-image, width and height", e.getMessage());
    }

    @Test
    void withCorrectArgs() {
        String[] params = {"file:/any-picture.BMP", "111", "222"};
        ResizeParams resizeParams = ParamsValidator.getInstance(params).validate();
        assertNotNull(resizeParams);
        assertTrue(resizeParams.getPathToFile().endsWith("any-picture.BMP"));
        assertEquals(111, resizeParams.getWidth());
        assertEquals(222, resizeParams.getHeight());
        assertEquals(ResizeType.RAW, resizeParams.getAlgorithm());
    }

    @Test
    void withRedundantArgs() {
        String[] params = {"", "1", "2", "3"};
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ParamsValidator.getInstance(params).validate());
        assertEquals("Invalid number of arguments; should be 3 arguments: path-to-image, width and height", e.getMessage());
    }

    @Test
    void withIncompleteArgs() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ParamsValidator.getInstance(new String[1]).validate());
        assertEquals("Invalid number of arguments; should be 3 arguments: path-to-image, width and height", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> ParamsValidator.getInstance(new String[2]).validate());
        assertEquals("Invalid number of arguments; should be 3 arguments: path-to-image, width and height", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> ParamsValidator.getInstance(new String[3]).validate());
        assertEquals("The width has an invalid format or value", e.getMessage());

        String[] params = {"", "1", null};
        e = assertThrows(IllegalArgumentException.class, () -> ParamsValidator.getInstance(params).validate());
        assertEquals("The height has an invalid format or value", e.getMessage());
    }

    @Test
    void useDefault() {
        ResizeParams resizeParams = ParamsValidator.getInstance(new String[0]).useDefaultIfNeed().validate();
        assertNotNull(resizeParams);
        assertTrue(resizeParams.getPathToFile().endsWith("java-logo.jpeg"));
        assertEquals(200, resizeParams.getWidth());
        assertEquals(100, resizeParams.getHeight());
        assertEquals(ResizeType.RAW, resizeParams.getAlgorithm());
    }

    @Test
    void withAlgorithm() {
        ResizeParams resizeParams = ParamsValidator.getInstance(new String[0]).useDefaultIfNeed().withAlgorithm(ResizeType.KEEP_ASPECT_RATIO).validate();
        assertNotNull(resizeParams);
        assertEquals(ResizeType.KEEP_ASPECT_RATIO, resizeParams.getAlgorithm());
    }

    @Test
    void withPath() {
        ResizeParams resizeParams = ParamsValidator.getInstance(new String[0]).useDefaultIfNeed().withPath("file:/any-picture.bmp").validate();
        assertNotNull(resizeParams);
        assertTrue(resizeParams.getPathToFile().endsWith("any-picture.bmp"));
    }
}