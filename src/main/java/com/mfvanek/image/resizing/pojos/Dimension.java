/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.pojos;

import com.mfvanek.image.resizing.interfaces.Dimensional;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Dimension implements Dimensional {

    private final int width;
    private final int height;

    public Dimension(int dimension) {
        this(dimension, dimension);
    }

    public Dimension(int width, int height) {
        validateWidth(width);
        validateHeight(height);

        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private static void validateWidth(int width) {
        if (width < 1) {
            throw new IllegalArgumentException("Width should be positive");
        }
    }

    private static void validateHeight(int height) {
        if (height < 1) {
            throw new IllegalArgumentException("Height should be positive");
        }
    }
}
