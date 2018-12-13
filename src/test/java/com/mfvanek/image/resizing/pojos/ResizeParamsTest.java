/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.pojos;

import com.mfvanek.image.resizing.enums.ResizeType;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class ResizeParamsTest {

    @Test
    void withInvalidPath() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ResizeParams.newWithDefaultDimension(null));
        assertEquals("Path to image cannot be null", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> ResizeParams.newWithDefaultDimension(""));
        assertEquals("Path to image cannot be empty", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> ResizeParams.newWithDefaultDimension("invalid path"));
        assertEquals("Path to image should starts with 'file' or 'http'", e.getMessage());
    }

    @Test
    void isSimilarToURL() {
        ResizeParams resizeParams = ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.png");
        assertTrue(resizeParams.isSimilarToURL());

        resizeParams = ResizeParams.newWithDefaultDimension("file:/any-web-site/any-catalog/anyfile.png");
        assertFalse(resizeParams.isSimilarToURL());
    }

    @Test
    void getExtension() {
        ResizeParams resizeParams = ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.png");
        assertEquals("png", resizeParams.getExtension());
        assertTrue(resizeParams.isFormatSupported());
        assertFalse(resizeParams.isFormatNotSupported());

        resizeParams = ResizeParams.newWithDefaultDimension("file:///C:/Users/IVAN~1.VAK/AppData/Local/Temp/resized_images_7304718956539175727/resized_java-logo.JPEG");
        assertEquals("jpeg", resizeParams.getExtension());
        assertTrue(resizeParams.isFormatSupported());
        assertFalse(resizeParams.isFormatNotSupported());
    }

    @Test
    void toURI() throws URISyntaxException {
        ResizeParams resizeParams = ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.PnG");
        assertEquals(new URI("https://any-web-site/any-catalog/anyfile.PnG"), resizeParams.toURI());

        resizeParams = ResizeParams.newWithDefaultDimension("file:///C:/Users/IVAN~1.VAK/AppData/Local/Temp/resized_images_7304718956539175727/resized_java-logo.JPEG");
        assertEquals(new URI("file:///C:/Users/IVAN~1.VAK/AppData/Local/Temp/resized_images_7304718956539175727/resized_java-logo.JPEG"), resizeParams.toURI());

        assertThrows(URISyntaxException.class, () -> ResizeParams.newWithDefaultDimension("http://mw1.google.com/mw-earth-vectordb/kml-samples/gp/seattle/gigapxl/$[level]/r$[y]_c$[x].jpg").toURI());
    }

    @Test
    void getOutputName() {
        ResizeParams resizeParams = ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.PnG");
        assertEquals("resized_anyfile.PnG", resizeParams.getOutputName());
    }

    @Test
    void isFormatSupported_NotSupported() {
        ResizeParams resizeParams = ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.PnG");
        assertEquals("png", resizeParams.getExtension());
        assertTrue(resizeParams.isFormatSupported());
        assertFalse(resizeParams.isFormatNotSupported());

        resizeParams = ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.exTensIoN");
        assertEquals("extension", resizeParams.getExtension());
        assertFalse(resizeParams.isFormatSupported());
        assertTrue(resizeParams.isFormatNotSupported());
    }

    @Test
    void getPathToFile() {
        ResizeParams resizeParams = ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.PnG");
        assertEquals("https://any-web-site/any-catalog/anyfile.PnG", resizeParams.getPathToFile());
    }

    @Test
    void getWidth_Height_Algorithm() {
        ResizeParams resizeParams = ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.PnG");
        assertEquals(500, resizeParams.getWidth());
        assertEquals(500, resizeParams.getHeight());
        assertEquals(ResizeType.RAW, resizeParams.getAlgorithm());

        resizeParams = new ResizeParams("https://anyfile.PnG", 100, 200, ResizeType.KEEP_ASPECT_RATIO);
        assertEquals(100, resizeParams.getWidth());
        assertEquals(200, resizeParams.getHeight());
        assertEquals(ResizeType.KEEP_ASPECT_RATIO, resizeParams.getAlgorithm());
    }
}