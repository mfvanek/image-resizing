/*
 * Copyright (c) 2018-2023. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package io.github.mfvanek.image.resizing.pojos;

import io.github.mfvanek.image.resizing.interfaces.Dimensional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class ImageDimension implements Dimensional {

    private static final int MINIMAL_SIZE = 1;

    private final int width;
    private final int height;

    public ImageDimension(final int dimension) {
        this(dimension, dimension);
    }

    public ImageDimension(final int width, final int height) {
        validateWidth(width);
        validateHeight(height);

        this.width = width;
        this.height = height;
    }

    private static void validateWidth(final int width) {
        if (width < MINIMAL_SIZE) {
            throw new IllegalArgumentException("Width should be positive");
        }
    }

    private static void validateHeight(final int height) {
        if (height < MINIMAL_SIZE) {
            throw new IllegalArgumentException("Height should be positive");
        }
    }
}
