/*
 * Copyright (c) 2018-2023. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package io.github.mfvanek.image.resizing.utils;

import io.github.mfvanek.image.resizing.enums.ResizeType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParamsValidatorTest {

    @Test
    void withoutArgs() {
        final ParamsValidator validatorOfNull = ParamsValidator.builder((String[]) null);
        assertThatThrownBy(validatorOfNull::validate)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Parameter 'args' cannot be null");

        final ParamsValidator validator = ParamsValidator.builder();
        assertThatThrownBy(validator::validate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid number of arguments; should be 3 arguments: path-to-image, width and height");
    }

    @Test
    void withCorrectArgs() {
        assertThat(ParamsValidator.builder("file:/any-picture.BMP", "111", "222").validate())
                .isNotNull()
                .hasToString("ResizeParams(pathToFile=file:/any-picture.BMP, pathToFileLowercased=file:/any-picture.bmp, " +
                        "dimension=Dimension(width=111, height=222), algorithm=RAW, convertToGrayscale=true)")
                .satisfies(r -> {
                    assertThat(r.getPathToFile()).endsWith("any-picture.BMP");
                    assertThat(r.getWidth()).isEqualTo(111);
                    assertThat(r.getHeight()).isEqualTo(222);
                    assertThat(r.getAlgorithm()).isEqualTo(ResizeType.RAW);
                });
    }

    @Test
    void withRedundantArgs() {
        final ParamsValidator validator = ParamsValidator.builder("", "1", "2", "3");
        assertThatThrownBy(validator::validate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid number of arguments; should be 3 arguments: path-to-image, width and height");
    }

    @Test
    void withIncompleteArgs() {
        ParamsValidator validator = ParamsValidator.builder("");
        assertThatThrownBy(validator::validate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid number of arguments; should be 3 arguments: path-to-image, width and height");

        validator = ParamsValidator.builder("", "");
        assertThatThrownBy(validator::validate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid number of arguments; should be 3 arguments: path-to-image, width and height");

        validator = ParamsValidator.builder("", "", "");
        assertThatThrownBy(validator::validate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The width has an invalid format or value");

        validator = ParamsValidator.builder("", "1", null);
        assertThatThrownBy(validator::validate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The height has an invalid format or value");
    }

    @Test
    void useDefault() {
        assertThat(ParamsValidator.builder()
                .useDefaultIfNeed()
                .validate())
                .isNotNull()
                .satisfies(r -> {
                    assertThat(r.getPathToFile()).endsWith("java-logo.jpeg");
                    assertThat(r.getWidth()).isEqualTo(200);
                    assertThat(r.getHeight()).isEqualTo(100);
                    assertThat(r.getAlgorithm()).isEqualTo(ResizeType.RAW);
                });
    }

    @Test
    void withAlgorithm() {
        assertThat(ParamsValidator.builder()
                .useDefaultIfNeed()
                .withAlgorithm(ResizeType.KEEP_ASPECT_RATIO)
                .validate())
                .isNotNull()
                .satisfies(r -> assertThat(r.getAlgorithm()).isEqualTo(ResizeType.KEEP_ASPECT_RATIO));

    }

    @Test
    void withPath() {
        assertThat(ParamsValidator.builder()
                .useDefaultIfNeed()
                .withPath("file:/any-picture.bmp")
                .validate())
                .isNotNull()
                .satisfies(r -> assertThat(r.getPathToFile()).endsWith("any-picture.bmp"));
    }
}
