/*
 * Copyright (c) 2018-2023. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package io.github.mfvanek.image.resizing.pojos;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageDimensionTest {

    @Test
    void constructor() {
        assertThatThrownBy(() -> new ImageDimension(0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Width should be positive");

        assertThatThrownBy(() -> new ImageDimension(-1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Width should be positive");

        assertThatThrownBy(() -> new ImageDimension(1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Height should be positive");

        assertThatThrownBy(() -> new ImageDimension(1, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Height should be positive");
    }

    @Test
    void widthAndHeight() {
        assertThat(new ImageDimension(100))
                .hasToString("ImageDimension(width=100, height=100)")
                .satisfies(d -> {
                    assertThat(d.getWidth()).isEqualTo(100);
                    assertThat(d.getHeight()).isEqualTo(100);
                });

        assertThat(new ImageDimension(123, 321))
                .hasToString("ImageDimension(width=123, height=321)")
                .satisfies(d -> {
                    assertThat(d.getWidth()).isEqualTo(123);
                    assertThat(d.getHeight()).isEqualTo(321);
                });
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void equalsHashCodeShouldAdhereContracts() {
        EqualsVerifier.forClass(ImageDimension.class)
                .verify();
    }
}
