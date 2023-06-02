/*
 * Copyright (c) 2018-2023. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package io.github.mfvanek.image.resizing.interfaces;

import io.github.mfvanek.image.resizing.enums.ResizeType;

import java.awt.image.BufferedImage;

public interface ImageResizer {

    BufferedImage resize(BufferedImage inputImage, ImageParams resizeParams);

    ResizeType getAlgorithm();
}
