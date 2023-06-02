/*
 * Copyright (c) 2018-2023. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package io.github.mfvanek.image.resizing.pojos;

import io.github.mfvanek.image.resizing.enums.ResizeType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ResizeParamsTest {

    @Test
    void withInvalidPath() {
        assertThatThrownBy(() -> ResizeParams.newWithDefaultDimension(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Path to image cannot be null");

        assertThatThrownBy(() -> ResizeParams.newWithDefaultDimension(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Path to image cannot be empty");

        assertThatThrownBy(() -> ResizeParams.newWithDefaultDimension("invalid path"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Path to image should starts with 'file' or 'http'");
    }

    @Test
    void isSimilarToURL() {
        assertThat(ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.png"))
                .isNotNull()
                .satisfies(r -> assertThat(r.isSimilarToURL()).isTrue());

        assertThat(ResizeParams.newWithDefaultDimension("file:/any-web-site/any-catalog/anyfile.png"))
                .isNotNull()
                .satisfies(r -> assertThat(r.isSimilarToURL()).isFalse());
    }

    @Test
    void getExtension() {
        assertThat(ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.png"))
                .isNotNull()
                .satisfies(r -> assertThat(r.getExtension()).isEqualTo("png"));

        assertThat(ResizeParams.newWithDefaultDimension("file:///C:/Users/IVAN~1.VAK/AppData/Local/Temp/resized_images_7304718956539175727/resized_java-logo.JPEG"))
                .isNotNull()
                .satisfies(r -> assertThat(r.getExtension()).isEqualTo("jpeg"));
    }

    @Test
    @SneakyThrows
    void toURI() {
        assertThat(ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.PnG"))
                .isNotNull()
                .satisfies(r -> assertThat(r.toURI()).isEqualTo(new URI("https://any-web-site/any-catalog/anyfile.PnG")));

        assertThat(ResizeParams.newWithDefaultDimension("file:///C:/Users/IVAN~1.VAK/AppData/Local/Temp/resized_images_7304718956539175727/resized_java-logo.JPEG"))
                .isNotNull()
                .satisfies(r -> assertThat(r.toURI()).isEqualTo(new URI("file:///C:/Users/IVAN~1.VAK/AppData/Local/Temp/resized_images_7304718956539175727/resized_java-logo.JPEG")));

        final ResizeParams resizeParams = ResizeParams.newWithDefaultDimension("http://mw1.google.com/mw-earth-vectordb/kml-samples/gp/seattle/gigapxl/$[level]/r$[y]_c$[x].jpg");

        assertThatThrownBy(resizeParams::toURI)
                .isInstanceOf(URISyntaxException.class)
                .hasMessage("Illegal character in path at index 72: http://mw1.google.com/mw-earth-vectordb/kml-samples/gp/seattle/gigapxl/$[level]/r$[y]_c$[x].jpg");
    }

    @Test
    void getOutputName() {
        assertThat(ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.PnG"))
                .isNotNull()
                .satisfies(r -> {
                    assertThat(r.getOutputName()).isEqualTo("resized_anyfile.PnG");
                    assertThat(r.getPathToFile()).isEqualTo("https://any-web-site/any-catalog/anyfile.PnG");
                });
    }

    @Test
    void getWidthAndHeightAlgorithm() {
        assertThat(ResizeParams.newWithDefaultDimension("https://any-web-site/any-catalog/anyfile.PnG"))
                .isNotNull()
                .satisfies(r -> {
                    assertThat(r.getWidth()).isEqualTo(500);
                    assertThat(r.getHeight()).isEqualTo(500);
                    assertThat(r.getAlgorithm()).isEqualTo(ResizeType.RAW);
                });

        assertThat(ResizeParams.newWithAlgorithm("https://anyfile.PnG", 100, 200, ResizeType.KEEP_ASPECT_RATIO))
                .isNotNull()
                .satisfies(r -> {
                    assertThat(r.getWidth()).isEqualTo(100);
                    assertThat(r.getHeight()).isEqualTo(200);
                    assertThat(r.getAlgorithm()).isEqualTo(ResizeType.KEEP_ASPECT_RATIO);
                });
    }
}
