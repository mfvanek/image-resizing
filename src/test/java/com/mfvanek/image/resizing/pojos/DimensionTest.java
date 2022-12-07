/*
 * Copyright (c) 2018-2022. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package com.mfvanek.image.resizing.pojos;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DimensionTest {

    @Test
    void constructor() {
        assertThatThrownBy(() -> new Dimension(0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Width should be positive");

        assertThatThrownBy(() -> new Dimension(-1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Width should be positive");

        assertThatThrownBy(() -> new Dimension(1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Height should be positive");

        assertThatThrownBy(() -> new Dimension(1, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Height should be positive");
    }

    @Test
    void widthAndHeight() {
        assertThat(new Dimension(100))
                .hasToString("Dimension(width=100, height=100)")
                .satisfies(d -> {
                    assertThat(d.getWidth()).isEqualTo(100);
                    assertThat(d.getHeight()).isEqualTo(100);
                });

        assertThat(new Dimension(123, 321))
                .hasToString("Dimension(width=123, height=321)")
                .satisfies(d -> {
                    assertThat(d.getWidth()).isEqualTo(123);
                    assertThat(d.getHeight()).isEqualTo(321);
                });
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void equalsHashCodeShouldAdhereContracts() {
        EqualsVerifier.forClass(Dimension.class)
                .verify();
    }
}
